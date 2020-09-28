package com.example.server.Model;


import com.example.server.Config;
import com.example.server.Controller.GoogleMapAPI;
import com.example.server.Controller.SmsController;
import com.google.maps.errors.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sms {
    private static Logger logger = Logger.getLogger(Sms.class.getName());
    static private Config instance = Config.getInstance();

    private String number;
    private String message;

//    @Value("${twilio_phone_number}")
//    private String fromNumber;

    public Sms(String number) {
        this.number = number;
    }

    public Sms(String number, String message) {
        this.number = number;
        this.message = message;
    }

    public String getNumber() { return this.number; }

    public String getMessage() { return this.message; }

    public void setNumber(String number) { this.number = number; }

    public void setMessage(String message) { this.message = message; }

    public void sendMessage() {
        Message msg = Message.creator(new PhoneNumber(number), // to
                new PhoneNumber(instance.getProperty("twilio_phone_number")), message).create();     // from
        logger.log(Level.INFO, "Sent message w/ sid: " + msg.getSid());
    }
}
