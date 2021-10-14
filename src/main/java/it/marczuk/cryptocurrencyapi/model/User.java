package it.marczuk.cryptocurrencyapi.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document
public class User {

    private String name;
    private String mail;
    private FiatCurrency fiatCurrency;
    private Map<String, Double> cryptosMap = new HashMap<>();
}
