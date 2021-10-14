package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.model.Crypto;

import java.util.List;
import java.util.Optional;

public interface CryptoService {

    List<Crypto> getCryptoList(int count);

    Optional<Crypto> getCryptoBySymbol(String symbol);
}
