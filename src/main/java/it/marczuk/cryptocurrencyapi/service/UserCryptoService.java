package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.exceptions.ExistCryptoException;
import it.marczuk.cryptocurrencyapi.model.Crypto;
import it.marczuk.cryptocurrencyapi.model.User;
import it.marczuk.cryptocurrencyapi.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserCryptoService {

    private final UserRepository userRepository;
    private final CryptoService cryptoService;
    private final MailService mailService;

    @Autowired
    public UserCryptoService(UserRepository userRepository, CryptoService cryptoService, MailService mailService) {
        this.userRepository = userRepository;
        this.cryptoService = cryptoService;
        this.mailService = mailService;
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

    @Scheduled(cron = "*/50 * * * * *")
    public void cryptoPriceInformation() {
        List<Crypto> cryptoList = cryptoService.getCryptoList(50);
        List<User> userList = userRepository.findAll();

        userList.forEach(u -> {
            Map<String, Double> uCryptosMap = u.getCryptosMap();
            cryptoList.forEach(e -> {
                if(uCryptosMap.containsKey(e.getSymbol())) {
                    if(e.getCurrentPrice() <= uCryptosMap.get(e.getSymbol())) {
                        mailService.sendInformationMail(u.getMail(), e.getCurrentPrice(), e.getSymbol());
                        uCryptosMap.remove(e.getSymbol());
                        updateUserCryptoMap(u, uCryptosMap);
                    }
                }
            });
        });
    }

    private boolean cryptoValidation(String cryptoShort) {
        return cryptoService.getCryptoBySymbol(cryptoShort).isPresent();
    }

    private double convertToDouble(String interestedPrice) {
        return Double.parseDouble(interestedPrice);
    }

    private void addCryptoValidation(String cryptoShort, Map<String, Double> cryptosUserMap, double valuePrice) {
        if(!cryptoValidation(cryptoShort)) {
            throw new ExistCryptoException("This crypto not exist in database!");
        }
        if(cryptosUserMap.containsKey(cryptoShort) && cryptosUserMap.containsValue(valuePrice)) {
            throw new ExistCryptoException("This stack already exist!");
        }
    }

    private void updateUserCrypto(String mail, User user) {
        userRepository.deleteUserByMail(mail);
        userRepository.save(user);
    }

    private void updateUserCryptoMap(User user, Map<String, Double> cryptosMap) {
        user.setCryptosMap(cryptosMap);
        updateUserCrypto(user.getMail(), user);
    }
}
