package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.exceptions.GetCryptoException;
import it.marczuk.cryptocurrencyapi.model.Crypto;
import it.marczuk.cryptocurrencyapi.respository.CryptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        if(count<=50) {
            return cryptoRepository.findAll().stream().filter(e -> e.getMarketCapRank()<=count).collect(Collectors.toList());
        }
       throw new GetCryptoException("count must be less than 50!");
    }

    @Override
    public Optional<Crypto> getCryptoBySymbol(String symbol) {
        return cryptoRepository.findCryptoBySymbol(symbol);
    }
}
