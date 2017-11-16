package com.thewhitewalkers.parkhere;

import java.util.ArrayList;

/**
 * Created by Rohit on 10/27/17.
 */

public class User {

    String userId;
    String email;
    String firstName;
    String lastName;
    String location;
    String phoneNumber;
    double sumOfRatings;
    int numOfRatings;
    double avgRating;

    public User() {

    }

    public User(String userId, String email, String firstName, String lastName, String location, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        sumOfRatings = 0;
        numOfRatings = 0;
        avgRating = 0;
    }
    public String getUserId(){
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getAvgRating(){
        return avgRating;
    }

    public void setAvgRating(double newRating){
        sumOfRatings += newRating;
        numOfRatings++;
        avgRating = sumOfRatings / numOfRatings;
    }
}
