package com.thewhitewalkers.parkhere;;

import java.io.Serializable;
import java.util.ArrayList;

public class ParkingSpot implements Serializable {

    String parkingSpotId;
    String name;
    String address;
    String description;
    String ownerId;
    String ownerEmail;
    public ArrayList<Rating> ratingsList = new ArrayList<Rating>();
    int priorBookings;
    double lat;
    double lng;

    public ParkingSpot() {

    }

    public ParkingSpot(String parkingSpotId, String name, String address, String description, String ownerId, String ownerEmail) {
        this.parkingSpotId = parkingSpotId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
        this.priorBookings = 0;
        ratingsList.add(new Rating());
    }

    public String getParkingSpotId() {
        return parkingSpotId;
    }

    public void setparkingSpotId(String parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
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
  
    public int getPriorBookings() {
        return priorBookings;
    }

    public void setPriorBookings(int num) {
        priorBookings = num;
    }

    public void setLatLng(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat(){
        return lat;
    }
    public double getLng(){
        return lng;
    }

    public double getAvgRating(){
        double total = 0;
        double size = ratingsList.size();
        for(Rating r : ratingsList){
            if(r.getRating() != 100.0){
                total += r.getRating();
            }
        }

        return total / size;
    }
}