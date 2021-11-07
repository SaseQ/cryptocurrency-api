package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.exceptions.MailValidationException;
import it.marczuk.cryptocurrencyapi.model.FiatCurrency;
import it.marczuk.cryptocurrencyapi.model.User;
import it.marczuk.cryptocurrencyapi.respository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUserToDatabase(String name, String mail, String fiatCurrency) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setMail(mail);
        newUser.setFiatCurrency(FiatCurrency.valueOf(fiatCurrency.toUpperCase()));

        String mailValidation = registryMailValidation(newUser.getMail());
        if(mailValidation.equals("correct")) {
            User saveUser = userRepository.save(newUser);
            log.info("Add new user to database: {}", saveUser.getMail());
            return saveUser;
        }
        throw new MailValidationException(mailValidation);
    }

    private String registryMailValidation(String mail) {
        List<String> mailList = userRepository.findAll()
                .stream().map(User::getMail).collect(Collectors.toList());

        if(mailList.contains(mail)) {
            return "Mail already exist in database!";
        }
        if(!mail.contains("@")) {
            return "Mail not contain @!";
        }

        return "correct";
    }

}
