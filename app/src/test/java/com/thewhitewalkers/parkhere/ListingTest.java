package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class ListingTest extends TestCase {
    public ListingTest(){} //default constructor

    Listing Listing;

    @Before
    protected void setUp(){
        String startingDate  = "11/8/2017";
        String endingDate = "11/11/2017";
        String startingTime = "10:00";
        String endingTime = "1:30";
        boolean isAm = true;
        // 10:00 AM to 1:30 PM
        timeDetails1 = new TimeDetails(startingDate, endingDate, startingTime, isAm, endingTime, !isAm);

        // 10:00 PM to 1:30 AM
        timeDetails2 = new TimeDetails(startingDate, endingDate, startingTime, !isAm, endingTime, isAm);

        // Invalid Date 11/11/2017 to 11/8/2017 10:00 AM to 1:30 PM
        timeDetails3 = new TimeDetails(endingDate, startingDate, startingTime, isAm, endingTime, !isAm);

        // Invalid Date 11/11/2017 to 11/8/2017 10:00 PM to 1:30 AM
        timeDetails4 = new TimeDetails(endingDate, startingDate, startingTime, !isAm, endingTime, isAm);
    }

    @Test
    public void testGetListing() throws Exception{
        String expectedListingID = “1”;
        String actualListingID = listing.getListingID();
        AssertEquals(expectedListingID, actualListingID);

    }
}
