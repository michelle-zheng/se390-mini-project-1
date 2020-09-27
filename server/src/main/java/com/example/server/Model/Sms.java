package com.example.server.Model;


public class Sms {
    private String number;
    private String message;

    public Sms(String number, String message) {
        this.number = number;
        this.message = message;
    }

    public String getNumber() { return this.number; }

    public String getMessage() { return this.message; }

    public void setNumber(String number) { this.number = number; }

    public void setMessage(String message) { this.message = message; }

}
