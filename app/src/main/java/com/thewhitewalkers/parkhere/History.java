package com.thewhitewalkers.parkhere;

import java.util.ArrayList;

//NFR
public class History {

    private String historyID;
    private String userID;
    private ArrayList<RecentAddress> recentHistory;

    public History() {

    }

    public History(String hID, String uID, String addr, double lat, double lng) {
        historyID = hID;
        userID = uID;
        recentHistory = new ArrayList<>();
        recentHistory.add(0, new RecentAddress(addr, lat, lng));
    }

    public String getHistoryID() {
        return historyID;
    }

    public String getUserID() {
        return userID;
    }

    public ArrayList<RecentAddress> getRecentHistory() {
        return recentHistory;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void addAddress(String addr, double lat, double lng){
        recentHistory.add(0, new RecentAddress(addr, lat, lng));
    }
}
