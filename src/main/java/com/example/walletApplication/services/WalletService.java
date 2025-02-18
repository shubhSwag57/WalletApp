    package com.example.walletApplication.services;

    import com.example.walletApplication.Exceptions.UnauthorizedAccessException;
    import com.example.walletApplication.Exceptions.WalletNotFoundException;
    import com.example.walletApplication.entity.Client;
    import com.example.walletApplication.enums.Currency;
    import com.example.walletApplication.entity.Wallet;
    import com.example.walletApplication.repository.ClientRepository;
    import com.example.walletApplication.repository.WalletRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class WalletService  {
        private WalletRepository walletRepository;

        private ClientRepository clientRepository;

        @Autowired
        public WalletService(ClientRepository clientRepository, WalletRepository walletRepository) {
            this.clientRepository = clientRepository;
            this.walletRepository = walletRepository;
        }

        public void deposit(long userId, double amount, Currency currency){
            Wallet wallet = fetchWalletOfClient(userId);
            double amountInINR = currency.convertToINR(amount);
            wallet.deposit(amountInINR);
            walletRepository.save(wallet);
        }

        public void withdraw(long userId, double amount,Currency currency){
            Wallet wallet = fetchWalletOfClient(userId);
            double amountInINR = currency.convertToINR(amount);
            wallet.withdraw(amountInINR);
            walletRepository.save(wallet);
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
            withdraw(clientId,amount,currency);
            deposit(receiverId,amount,currency);
        }
    }
