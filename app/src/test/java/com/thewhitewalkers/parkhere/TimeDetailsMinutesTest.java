package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class TimeDetailsMinutesTest extends TestCase {
    public TimeDetailsMinutesTest(){} //default constructor
    TimeDetails timeDetails1;
    TimeDetails timeDetails2;
    double delta = 0.01;
    @Before
    protected void setUp(){
        String startingDate  = "11/8/2017";
        String endingDate = "11/11/2017";
        String startingTime1 = "1:30";
        String endingTime1 = "3:10";

        boolean isAm = true;
        // 1:30 AM to 3:10 AM
        timeDetails1 = new TimeDetails(startingDate, endingDate, startingTime1, isAm, endingTime1, isAm);

        // 1:30 PM to 3:10 PM
        timeDetails2 = new TimeDetails(startingDate, endingDate, startingTime1, !isAm, endingTime1, !isAm);
    }

    @Test
    public void testGetHours() throws Exception{
        //Expected 15.5 hours
        double expectedNumOfHours = 1.67;
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
        //Expected 1.67 hours * 4 days = 6.67 hours
        double expectedTotalNumOfHours = 6.67;
        double actualTotalNumOfHours1 = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours1, delta);

        double actualTotalNumOfHours2 = timeDetails2.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours2, delta);
    }

    @Test
    public void testSetPrice() throws Exception{

        String pricePerHour1 = "1.50"; //Expected 6.67 hours * 1.50 = $14.0
        String pricePerHour2 = "10.23"; //Expected 6.67 hours * 10.23 = 68.2
        String pricePerHour3 = "17"; //Expected 6.67 hours * 17 = 113.33
        String expectedPrice1 = "$10.0";
        String expectedPrice2 = "$68.2";
        String expectedPrice3 = "$113.33";

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