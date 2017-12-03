package com.thewhitewalkers.parkhere;

import java.util.Date;

public class Message {
    private String email;
    private String body;
    private Date date;


    public Message() {
        email = "ParkHere";
        body = "You can begin messaging.";
        date = new Date(System.currentTimeMillis());
    }

    public Message(String e, String b) {
        email = e;
        body = b;
        date = new Date(System.currentTimeMillis());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        email = e;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String b) {
        body = b;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date d) {
        date = d;
    }
}
