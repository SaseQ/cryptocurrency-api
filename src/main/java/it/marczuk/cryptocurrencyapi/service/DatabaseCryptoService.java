package it.marczuk.cryptocurrencyapi.service;

import it.marczuk.cryptocurrencyapi.model.Crypto;
import it.marczuk.cryptocurrencyapi.respository.CryptoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class DatabaseCryptoService {

    private final RestTemplate restTemplate;
    private final CryptoRepository cryptoRepository;

    private final Logger log = LoggerFactory.getLogger(DatabaseCryptoService.class);

    private static final String FIAT_CURRENCY = "pln";
    private static final String PER_PAGE = "50";
    private static final String PAGE = "1";

    @Autowired
    public DatabaseCryptoService(RestTemplate restTemplate, CryptoRepository cryptoRepository) {
        this.restTemplate = restTemplate;
        this.cryptoRepository = cryptoRepository;
    }

    @Scheduled(cron = "*/30 * * * * *")
    private void addCryptoToDatabase() {
        Optional<List<Crypto>> optionalCryptoList = Optional.of(cryptoGetRest());
        cryptoRepository.saveAll(optionalCryptoList.orElseThrow());

        //Log
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        log.info("Add cryptos to database: {}", time);
    }

    private List<Crypto> cryptoGetRest() {
        final String URL = "https://api.coingecko.com/api/v3/";

        ResponseEntity<List<Crypto>> rateResponse =
                restTemplate.exchange(
                        URL + "coins/markets?"
                                + "vs_currency=" + FIAT_CURRENCY
                                + "&" + "per_page=" + PER_PAGE
                                + "&" + "page=" + PAGE,
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        return rateResponse.getBody();
    }
}
