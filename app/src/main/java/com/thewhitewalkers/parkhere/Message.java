package com.thewhitewalkers.parkhere;

public class Message {
//    private Time dateTime;
//    private User user;
    private String email;
    private String body;


    public Message() {
        email = "ParkHere";
        body = "You can begin messaging.";
    }

    public Message(String e, String b) {
//        user = u;
//        timeDetails = t;
        email = e;
        body = b;
//        email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }
}
