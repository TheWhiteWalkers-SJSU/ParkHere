package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class TimeDetailsTest extends TestCase {
    public TimeDetailsTest(){} //default constructor
    TimeDetails timeDetails1;
    double delta = 0.01;
    @Before
    protected void setUp(){
        String startingDate  = "11/8/2017";
        String endingDate = "11/11/2017";
        String startingTime = "10:00";
        String endingTime = "1:30";
        boolean s_IsAm = true;
        timeDetails1 = new TimeDetails(startingDate, endingDate, startingTime, s_IsAm, endingTime, !s_IsAm);
    }

    @Test
    public void testGetHours() throws Exception{
        //Expected 3.5 hours * 3 days = 10.5
        double expectedNumOfHours = 3.5;
        double actualNumOfHours = timeDetails1.getHours();
        assertEquals(expectedNumOfHours, actualNumOfHours, delta);
    }

    @Test
    public void testGetDays() throws Exception{
        //Expected 3 days
        double expectedNumOfDays = 4.0;
        double actualNumOfDays = timeDetails1.getDays();
        assertEquals(expectedNumOfDays, actualNumOfDays, delta);
    }

    @Test
    public void testGetTotalHours() throws Exception{
        //Expected 3.5 hours * 4 days = 14.0
        double expectedTotalNumOfHours = 14.0;
        double actualTotalNumOfHours = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours, delta);
    }

    @Test
    public void testSetPrice() throws Exception{
        //Expected 3.5 hours * 4 days = 14.0
        double pricePerHour1 = 1.50;
        double pricePerHour2 = 10.23;
        double pricePerhour3 = 17;

        //assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours, delta);
    }

    @Test
    public void testChecKDateFormat() throws Exception{
        //Expected 3.5 hours * 4 days = 14.0
        double expectedTotalNumOfHours = 14.0;
        double actualTotalNumOfHours = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours, delta);
    }

    @Test
    public void testCheckTimeFormat() throws Exception{
        //Expected 3.5 hours * 4 days = 14.0
        double expectedTotalNumOfHours = 14.0;
        double actualTotalNumOfHours = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours, delta);
    }

    @Test
    public void testCheckDateValid() throws Exception{
        //Expected 3.5 hours * 4 days = 14.0
        double expectedTotalNumOfHours = 14.0;
        double actualTotalNumOfHours = timeDetails1.getTotalHours();
        assertEquals(expectedTotalNumOfHours, actualTotalNumOfHours, delta);
    }
}
