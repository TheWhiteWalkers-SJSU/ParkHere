package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class TimeDetailsSameAMTest extends TestCase {
    public TimeDetailsSameAMTest(){} //default constructor
    TimeDetails timeDetails1;
    TimeDetails timeDetails2;

    double delta = 0.01;
    @Before
    protected void setUp(){
        String startingDate  = "11/8/2017";
        String endingDate = "11/11/2017";
        String startingTime = "10:00";
        String endingTime = "1:30";

        boolean isAm = true;
        // 10:00 AM to 1:30 AM
        timeDetails1 = new TimeDetails(startingDate, endingDate, startingTime, isAm, endingTime, isAm);

        // 10:00 PM to 1:30 PM
        timeDetails2 = new TimeDetails(startingDate, endingDate, startingTime, !isAm, endingTime, !isAm);
    }

    @Test
    public void testGetHours() throws Exception{
        //Expected 15.5 hours
        double expectedNumOfHours = 15.5;
        double actualNumOfHours1 = timeDetails1.getHours();
        assertEquals(expectedNumOfHours, actualNumOfHours1, delta);

        double actualNumOfHours2 = timeDetails2.getHours();
        assertEquals(expectedNumOfHours, actualNumOfHours2, delta);
    }

    @Test
    public void testGetDays() throws Exception{
        //Expected 4 days
        double expectedNumOfDays = 4.0;
        double actualNumOfDays1 = timeDetails1.getDays();
        assertEquals(expectedNumOfDays, actualNumOfDays1, delta);

        double actualNumOfDays2 = timeDetails2.getDays();
        assertEquals(expectedNumOfDays, actualNumOfDays2, delta);
    }

    @Test
    public void testGetTotalHours() throws Exception{
        //Expected 15.5 hours * 4 days = 62.0 hours
        double expectedTotalNumOfHours = 62.0;
        double actualTotalNumOfHours1 = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours1, delta);

        double actualTotalNumOfHours2 = timeDetails2.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours2, delta);
    }

    @Test
    public void testSetPrice() throws Exception{

        String pricePerHour1 = "1.50"; //Expected 62.0 hours * 1.50 = 93.0
        String pricePerHour2 = "10.23"; //Expected 62.0 hours * 10.23 = 634.26
        String pricePerHour3 = "17"; //Expected 62.0 hours * 17 = 1054.0
        String expectedPrice1 = "$93.0";
        String expectedPrice2 = "$634.26";
        String expectedPrice3 = "$1054.0";

        String actual1 = timeDetails1.setPrice(pricePerHour1);
        assertEquals(expectedPrice1, actual1);

        String actual2 = timeDetails1.setPrice(pricePerHour2);
        assertEquals(expectedPrice2, actual2);

        String actual3 = timeDetails1.setPrice(pricePerHour3);
        assertEquals(expectedPrice3, actual3);

        //test the other TimeDetails

        actual1 = timeDetails2.setPrice(pricePerHour1);
        assertEquals(expectedPrice1, actual1);

        actual2 = timeDetails2.setPrice(pricePerHour2);
        assertEquals(expectedPrice2, actual2);

        actual3 = timeDetails2.setPrice(pricePerHour3);
        assertEquals(expectedPrice3, actual3);
    }

    @Test
    public void testCheckDateValid() throws Exception{
        assertTrue(timeDetails1.checkDateValid(timeDetails1.getStartingDate(), timeDetails1.getEndingDate()));
        assertTrue(timeDetails2.checkDateValid(timeDetails2.getStartingDate(), timeDetails2.getEndingDate()));
    }
}