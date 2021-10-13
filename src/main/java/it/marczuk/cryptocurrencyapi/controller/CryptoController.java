package it.marczuk.cryptocurrencyapi.controller;

import it.marczuk.cryptocurrencyapi.model.Crypto;
import it.marczuk.cryptocurrencyapi.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/list")
    public List<Crypto> getCryptoList(@RequestParam String count) {
        return cryptoService.getCryptoList(Integer.parseInt(count));
    }
}
