package com.thewhitewalkers.parkhere;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public ArrayList<Rating> ratingsList = new ArrayList<Rating>();;

    public User() {

    }

    public User(String userId, String email, String firstName, String lastName, String location, String phoneNumber) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        ratingsList.add(new Rating());
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
        double total = 0;
        double size = ratingsList.size();
        for(Rating r : ratingsList){
            if(r.getRating() != 100.0){
                total += r.getRating();
            }
        }

        return total / size;
    }

    //comparator to sort users by rating
    public static class RatingUserComparator implements Comparator<User> {
        public int compare(User user1, User user2) {
            if(user1.getAvgRating() < user2.getAvgRating()) {
                return -1;
            }
            else if(user1.getAvgRating() > user2.getAvgRating()) {
                return 1;
            }
            return 0;
        }
    }
}
