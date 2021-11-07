package it.marczuk.cryptocurrencyapi.exceptions;

public class MailValidationException extends RuntimeException{

    public MailValidationException(String message) {
        super(message);
    }
}
