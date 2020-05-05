package com.example.webfluxmongodb.sender;

import com.sendgrid.*;

import java.io.IOException;

public class MailSender {

    public void send() throws IOException {
        Email from = new Email("mpsh96@gmail.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("alyoyona.b@gmail.com ");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}

