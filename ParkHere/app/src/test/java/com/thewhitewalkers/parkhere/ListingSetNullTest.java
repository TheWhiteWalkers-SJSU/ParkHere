package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListingSetNullTest extends TestCase {
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

    //After "" or null values
    private String after1;
    private String after2;

    //default constructor
    public ListingSetNullTest() {}

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

        //Setup after values
        after1 = "";
        after2 = null;
    }

    @Test
    public void testSetListingId() throws Exception {
        //Invalid set listing ID to ""
        testEmptyListing.setListingId(after1);
        assertEquals(beforeEmpty, testEmptyListing.getListingId());

        //Invalid change listing ID to null
        testOverrideListing.setListingId(after2);
        assertEquals(beforeID, testOverrideListing.getListingId());
    }

    @Test
    public void testSetListingName() throws Exception {
        //Invalid set listing name to ""
        testEmptyListing.setListingName(after1);
        assertEquals(beforeEmpty, testEmptyListing.getListingName());

        //Invalid change listing name to null
        testOverrideListing.setListingName(after2);
        assertEquals(beforeName, testOverrideListing.getListingName());

    }

    @Test
    public void testSetListingAddress() throws Exception {
        //Invalid set listing address to ""
        testEmptyListing.setListingAddress(after1);
        assertEquals(beforeEmpty, testEmptyListing.getListingAddress());

        //Invalid change listing address to null
        testOverrideListing.setListingAddress(after2);
        assertEquals(beforeAddr, testOverrideListing.getListingAddress());
    }

    @Test
    public void testSetListingDescription() throws Exception {
        //Valid set listing description to ""
        testEmptyListing.setListingDescription(after1);
        assertEquals(after1, testEmptyListing.getListingDescription());

        //Valid change description address to null
        testOverrideListing.setListingDescription(after2);
        assertEquals(after2, testOverrideListing.getListingDescription());
    }

    @Test
    public void testSetListingPrice() throws Exception {
        //Invalid set listing price to ""
        testEmptyListing.setListingPrice(after1);
        assertEquals(beforeEmpty, testEmptyListing.getListingPrice());

        //Invalid change listing price to null
        testOverrideListing.setListingPrice(after2);
        assertEquals(beforePrice, testOverrideListing.getListingPrice());
    }

    @Test
    public void testSetOwnerId() throws Exception {
        //Invalid set owner ID to ""
        testEmptyListing.setOwnerId(after1);
        assertEquals(beforeEmpty, testEmptyListing.getOwnerId());

        //Invalid change owner ID to null
        testOverrideListing.setOwnerId(after2);
        assertEquals(beforeOwnerID, testOverrideListing.getOwnerId());
    }

    @Test
    public void testSetOwnerEmail() throws Exception {
        //Valid set owner email to ""
        testEmptyListing.setOwnerEmail(after1);
        assertEquals(after1, testEmptyListing.getOwnerEmail());

        //Valid change owner email to null
        testOverrideListing.setOwnerEmail(after2);
        assertEquals(after2, testOverrideListing.getOwnerEmail());
    }

    @Test
    public void testSetListingStatus() throws Exception {
        //Invalid set listing status to ""
        testEmptyListing.setListingStatus(after1);
        assertEquals(beforeEmpty, testEmptyListing.getListingStatus());

        //Invalid change listing status to null
        testOverrideListing.setListingStatus(after2);
        assertEquals(beforeStatus, testOverrideListing.getListingStatus());
    }

    @Test
    public void testSetTimeDetails() throws Exception {
        TimeDetails afterTime = null;

        //Invalid set TimeDetails to null
        testEmptyListing.setTimeDetails(afterTime);
        assertEquals(beforeEmpty, testEmptyListing.getTimeDetails());
        testOverrideListing.setTimeDetails(afterTime);
        assertEquals(beforeTime, testOverrideListing.getTimeDetails());
    }

    @Test
    public void testSetListingRating() throws Exception {
        //Valid set listing rating to ""
        testEmptyListing.setListingRating(after1);
        assertEquals(after1, testEmptyListing.getListingRating());

        //Valid change listing rating to null
        testOverrideListing.setListingRating(after2);
        assertEquals(after2, testOverrideListing.getListingRating());
    }

    @Test
    public void testSetRenterId() throws Exception {
        //Invalid set renter ID to ""
        testEmptyListing.setRenterId(after1);
        assertEquals(beforeEmpty, testEmptyListing.getRenterId());

        //Invalid change renter ID to null
        testOverrideListing.setRenterId(after2);
        assertEquals(beforeEmpty, testOverrideListing.getRenterId());
    }
}