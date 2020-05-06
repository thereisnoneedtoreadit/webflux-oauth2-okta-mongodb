package com.example.webfluxmongodb.sender;

import com.sendgrid.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailSender {

    @Value("${mail.sender.from.address}")
    private String fromAddress;

    @SneakyThrows
    public void send(String to, String subject, String content) {
        SendGrid sg = new SendGrid(System.getenv("mail_key"));
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(new Mail(new Email(fromAddress), subject, new Email(to), new Content("text/plain", content)).build());
        Response response = sg.api(request);
        log.info("Mail sended. Status code: {}", response.getStatusCode());
        log.debug("Response body: {}, response headers: {}", response.getBody(), response.getHeaders());
    }

}

