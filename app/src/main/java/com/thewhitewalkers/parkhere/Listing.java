package com.thewhitewalkers.parkhere;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Rohit on 10/27/17.
 */

public class Listing implements Serializable{

    String listingId;
    String listingName;
    String listingAddress;
    String listingDescription;
    String listingPrice;
    String ownerId;
    String listingStatus;
    String listingRating;

    public Listing() {

    }

    public Listing(String listingId, String listingName, String listingAddress, String listingDescription, String listingPrice, String ownerId, String listingStatus) {
        this.listingId = listingId;
        this.listingName = listingName;
        this.listingAddress = listingAddress;
        this.listingDescription = listingDescription;
        this.listingPrice = listingPrice;
        this.ownerId = ownerId;
        this.listingStatus = listingStatus;
    }


    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public String getListingAddress() {
        return listingAddress;
    }

    public void setListingAddress(String listingAddress) {
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
        this.listingPrice = listingPrice;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }

    public String getListingRating() {
        return listingRating;
    }

    public void setListingRating(String listingRating) {
        this.listingRating = listingRating;
    }

}
