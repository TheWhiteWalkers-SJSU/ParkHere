package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class TimeDetailsSwappedTimeTest extends TestCase {
    public TimeDetailsSwappedTimeTest(){} //default constructor
    TimeDetails timeDetails1;

    double delta = 0.01;
    @Before
    protected void setUp(){
        String startingDate  = "11/8/2017";
        String endingDate = "11/11/2017";
        String startingTime = "1:30";
        String endingTime = "10:00";
        boolean isAm = true;
        //  1:30PM to  10:00AM
        timeDetails1 = new TimeDetails(startingDate, endingDate, startingTime, !isAm, endingTime, isAm);

    }

    @Test
    public void testGetHours() throws Exception{
        //Expected 20.5 hours
        double expectedNumOfHours = 20.5;
        double actualNumOfHours1 = timeDetails1.getHours();
        assertEquals(expectedNumOfHours, actualNumOfHours1, delta);

    }

    @Test
    public void testGetDays() throws Exception{
        //Expected 4 days
        double expectedNumOfDays = 4.0;
        double actualNumOfDays1 = timeDetails1.getDays();
        assertEquals(expectedNumOfDays, actualNumOfDays1, delta);
    }

    @Test
    public void testGetTotalHours() throws Exception{
        //Expected 20.5 hours * 4 days = 82.0 hours
        double expectedTotalNumOfHours = 82.0;
        double actualTotalNumOfHours1 = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours1, delta);
    }

    @Test
    public void testSetPrice() throws Exception{

        String pricePerHour1 = "1.50"; //Expected 82.0 hours * 1.50 = 123.0
        String pricePerHour2 = "10.23"; //Expected 82.0hours * 10.23 = 838.86
        String pricePerHour3 = "17"; //Expected 82.0 hours * 17 = 1394
        String expectedPrice1 = "$123.0";
        String expectedPrice2 = "$838.86";
        String expectedPrice3 = "$1394.0";

        String actual1 = timeDetails1.setPrice(pricePerHour1);
        assertEquals(expectedPrice1, actual1);

        String actual2 = timeDetails1.setPrice(pricePerHour2);
        assertEquals(expectedPrice2, actual2);

        String actual3 = timeDetails1.setPrice(pricePerHour3);
        assertEquals(expectedPrice3, actual3);
    }

    @Test
    public void testCheckDateValid() throws Exception{
        assertTrue(timeDetails1.checkDateValid(timeDetails1.getStartingDate(), timeDetails1.getEndingDate()));
    }
}
