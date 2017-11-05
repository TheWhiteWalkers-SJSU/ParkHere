package com.thewhitewalkers.parkhere;

public class TimeDetails {
    private String startingDate;
    private String endingDate;
    private String startingTime;
    private String endingTime;
    private boolean startingIsAM;
    private boolean endingIsAM;

    public TimeDetails(String s_Date, String e_Date, String s_Time, boolean s_IsAM, String e_Time, boolean e_IsAM){
        startingDate = s_Date;
        endingDate = e_Date;
        startingTime = s_Time;
        endingTime = e_Time;
        startingIsAM = s_IsAM;
        endingIsAM = e_IsAM;
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
