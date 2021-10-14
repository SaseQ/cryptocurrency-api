package it.marczuk.cryptocurrencyapi.respository;

import it.marczuk.cryptocurrencyapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByMail(String mail);

    void deleteUserByMail(String mail);
}
