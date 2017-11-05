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

    public TimeDetails(){

    }

    public TimeDetails(String s_Date, String e_Date, String s_Time, boolean s_IsAM, String e_Time, boolean e_IsAM){
        startingDate = s_Date;
        endingDate = e_Date;
        startingTime = s_Time;
        endingTime = e_Time;
        startingIsAM = s_IsAM;
        endingIsAM = e_IsAM;

        if(checkDateFormat(s_Date) && checkDateFormat(e_Date)){
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
        int s_h = cleanTime(starting[0]);
        int s_m = cleanTime(starting[1]);
        int e_h = cleanTime(ending[0]);
        int e_m = cleanTime(ending[1]);
        double total_hours = 0;
        double total_minutes = Math.abs(e_m - s_m);
        if((startingIsAM && !endingIsAM) || (!startingIsAM && endingIsAM)){
            // 9am to 1pm (4) //9pm to 1am (4)
            // 9am to 10pm (13)
            total_hours = (12 - s_h) + e_h;
        }
        else{
            total_hours = e_h - s_h;
        }


        return total_hours + (total_minutes/60);
    }
    public double getDays(){
        long days = ((ending.getTime()-starting.getTime())/1000)/60/60/24;
        return days+1;
    }
    public boolean hasConflict(){
        //MM/DD/YYYY
        //HH:MM:AM

        return false;
    }
    /*
    public boolean withinSlot(TimeDetails bookingToCheck){
        return withinDate(bookingToCheck) && withinTime(bookingToCheck);
    }
    public boolean withinDate(TimeDetails bookingToCheck){
        return this.getStarting().before(bookingToCheck.getStarting()) && this.getEnding().after(bookingToCheck.getEnding());
    }
    public boolean withinTime(TimeDetails bookingToCheck){
        return true;
    }
    */
    public String[] dateSplit(String date){
        String[] split = date.split("[.-/]");
        return split;
    }
    public String[] timeSplit(String time){
        String[] split = time.split(":");
        return split;
    }
    public boolean checkDateValid(String s_Date, String e_Date){
        String[] split1 = dateSplit(s_Date);
        String[] split2 = dateSplit(e_Date);

        int m1 = cleanDate(split1[0]);
        int d1 = cleanDate(split1[1]);
        int y1 = Integer.parseInt(split1[2]);
        int m2 = cleanDate(split2[0]);
        int d2 = cleanDate(split2[1]);
        int y2 = Integer.parseInt(split2[2]);

        Date date1 = new Date(y1, m1, d1);
        Date date2 = new Date(y2, m2, d2);

        return date1.before(date2);
    }
    public boolean checkDateFormat(String date){
        String[] split = dateSplit(date);
        if(split.length == 3 && split[0].length() < 3 && split[1].length() < 3 && split[2].length() == 4){
            int m = cleanDate(split[0]);
            int d = cleanDate(split[1]);

            if(m > 0 && m <= 12 && d >= 1 && d <= 31){
                return true;
            }
        }
        return false;
    }
    public int cleanDate(String toClean){
        int cleaned = 0;
        if(toClean.length() == 2 && toClean.startsWith("0")){ //if HH starts with 0
            cleaned = Integer.parseInt(toClean.substring(1));
        }
        else{
            cleaned = Integer.parseInt(toClean);
        }
        return cleaned;
    }
    public boolean checkTimeFormat(String time){
        String[] split = timeSplit(time);
        if(split.length == 2 && split[0].length() < 3 && split[0].length() > 0 && split[1].length() < 3 && split[1].length() > 0){
            int h = cleanTime(split[0]);
            int m = cleanTime(split[1]);

            if(h > 0 && h <= 12 && m >= 0 && m <= 59){
                return true;
            }
        }
        return false;
    }
    public int cleanTime(String toClean){
        int cleaned = 0;
        if(toClean.length() == 2 && toClean.startsWith("0")){ //if HH starts with 0
            cleaned = Integer.parseInt(toClean.substring(1));
        }
        else{
            cleaned = Integer.parseInt(toClean);
        }
        return cleaned;
    }
    public Date getStarting(){
        return starting;
    }
    public Date getEnding(){
        return ending;
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
