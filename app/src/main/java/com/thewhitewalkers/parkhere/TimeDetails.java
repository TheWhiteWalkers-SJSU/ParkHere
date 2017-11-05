package com.thewhitewalkers.parkhere;

import java.util.Date;

public class TimeDetails {
    private String startingDate;
    private String endingDate;
    private String startingTime;
    private String endingTime;
    private Date starting;
    private Date ending;
    private boolean startingIsAM;
    private boolean endingIsAM;
    private double price;

    public TimeDetails(String s_Date, String e_Date, String s_Time, boolean s_IsAM, String e_Time, boolean e_IsAM){
        startingDate = s_Date;
        endingDate = e_Date;
        startingTime = s_Time;
        endingTime = e_Time;
        startingIsAM = s_IsAM;
        endingIsAM = e_IsAM;

        String[] starting_Date = dateSplit(startingDate);
        int d_s = Integer.parseInt(starting_Date[1]);
        int m_s = Integer.parseInt(starting_Date[0]);
        int y_s = Integer.parseInt(starting_Date[2]);
        starting = new Date(y_s, m_s, d_s);
        String[] ending_Date = dateSplit(endingDate);
        int d_e = Integer.parseInt(ending_Date[1]);
        int m_e = Integer.parseInt(ending_Date[0]);
        int y_e = Integer.parseInt(ending_Date[2]);
        ending = new Date(y_e, m_e, d_e);
    }

    public String setPrice(String rate){
        double hourly_rate = Double.parseDouble(rate);
        price = hourly_rate * getTotalHours();
        return "$" + price;
    }
    public double getTotalHours(){
        return getHours() * getDays();
    }
    public double getHours(){
        String[] starting = timeSplit(this.getStartingTime());
        String[] ending = timeSplit(this.getEndingTime());

        return 1;
    }
    public double getDays(){
        long days = ((ending.getTime()-starting.getTime())/1000)/60/60/24;
        return days;
    }
    public boolean hasConflict(){
        //MM/DD/YYYY
        //HH:MM:AM

        return false;
    }
    public boolean withinSlot(TimeDetails bookingToCheck){
        return withinDate(bookingToCheck) && withinTime(bookingToCheck);
    }
    public boolean withinDate(TimeDetails bookingToCheck){
        String[] slotStarting = dateSplit(this.getStartingDate());
        String[] slotEnding = dateSplit(this.getEndingDate());
        String[] checkStarting = dateSplit(bookingToCheck.getStartingDate());
        String[] checkEnding = dateSplit(bookingToCheck.getEndingDate());
        //Check Slot's Starting MM < C_MM && Slot's Ending MM > C_MM
        if(slotStarting[0].compareTo(checkStarting[0]) >= 0 && slotEnding[2].compareTo(checkEnding[2]) <= 0 ){
            if(slotStarting[1].compareTo(checkStarting[1]) >= 0 && slotEnding[1].compareTo(checkEnding[1]) <= 0 ){
                if(slotStarting[2].compareTo(checkStarting[2]) >= 0 && slotEnding[2].compareTo(checkEnding[2]) <= 0 ){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean withinTime(TimeDetails bookingToCheck){
        return true;
    }
    public String[] dateSplit(String date){
        String[] split = date.split("/");
        return split;
    }
    public String[] timeSplit(String time){
        String[] split = time.split(":");
        return split;
    }
    public String getStartingDate() {
        return startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public String getEndingTime(){
        return endingTime;
    }
}
