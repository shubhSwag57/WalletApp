    package com.example.walletApplication.services;

    import com.example.walletApplication.DTO.TransactionHistoryRes;
    import com.example.walletApplication.Exceptions.UnauthorizedAccessException;
    import com.example.walletApplication.Exceptions.WalletNotFoundException;
    import com.example.walletApplication.entity.Client;
    import com.example.walletApplication.entity.Transaction;
    import com.example.walletApplication.entity.TransferTransaction;
    import com.example.walletApplication.enums.Currency;
    import com.example.walletApplication.entity.Wallet;
    import com.example.walletApplication.enums.TransactionType;
    import com.example.walletApplication.repository.ClientRepository;
    import com.example.walletApplication.repository.TransactionRepository;
    import com.example.walletApplication.repository.TransferTransactionRepository;
    import com.example.walletApplication.repository.WalletRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.*;
    import java.util.stream.Collectors;

    @Service
    public class WalletService  {
        private WalletRepository walletRepository;

        private ClientRepository clientRepository;

        private TransactionRepository transactionRepository;

        private TransferTransactionRepository transferTransactionRepository;

        @Autowired
        public WalletService(ClientRepository clientRepository, WalletRepository walletRepository,TransactionRepository transactionRepository,TransferTransactionRepository transferTransactionRepository) {
            this.clientRepository = clientRepository;
            this.walletRepository = walletRepository;
            this.transactionRepository = transactionRepository;
            this.transferTransactionRepository = transferTransactionRepository;
        }

        public void deposit(long userId, double amount, Currency currency){
            Wallet wallet = fetchWalletOfClient(userId);
            double amountInINR = currency.convertToINR(amount);
            wallet.deposit(amountInINR);
            walletRepository.save(wallet);
            Transaction transaction = new Transaction(wallet, amountInINR, currency, TransactionType.DEPOSIT);
            transactionRepository.save(transaction);
        }

        public void withdraw(long userId, double amount,Currency currency){
            Wallet wallet = fetchWalletOfClient(userId);
            double amountInINR = currency.convertToINR(amount);
            wallet.withdraw(amountInINR);
            walletRepository.save(wallet);

            Transaction transaction = new Transaction(wallet, amountInINR, currency, TransactionType.WITHDRAW);
            transactionRepository.save(transaction);
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

        public void transferMoney(long clientId, Long receiverId, Double amount, Currency currency) {

            Wallet senderWallet = fetchWalletOfClient(clientId);
            Wallet receiverWallet = fetchWalletOfClient(receiverId);

            double amountInINR = currency.convertToINR(amount);

            senderWallet.withdraw(amountInINR);
            receiverWallet.deposit(amountInINR);

            Transaction senderTransaction = new Transaction(senderWallet, amountInINR, currency, TransactionType.TRANSFER);
            transactionRepository.save(senderTransaction);

            TransferTransaction transferTransaction = new TransferTransaction(senderTransaction, senderWallet, receiverWallet);
            transferTransactionRepository.save(transferTransaction);

        }

        public List<TransactionHistoryRes> walletHistory(Long userId) {
            Wallet wallet = fetchWalletOfClient(userId);
            List<TransactionHistoryRes> depositsAndWithdrawals = transactionRepository.findDepositsAndWithdrawals(wallet);
            List<TransactionHistoryRes> transferTransactions = transactionRepository.findWalletTransferTransHistory(wallet);
            List<TransactionHistoryRes> history = new ArrayList<>();
            history.addAll(depositsAndWithdrawals);
            history.addAll(transferTransactions);
            history.sort(Comparator.comparing(TransactionHistoryRes::getTimestamp).reversed());

            return history;


        }

        public List<TransactionHistoryRes> walletTransactionHistoryById(Long clientId) {
                return null;
        }
    }
