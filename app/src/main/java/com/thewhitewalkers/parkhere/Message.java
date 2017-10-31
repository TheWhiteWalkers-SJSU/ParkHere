package com.thewhitewalkers.parkhere;

import java.io.Serializable;

public class Message implements Serializable{

    private String messageID;
    private String recipientID;
    private String senderID;
    private String listingID;
    private String subject;
    private String message;
    private int requestType;
    private boolean hasBeenRead;
    private boolean isDefault;
    public Message(String defaultMessage){
        message = defaultMessage;
        isDefault = true;
    }
    /**
     *
     * @param messageID is the message ID
     * @param to_ID is the recipient user ID
     * @param from_ID is the sender user ID
     * @param currentListingID is the currentListing ID
     * @param subject_Line is the subject line of the message
     * @param message_Line is the message body
     * @param message_RequestType is the request type of the message where:
     *                            0 - Owner Action Required (from renter user)
     *                            1- Booking Pending (from system)
     *                            2 - Booking Accepted (from system)
     *                            3 - Booking Denied (from system)
     */
    public Message(String messageID, String to_ID, String from_ID, String currentListingID, String subject_Line, String message_Line, int message_RequestType){
        this.messageID = messageID;
        recipientID = to_ID;
        senderID = from_ID;
        listingID = currentListingID;
        subject = subject_Line;
        message = message_Line;
        requestType = message_RequestType;
        hasBeenRead = false;
        isDefault = false;
    }
    public String getMessageID(){
        return messageID;
    }
    public String getRecipientID(){
        return recipientID;
    }
    public String getSenderID(){
        return senderID;
    }
    public String getListingID(){
        return listingID;
    }
    public String getSubject(){
        return subject;
    }
    public String getMessage(){
        return message;
    }
    public int getRequestType(){
        return requestType;
    }
    public boolean hasBeenRead(){
        return hasBeenRead;
    }
    public String toString(){
        if(isDefault){
            return message;
        }
        return subject + "\n" + message;
    }
}
