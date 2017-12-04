package com.thewhitewalkers.parkhere;

public class RecentAddress {
    private String address;
    private double lat;
    private double lng;

    public RecentAddress(){

    }
    public RecentAddress(String addr, double lt, double lg){
        address = addr;
        lat = lt;
        lng = lg;
    }

    public String getAddress() {
        return address;
    }
    public double getLat() {
        return lat;
    }
    public double getLng() {
        return lng;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
