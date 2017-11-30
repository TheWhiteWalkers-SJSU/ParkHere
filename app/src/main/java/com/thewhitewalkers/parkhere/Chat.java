package com.thewhitewalkers.parkhere;


import java.util.ArrayList;

public class Chat {

    private String emailUser1;
    private String emailUser2;

    private String email;
    public ArrayList<Message> messageList = new ArrayList<Message>();

    public Chat(String e1, String e2) {
        emailUser1 = e1;
        emailUser2 = e2;
        messageList.add(new Message());
    }

    public String getEmailUser1() {
        return emailUser1;
    }

    public String getEmailUser2() {
        return emailUser2;
    }

//    public String getNameAddress() {
//        name = listing.getListingName();
//        address = listing.getListingAddress();
//        return name + " @ " + address;
//    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void addMessage(Message m) {
        messageList.add(m);
    }
}
