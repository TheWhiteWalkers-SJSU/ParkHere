package com.thewhitewalkers.parkhere;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {

    private String chatId;
    private String emailUser1;
    private String emailUser2;
    public ArrayList<Message> messageList = new ArrayList<Message>();

    public Chat() {}

    public Chat(String id, String e1, String e2) {
        chatId = id;
        emailUser1 = e1;
        emailUser2 = e2;
        messageList.add(new Message());
    }

    public Chat(String id, String e1, String e2, ArrayList<Message> m) {
        chatId = id;
        emailUser1 = e1;
        emailUser2 = e2;
        messageList = m;
    }

    public String getChatId() {
        return chatId;
    }

    public String getEmailUser1() {
        return emailUser1;
    }

    public String getEmailUser2() {
        return emailUser2;
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    public void addMessage(Message m) {
        messageList.add(m);
    }
}
