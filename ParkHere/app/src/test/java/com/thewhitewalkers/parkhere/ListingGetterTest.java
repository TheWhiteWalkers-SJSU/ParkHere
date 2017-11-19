package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class ListingGetterTest extends TestCase {
    public ListingGetterTest(){} //default constructor

    Listing testListing;
    TimeDetails testTimeDetails;

    @Before
    protected void setUp(){
        testTimeDetails = new TimeDetails("testStartingDate", "testEndDate", "testStartTime", true , "testEndTime", true);
        testListing = new Listing("testListingID", "testListing", "testListingAddress", "testListingDescription", "testListingPrice", "testUserID", "testUserEmail", testTimeDetails, "available");
    }

    @Test
    public void testGetListingId() throws Exception{
        String expectedListingID = "testListingID";
        String actualListingID = testListing.getListingId();
        assertEquals(expectedListingID, actualListingID);
    }

    @Test
    public void testGetListingName() throws Exception{
        String expectedListingName = "testListing";
        String actualListingName = testListing.getListingName();
        assertEquals(expectedListingName, actualListingName);
    }

    @Test
    public void testGetListingAddress() throws Exception{
        String expectedListingAddress = "testListingAddress";
        String actualListingAddress = testListing.getListingAddress();
        assertEquals(expectedListingAddress, actualListingAddress);
    }

    @Test
    public void testGetListingDescription() throws Exception{
        String expectedListingDescription = "testListingDescription";
        String actualListingDescription = testListing.getListingDescription();
        assertEquals(expectedListingDescription, actualListingDescription);
    }

    @Test
    public void testGetListingPrice() throws Exception{
        String expectedListingPrice = "testListingPrice";
        String actualListingPrice = testListing.getListingPrice();
        assertEquals(expectedListingPrice, actualListingPrice);
    }

    @Test
    public void testGetListingOwnerId() throws Exception{
        String expectedListingOwnerId = "testUserID";
        String actualListingOwnerId = testListing.getOwnerId();
        assertEquals(expectedListingOwnerId, actualListingOwnerId);
    }

    @Test
    public void testGetListingOwnerEmail() throws Exception{
        String expectedListingOwnerEmail = "testUserEmail";
        String actualListingOwnerEmail = testListing.getOwnerEmail();
        assertEquals(expectedListingOwnerEmail, actualListingOwnerEmail);
    }

    @Test
    public void testGetListingStatus() throws Exception{
        String expectedListingStatus = "available";
        String actualListingStatus = testListing.getListingStatus();
        assertEquals(expectedListingStatus, actualListingStatus);
    }
}
