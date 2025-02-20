    package com.example.walletApplication.services;

    import com.example.walletApplication.DTO.TransactionRequest;
    import com.example.walletApplication.DTO.TransactionResponse;
    import com.example.walletApplication.Exceptions.TransactionIdNotFoundException;
    import com.example.walletApplication.Exceptions.UnauthorizedAccessException;
    import com.example.walletApplication.Exceptions.WalletNotFoundException;
    import com.example.walletApplication.entity.Client;
    import com.example.walletApplication.entity.Transaction;
    import com.example.walletApplication.entity.TransferTransaction;
    import com.example.walletApplication.enums.Currency;
    import com.example.walletApplication.entity.Wallet;
    import com.example.walletApplication.enums.TransactionType;
    import com.example.walletApplication.messages.Messages;
    import com.example.walletApplication.repository.ClientRepository;
    import com.example.walletApplication.repository.TransactionRepository;
    import com.example.walletApplication.repository.TransferTransactionRepository;
    import com.example.walletApplication.repository.WalletRepository;
    import org.jetbrains.annotations.NotNull;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.*;

    @Service
    public class TransactionService {
        private WalletRepository walletRepository;

        private ClientRepository clientRepository;

        private TransactionRepository transactionRepository;

        private TransferTransactionRepository transferTransactionRepository;

        @Autowired
        public TransactionService(ClientRepository clientRepository, WalletRepository walletRepository, TransactionRepository transactionRepository, TransferTransactionRepository transferTransactionRepository) {
            this.clientRepository = clientRepository;
            this.walletRepository = walletRepository;
            this.transactionRepository = transactionRepository;
            this.transferTransactionRepository = transferTransactionRepository;
        }


        private Wallet fetchWalletOfClient(long userId) {
            Client client = clientRepository.findClientById( userId)
                    .orElseThrow(() -> new WalletNotFoundException("Client not found"));
            Wallet wallet = walletRepository.findByClient(client)
                    .orElseThrow(() -> new WalletNotFoundException("Wallet not found for client"));
            if (!wallet.checkClient(client)) {
                throw new UnauthorizedAccessException("Unauthorized access to wallet");
            }
            return wallet;
        }



        public List<TransactionResponse> walletHistory(Long userId) {
            Wallet wallet = fetchWalletOfClient(userId);
            List<TransactionResponse> depositsAndWithdrawals = transactionRepository.findDepositsAndWithdrawals(wallet);
            List<TransactionResponse> transferTransactions = transactionRepository.findWalletTransferTransHistory(wallet);
            List<TransactionResponse> history = new ArrayList<>();
            history.addAll(depositsAndWithdrawals);
            history.addAll(transferTransactions);
            history.sort(Comparator.comparing(TransactionResponse::getTimestamp).reversed());
            return history;

        }

        public TransactionResponse walletTransactionHistoryById(Long clientId, Long transId) {
            Wallet wallet = fetchWalletOfClient(clientId);
            TransactionResponse transactionRes = transactionRepository.findTransactionTypeWithdrawAndDepositByIdAndWallet(transId, wallet);
            if(transactionRes == null){
                transactionRes = transactionRepository.findWalletTransferTransHistoryByIdAndWallet(transId,wallet);
                if(transactionRes == null){
                    throw new TransactionIdNotFoundException(Messages.INVALID_TRANSACTIONID);
                }
            }
            return transactionRes;

        }

        public void transaction(long clientId, TransactionRequest transactionRequest) {
            Wallet wallet = fetchWalletOfClient(clientId);
            TransactionType type = transactionRequest.getType();
            Currency currency = transactionRequest.getCurrency();
            double amount = transactionRequest.getAmount();
            double amountInINR = currency.convertToINR(amount);

            if(type == TransactionType.DEPOSIT){
                depositAmount(wallet, amountInINR, currency);
            }
            else if(type == TransactionType.WITHDRAW){
                withdrawAmount(wallet, amountInINR, currency);
            }
            else{
                transferAmountToReceiver(transactionRequest, wallet, amountInINR, currency);
            }
        }

        private void transferAmountToReceiver(@NotNull TransactionRequest transactionRequest, @NotNull Wallet wallet, double amountInINR, Currency currency) {
            long receiverId = transactionRequest.getReceiverId();
            Wallet reciverWallet = fetchWalletOfClient(receiverId);
            if(reciverWallet ==null){
                throw  new WalletNotFoundException("Wallet not found");
            }
            wallet.withdraw(amountInINR);
            walletRepository.save(wallet);
            reciverWallet.deposit(amountInINR);
            walletRepository.save(reciverWallet);
            Transaction senderTransaction = new Transaction(wallet, amountInINR, currency, TransactionType.TRANSFER);
            transactionRepository.save(senderTransaction);
            TransferTransaction transferTransaction = new TransferTransaction(senderTransaction, wallet, reciverWallet);
            transferTransactionRepository.save(transferTransaction);
        }

        private void withdrawAmount(Wallet wallet, double amountInINR, Currency currency) {
            wallet.withdraw(amountInINR);
            walletRepository.save(wallet);
            Transaction transaction = new Transaction(wallet, amountInINR, currency, TransactionType.WITHDRAW);
            transactionRepository.save(transaction);
        }

        private void depositAmount(Wallet wallet, double amountInINR, Currency currency) {
            wallet.deposit(amountInINR);
            walletRepository.save(wallet);
            Transaction transaction = new Transaction(wallet, amountInINR, currency, TransactionType.DEPOSIT);
            transactionRepository.save(transaction);
        }
    }
