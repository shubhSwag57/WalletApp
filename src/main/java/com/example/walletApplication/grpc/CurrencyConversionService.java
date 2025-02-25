package com.example.walletApplication.grpc;

import io.grpc.ManagedChannel;
import org.springframework.stereotype.Service;
import pb.CurrencyConversionGrpc;
import pb.CurrencyConversionOuterClass.CurrencyRequest;
import pb.CurrencyConversionOuterClass.CurrencyResponse;
import pb.CurrencyConversionOuterClass.Money;

@Service
public class CurrencyConversionService {

    private final CurrencyConversionGrpc.CurrencyConversionBlockingStub currencyConversionStub;

    public CurrencyConversionService(ManagedChannel grpcChannel) {
        this.currencyConversionStub = CurrencyConversionGrpc.newBlockingStub(grpcChannel);
    }

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        Money source = Money.newBuilder()
                .setAmount(amount)
                .setCurrency(fromCurrency)
                .build();

        CurrencyRequest request = CurrencyRequest.newBuilder()
                .setSource(source)
                .setTargetCurrency(toCurrency)
                .build();

        CurrencyResponse response = currencyConversionStub.convertCurrency(request);
        return response.getConverted().getAmount();
    }
}