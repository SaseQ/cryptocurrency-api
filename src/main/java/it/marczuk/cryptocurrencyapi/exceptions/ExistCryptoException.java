package it.marczuk.cryptocurrencyapi.exceptions;

public class ExistCryptoException extends RuntimeException{

    public ExistCryptoException(String message) {
        super(message);
    }
}
