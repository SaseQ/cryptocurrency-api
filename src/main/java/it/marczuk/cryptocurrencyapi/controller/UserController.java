package it.marczuk.cryptocurrencyapi.controller;

import it.marczuk.cryptocurrencyapi.model.User;
import it.marczuk.cryptocurrencyapi.service.UserCryptoService;
import it.marczuk.cryptocurrencyapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/crypto/user")
public class UserController {

    private final UserService userService;
    private final UserCryptoService userCryptoService;

    @Autowired
    public UserController(UserService userService, UserCryptoService userCryptoService) {
        this.userService = userService;
        this.userCryptoService = userCryptoService;
    }

    @PostMapping("/add")
    public User addNewUser(@RequestParam String name,
                           @RequestParam String mail,
                           @RequestParam(name = "fiat_currency") String fiatCurrency) {
        return userService.addUserToDatabase(name, mail, fiatCurrency);
    }

    @PostMapping("/save_crypto")
    public String saveCryptoToStack(@RequestParam String mail,
                                    @RequestParam(name = "crypto_short") String cryptoShort,
                                    @RequestParam String price) {
        return userCryptoService.addCryptoToAccount(mail, cryptoShort, price);
    }
}
