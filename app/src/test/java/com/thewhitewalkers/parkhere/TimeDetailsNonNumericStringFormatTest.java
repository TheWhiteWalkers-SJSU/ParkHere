package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class TimeDetailsNonNumericStringFormatTest extends TestCase{
    public TimeDetailsNonNumericStringFormatTest(){} //default constructor
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
    public void testCheckDateFormat() throws Exception{
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

}
