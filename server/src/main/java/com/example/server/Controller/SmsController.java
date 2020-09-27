package com.example.server;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SmsController {
    @Autowired
    public SmsController(
        @Value("${twilio_account_sid}") String ACCOUNT_ID,
        @Value("${twilio_auth_token}") String AUTH_TOKEN) {
            Twilio.init(ACCOUNT_ID, AUTH_TOKEN);
        }

    @PostMapping("send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMessage(@RequestBody SMSDetails smsDetails) {

    }

}
