package com.thewhitewalkers.parkhere;

public class Rating {
    private double rating;
    private String comment;

    public Rating(){
        rating = 0.0;
        comment = "";
    }

    public Rating(double rating, String comment){
        this.rating = rating;
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}