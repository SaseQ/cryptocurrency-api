package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.model.Crypto;
import it.marczuk.cryptocurrencyapi.respository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCryptoService implements CryptoService {

    private final CryptoRepository cryptoRepository;

    @Autowired
    public DefaultCryptoService(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public List<Crypto> getCryptoList(int count) {
       return cryptoRepository.findAll().stream().filter(e -> e.getMarketCapRank()<=count).collect(Collectors.toList());
    }
}
