package com.example.server.Controller;

import com.example.server.UserRecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.logging.Logger;

@RestController
public class RegisterController {
    private static Logger logger = Logger.getLogger(SmsController.class.getName());
    public static String SUCCESS ="success";
    public static String ALREADY_EXISTS = "exists";
    public static String FAILURE = "failure";

    public RegisterController(){
        UserRecord.init();
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestParam(name="number")String number){

        // check if number is empty
        if(null == number || number.equals("")){
            return FAILURE;
        }
        // check if user registered
        if(!UserRecord.isValidUser(number)){
            UserRecord.add(number); // add new user
            UserRecord.save();
            return SUCCESS;
        }

        // user already exists
        return ALREADY_EXISTS;
    }


}
