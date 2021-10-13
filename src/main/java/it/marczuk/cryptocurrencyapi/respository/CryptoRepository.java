package it.marczuk.cryptocurrencyapi.respository;

import it.marczuk.cryptocurrencyapi.model.Crypto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends MongoRepository<Crypto, String> {
}
