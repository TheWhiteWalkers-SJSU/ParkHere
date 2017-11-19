package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class TimeDetails12Test extends TestCase {
    public TimeDetails12Test(){} //default constructor
    TimeDetails timeDetails1;
    TimeDetails timeDetails2;
    TimeDetails timeDetails3;
    TimeDetails timeDetails4;
    TimeDetails timeDetails5;
    TimeDetails timeDetails6;
    TimeDetails timeDetails7;
    TimeDetails timeDetails8;
    TimeDetails timeDetails9;
    TimeDetails timeDetails10;
    double delta = 0.01;
    @Before
    protected void setUp(){
        String startingDate  = "11/8/2017";
        String endingDate = "11/11/2017";
        String startingTime1 = "12:30";
        String endingTime1 = "12:10";
        boolean isAm = true;
        // 12:00 AM to 12:20 AM
        timeDetails1 = new TimeDetails(startingDate, endingDate, startingTime1, isAm, endingTime1, !isAm);

        // 12:00 PM to 12:20 PM
        timeDetails2 = new TimeDetails(startingDate, endingDate, startingTime1, !isAm, endingTime1, isAm);

        String startingTime2 = "1:00";
        String endingTime2 = "12:00";
        // 1:00 AM to 12:00 PM
        timeDetails3 = new TimeDetails(startingDate, endingDate, startingTime2, isAm, endingTime2, !isAm);

        // 1:00 PM to 12:00 AM
        timeDetails4 = new TimeDetails(startingDate, endingDate, startingTime2, !isAm, endingTime2, isAm);

        // 1:00 AM to 12:00 AM
        timeDetails5 = new TimeDetails(startingDate, endingDate, startingTime2, isAm, endingTime2, isAm);

        // 1:00 PM to 12:00 PM
        timeDetails6 = new TimeDetails(startingDate, endingDate, startingTime2, !isAm, endingTime2, !isAm);

        String startingTime3 = "12:00";
        String endingTime3 = "1:00";
        // 12:00 AM to 1:00 PM
        timeDetails7 = new TimeDetails(startingDate, endingDate, startingTime3, isAm, endingTime3, !isAm);

        // 12:00 PM to 1:00 AM
        timeDetails8 = new TimeDetails(startingDate, endingDate, startingTime3, !isAm, endingTime3, isAm);

        // 12:00 AM to 1:00 AM
        timeDetails9 = new TimeDetails(startingDate, endingDate, startingTime3, isAm, endingTime3, isAm);

        // 12:00 PM to 1:00 PM
        timeDetails10 = new TimeDetails(startingDate, endingDate, startingTime3, !isAm, endingTime3, !isAm);

    }

    @Test
    public void testGetHours() throws Exception{
        //Expected 15.5 hours
        double expectedNumOfHours = 11.66;
        double actualNumOfHours1 = timeDetails1.getHours();
        assertEquals(expectedNumOfHours, actualNumOfHours1, delta);

        double actualNumOfHours2 = timeDetails2.getHours();
        assertEquals(expectedNumOfHours, actualNumOfHours2, delta);

        double expectedNumOfHours2 = 11;
        double actualNumOfHours3 = timeDetails3.getHours();
        assertEquals(expectedNumOfHours2, actualNumOfHours3, delta);

        double actualNumOfHours4 = timeDetails4.getHours();
        assertEquals(expectedNumOfHours2, actualNumOfHours4, delta);

        double expectedNumOfHours3 = 23;
        double actualNumOfHours5 = timeDetails5.getHours();
        assertEquals(expectedNumOfHours3, actualNumOfHours5, delta);

        double actualNumOfHours6 = timeDetails6.getHours();
        assertEquals(expectedNumOfHours3, actualNumOfHours6, delta);

        double expectedNumOfHours4 = 1;
        double actualNumOfHours7 = timeDetails7.getHours();
        assertEquals(expectedNumOfHours4, actualNumOfHours7, delta);

        double actualNumOfHours8 = timeDetails8.getHours();
        assertEquals(expectedNumOfHours4, actualNumOfHours8, delta);

        double actualNumOfHours9 = timeDetails9.getHours();
        assertEquals(expectedNumOfHours4, actualNumOfHours9, delta);

        double actualNumOfHours10 = timeDetails10.getHours();
        assertEquals(expectedNumOfHours4, actualNumOfHours10, delta);
    }

    @Test
    public void testGetTotalHours() throws Exception{
        //Expected 11.66 hours * 4 days = 46.67 hours
        double expectedTotalNumOfHours = 46.67;
        double actualTotalNumOfHours1 = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours1, delta);

        double actualTotalNumOfHours2 = timeDetails2.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours2, delta);

        //Expected 11 hours * 4 days = 44 hours
        double expectedTotalNumOfHours2 = 44;
        double actualTotalNumOfHours3 = timeDetails3.getTotalHours();
        assertEquals(expectedTotalNumOfHours2, actualTotalNumOfHours3, delta);

        double actualTotalNumOfHours4 = timeDetails4.getTotalHours();
        assertEquals(expectedTotalNumOfHours2, actualTotalNumOfHours4, delta);

        //Expected 23 hours * 4 days = 92 hours
        double expectedTotalNumOfHours3 = 92;
        double actualTotalNumOfHours5 = timeDetails5.getTotalHours();
        assertEquals(expectedTotalNumOfHours3, actualTotalNumOfHours5, delta);

        double actualTotalNumOfHours6 = timeDetails6.getTotalHours();
        assertEquals(expectedTotalNumOfHours3, actualTotalNumOfHours6, delta);

        //Expected 1 hour * 4 days = 4 hours
        double expectedTotalNumOfHours4 = 4;
        double actualTotalNumOfHours7 = timeDetails7.getTotalHours();
        assertEquals(expectedTotalNumOfHours4, actualTotalNumOfHours7, delta);

        double actualTotalNumOfHours8 = timeDetails8.getTotalHours();
        assertEquals(expectedTotalNumOfHours4, actualTotalNumOfHours8, delta);

        double actualTotalNumOfHours9 = timeDetails9.getTotalHours();
        assertEquals(expectedTotalNumOfHours4, actualTotalNumOfHours9, delta);

        double actualTotalNumOfHours10 = timeDetails10.getTotalHours();
        assertEquals(expectedTotalNumOfHours4, actualTotalNumOfHours10, delta);
    }

}