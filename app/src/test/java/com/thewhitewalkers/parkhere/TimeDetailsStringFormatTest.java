package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class TimeDetailsStringFormatTest  extends TestCase {
    public TimeDetailsStringFormatTest(){} //default constructor
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
        assertTrue(timeDetails1.checkDateFormat(timeDetails1.getStartingDate()));
        assertTrue(timeDetails1.checkDateFormat(timeDetails1.getEndingDate()));

        assertFalse(timeDetails1.checkDateFormat(timeDetails1.getStartingTime()));
        assertFalse(timeDetails1.checkDateFormat(timeDetails1.getEndingTime()));

        String d1 = "01/01/2017";
        String d2 = "01.01.2017";
        String d3 = "1/1/2017";
        String d4 = "01 01 2017";
        String d5 = "01012017";

        assertTrue(timeDetails1.checkDateFormat(d1));
        assertTrue(timeDetails1.checkDateFormat(d2));
        assertTrue(timeDetails1.checkDateFormat(d3));
        assertFalse(timeDetails1.checkDateFormat(d4));
        assertFalse(timeDetails1.checkDateFormat(d5));
    }

    @Test
    public void testCheckTimeFormat() throws Exception{
        assertTrue(timeDetails1.checkTimeFormat(timeDetails1.getStartingTime()));
        assertTrue(timeDetails1.checkTimeFormat(timeDetails1.getEndingTime()));

        assertFalse(timeDetails1.checkTimeFormat(timeDetails1.getStartingDate()));
        assertFalse(timeDetails1.checkTimeFormat(timeDetails1.getEndingDate()));

        String t1 = "01:01";
        String t2 = "01:01";
        String t3 = "01*01";
        String t4 = "01.01";

        assertTrue(timeDetails1.checkTimeFormat(t1));
        assertTrue(timeDetails1.checkTimeFormat(t2));
        assertFalse(timeDetails1.checkTimeFormat(t3));
        assertFalse(timeDetails1.checkTimeFormat(t4));
    }

}
