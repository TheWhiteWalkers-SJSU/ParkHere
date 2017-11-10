package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListingSetInvalidTest extends TestCase {
    //Listings
    private Listing testEmptyListing;
    private Listing testOverrideListing;

    //Before values
    private String beforeEmpty;
    private String beforeID;
    private String beforeName;
    private String beforeAddr;
    private String beforeDesc;
    private String beforePrice;
    private String beforeOwnerID;
    private String beforeEmail;
    private TimeDetails beforeTime;
    private String beforeStatus;

    //default constructor
    public ListingSetInvalidTest() {}

    @Before
    public void setUp() throws Exception {
        //Setup empty listing
        beforeEmpty = null;
        testEmptyListing = new Listing();

        //Setup listing with existing info
        beforeID = "1";
        beforeName = "Before Listing";
        beforeAddr = "123 Listing Drive";
        beforeDesc = "Great listing!";
        beforePrice = "10";
        beforeOwnerID = "2";
        beforeEmail = "test@gmail.com";
        beforeTime = new TimeDetails("10/20/2017", "11/30/2017", "10:00", true, "5:30", false);
        beforeStatus = "booked";
        testOverrideListing = new Listing(beforeID, beforeName, beforeAddr, beforeDesc, beforePrice, beforeOwnerID, beforeEmail, beforeTime, beforeStatus);
    }

    @Test
    public void testSetListingPrice() throws Exception {
        //Invalid set listing price
        testEmptyListing.setListingPrice("hundred");
        assertEquals(beforeEmpty, testEmptyListing.getListingPrice());

        //Invalid change listing price
        testOverrideListing.setListingPrice("$100");
        assertEquals(beforePrice, testOverrideListing.getListingPrice());
    }

    @Test
    public void testSetListingStatus() throws Exception {
        //Invalid set listing status
        testEmptyListing.setListingStatus("unavailable");
        assertEquals(beforeEmpty, testEmptyListing.getListingStatus());

        //Invalid change listing status
        testOverrideListing.setListingStatus("close");
        assertEquals(beforeStatus, testOverrideListing.getListingStatus());
    }
}