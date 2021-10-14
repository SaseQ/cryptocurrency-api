package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.model.FiatCurrency;
import it.marczuk.cryptocurrencyapi.model.User;
import it.marczuk.cryptocurrencyapi.respository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CryptoService cryptoService;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, CryptoService cryptoService) {
        this.userRepository = userRepository;
        this.cryptoService = cryptoService;
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
        throw new RuntimeException(mailValidation);
    }

    public String addCryptoToAccount(String mail, String cryptoShort, String interestedPrice) {
        User user = userRepository.findUserByMail(mail)
                .orElseThrow(() -> new RuntimeException("This mail not exist!"));
        Map<String, Double> cryptosUserMap = user.getCryptosMap();
        double valuePrice = convertToDouble(interestedPrice);

        addCryptoValidation(cryptoShort, cryptosUserMap, valuePrice);

        cryptosUserMap.put(cryptoShort, valuePrice);
        user.setCryptosMap(cryptosUserMap);
        updateUserCrypto(mail, user);
        return "Crypto: " + cryptoShort + " Price: " + valuePrice + " " + user.getFiatCurrency().name();
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

    private boolean cryptoValidation(String cryptoShort) {
        return cryptoService.getCryptoBySymbol(cryptoShort).isPresent();
    }

    private double convertToDouble(String interestedPrice) {
        return Double.parseDouble(interestedPrice);
    }

    private void addCryptoValidation(String cryptoShort, Map<String, Double> cryptosUserMap, double valuePrice) {
        if(!cryptoValidation(cryptoShort)) {
            throw new RuntimeException("This crypto not exist in database!");
        }
        if(cryptosUserMap.containsKey(cryptoShort) && cryptosUserMap.containsValue(valuePrice)) {
            throw new RuntimeException("This stack already exist!");
        }
    }

    private void updateUserCrypto(String mail, User user) {
        userRepository.deleteUserByMail(mail);
        userRepository.save(user);
    }
}
