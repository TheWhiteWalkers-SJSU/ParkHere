package com.thewhitewalkers.parkhere;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Rohit on 10/27/17.
 */

public class Listing implements Serializable {

    String listingId;
    String listingName;
    String listingAddress;
    String listingDescription;
    String listingPrice;
    String ownerId;
    String ownerEmail;
    private TimeDetails timeDetails;
    String listingStatus;
    String renterId;
    String listingRating;

    public Listing() {

    }

    public Listing(String listingId, String listingName, String listingAddress, String listingDescription, String listingPrice, String ownerId, String ownerEmail, TimeDetails timeDetails, String listingStatus) {
        this.listingId = listingId;
        this.listingName = listingName;
        this.listingAddress = listingAddress;
        this.listingDescription = listingDescription;
        this.listingPrice = listingPrice;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
        this.timeDetails = timeDetails;
        this.listingStatus = listingStatus;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        if(listingId != null && !listingId.equals(""))
            this.listingId = listingId;
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        if(listingName != null && !listingName.equals(""))
            this.listingName = listingName;
    }

    public String getListingAddress() {
        return listingAddress;
    }

    public void setListingAddress(String listingAddress) {
        if(listingAddress != null && !listingAddress.equals(""))
            this.listingAddress = listingAddress;
    }

    public String getListingDescription() {
        return listingDescription;
    }

    public void setListingDescription(String listingDescription) {
        this.listingDescription = listingDescription;
    }

    public String getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(String listingPrice) {
        if (listingPrice != null && !listingPrice.equals("")) {
            String checkNum = listingPrice;
            try {
                Double.parseDouble(checkNum);
            } catch (NumberFormatException e) {
            }
            this.listingPrice = listingPrice;
        }
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        if(ownerId != null && !ownerId.equals(""))
            this.ownerId = ownerId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        if(listingStatus != null && !listingStatus.equals("")) {
            if (listingStatus.equals("available") || listingStatus.equals("booked"))
                this.listingStatus = listingStatus;
        }
    }

    public String getListingRating() {
        return listingRating;
    }

    public void setListingRating(String listingRating) {
        this.listingRating = listingRating;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        if(renterId != null && !renterId.equals(""))
            this.renterId = renterId;
    }

    public TimeDetails getTimeDetails() {
        return timeDetails;
    }

    public void setTimeDetails(TimeDetails timeDetails) {
        if(timeDetails != null)
            this.timeDetails = timeDetails;
    }

    public String getStartTime() {
        if(this.getTimeDetails() != null) {
            String starting = this.getTimeDetails().getStartingDate() +" @ "+ this.timeDetails.getStartingTime();
            return starting;
        }
        return "N/A";
    }

    public String getEndTime() {
        if(this.getTimeDetails() != null) {
            String ending = this.getTimeDetails().getEndingDate() +" @ "+ this.timeDetails.getEndingTime();
            return ending;
        }
        return "N/A";
    }

}
