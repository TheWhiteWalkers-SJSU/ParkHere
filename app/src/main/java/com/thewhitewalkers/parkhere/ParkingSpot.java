package com.thewhitewalkers.parkhere;


import java.io.Serializable;
import java.util.Comparator;
import android.os.Parcelable;

public class ParkingSpot implements Serializable {

    String listingId;
    String name;
    String address;
    String description;
    String ownerId;
    String ownerEmail;
    String listingRating;
    Integer priorBookings;

    public ParkingSpot() {

    }

    public ParkingSpot(String listingId, String name, String address, String description, String ownerId, String ownerEmail) {
        this.listingId = listingId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
        this.listingRating = listingRating;
        this.priorBookings = priorBookings;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getListingRating() {
        return listingRating;
    }

    public void setListingRating(String listingRating) {
        this.listingRating = listingRating;
    }

    public Integer getPriorBookings() {
        return priorBookings;
    }

    public void setPriorBookings(Integer priorBookings) {
        this.priorBookings = priorBookings;
    }

}
