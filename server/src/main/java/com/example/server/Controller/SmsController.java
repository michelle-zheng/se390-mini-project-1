package com.example.server.Controller;

import com.example.server.Model.Sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class SmsController {
    private static Logger logger = Logger.getLogger(SmsController.class.getName());

    @Value("${twilio_phone_number}")
    private String fromNumber;

    @Autowired
    public SmsController(
        @Value("${twilio_account_sid}") String ACCOUNT_ID,
        @Value("${twilio_auth_token}") String AUTH_TOKEN) {
            Twilio.init(ACCOUNT_ID, AUTH_TOKEN);
        }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMessage(@RequestBody Sms smsDetails) {
        Message message = Message.creator(new PhoneNumber(smsDetails.getNumber()),
                new PhoneNumber(fromNumber),smsDetails.getMessage()).create();
        logger.log(Level.INFO, "Sent message w/ sid: " + message.getSid());
    }

    @PostMapping("/receive")
    @ResponseBody
    public String receiveMessage(HttpServletRequest request) {
        String msg = request.getParameter("Body");
        String response;
        if (msg.contains("Hi")) {
            response = "Hello. Please message back your current location and the destination. " +
                    "i.e. from [location] to [location]";
        } else if (msg.contains("from") && msg.contains("to")) { // parse locations
            response = "Processing your route";
        } else {
            response = "Failed to read your location request";
        }
//        Body body = new Body.Builder(response).build();
//        com.twilio.twiml.messaging.Message message = new com.twilio.twiml.messaging.Message
//                .Builder().body(body).build();
//        MessagingResponse twinml = new MessagingResponse
//                .Builder().message(message).build();

        logger.log(Level.INFO, "Received a message");
//        return twinml.toXml();
        return response;
    }



}
