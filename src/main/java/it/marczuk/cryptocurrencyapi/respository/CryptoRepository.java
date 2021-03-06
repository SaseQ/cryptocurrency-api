package it.marczuk.cryptocurrencyapi.respository;

import it.marczuk.cryptocurrencyapi.model.Crypto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoRepository extends MongoRepository<Crypto, String> {

    Optional<Crypto> findCryptoBySymbol(String symbol);
}
