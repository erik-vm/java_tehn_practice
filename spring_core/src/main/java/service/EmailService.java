package service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void send(String subject) {
        System.out.println("Sending message with subject " + subject);
    }
}