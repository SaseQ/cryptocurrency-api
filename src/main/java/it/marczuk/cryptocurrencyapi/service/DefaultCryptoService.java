package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.model.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DefaultCryptoService implements CryptoService {

    private final RestTemplate restTemplate;
    private static final String URL = "https://api.coingecko.com/api/v3/";

    @Autowired
    public DefaultCryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Crypto> getCrypto() {

        ResponseEntity<List<Crypto>> rateResponse =
                restTemplate.exchange(URL + "coins/markets?vs_currency=pln&per_page=25&page=1",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        return rateResponse.getBody();
    }
}
