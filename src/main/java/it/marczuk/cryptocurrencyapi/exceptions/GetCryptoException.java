package it.marczuk.cryptocurrencyapi.exceptions;

public class GetCryptoException extends RuntimeException{

    public GetCryptoException(String message) {
        super(message);
    }
}
