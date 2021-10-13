package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.model.Crypto;

import java.util.List;

public interface CryptoService {

    List<Crypto> getCryptoList(int count);
}
