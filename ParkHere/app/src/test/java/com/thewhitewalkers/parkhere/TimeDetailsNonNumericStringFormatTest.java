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
        String d1 = "01/02/2017";
        String d2 = "0e/0d/20f7";
        String d3 = "0e012r07";

        assertTrue(timeDetails1.checkDateFormat(d1));
        assertFalse(timeDetails1.checkDateFormat(d2));
        assertFalse(timeDetails1.checkDateFormat(d3));
    }

    @Test
    public void testCheckTimeFormat() throws Exception{
        String t1 = "01:01";
        String t2 = "e1:e1";
        String t3 = "01:01f";
        String t4 = "f1:01";

        assertTrue(timeDetails1.checkTimeFormat(t1));
        assertFalse(timeDetails1.checkTimeFormat(t2));
        assertFalse(timeDetails1.checkTimeFormat(t3));
        assertFalse(timeDetails1.checkTimeFormat(t4));
    }

}
