package it.marczuk.cryptocurrencyapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendInformationMail(String mail, Double price, String symbol) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("auth@socialbook.com");
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject("Crypto Alert: " + symbol + "!");
        simpleMailMessage.setText(symbol + " price is: " + price );
        javaMailSender.send(simpleMailMessage);
        log.info("Mail send to: {}", mail);
    }
}
