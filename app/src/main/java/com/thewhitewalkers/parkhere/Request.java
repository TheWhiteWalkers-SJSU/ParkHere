package com.thewhitewalkers.parkhere;

import java.io.Serializable;

public class Request implements Serializable{

    private String requestID;
    private String recipientID;
    private String senderID;
    private String senderEmail;
    private String listingID;
    private String subject;
    private String message;
    private TimeDetails timeDetails;
    private int requestType;
    private boolean hasBeenRead;
    private boolean isDefault;

    public Request() {
    }

    /**
     *
     * @param requestID is the message ID
     * @param to_ID is the recipient user ID
     * @param from_ID is the sender user ID
     * @param from_email is the sender email
     * @param currentListingID is the currentListing ID
     * @param subject_Line is the subject line of the message
     * @param message_Line is the message body
     * @param tD is the time details
     * @param message_RequestType
     */
    public Request(String requestID, String to_ID, String from_ID, String from_email, String currentListingID, String subject_Line, String message_Line, TimeDetails tD,  int message_RequestType){
        this.requestID = requestID;
        recipientID = to_ID;
        senderID = from_ID;
        senderEmail = from_email;
        listingID = currentListingID;
        subject = subject_Line;
        message = message_Line;
        timeDetails = tD;
        requestType = message_RequestType;
        hasBeenRead = false;
        isDefault = false;
    }

    public String getRequestID() {
        return requestID;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getSenderEmail(){ return senderEmail; }

    public String getListingID() {
        return listingID;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public TimeDetails getTimeDetails() { return timeDetails; }


    public int getRequestType() {
        return requestType;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setListingID(String listingID) {
        this.listingID = listingID;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
