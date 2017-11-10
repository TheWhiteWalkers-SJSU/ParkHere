package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListingSetValidTest extends TestCase {

    private Listing testEmptyListing;
    private Listing testOverrideListing;

    //default constructor
    public ListingSetValidTest() {}

    @Before
    public void setUp() throws Exception {
        //Setup empty listing
        testEmptyListing = new Listing();

        //Setup listing with existing info
        String beforeID = "1";
        String beforeName = "Before Listing";
        String beforeAddr = "123 Listing Drive";
        String beforeDesc = "Great listing!";
        String beforePrice = "10";
        String beforeOwnerID = "2";
        String beforeEmail = "test@gmail.com";
        TimeDetails beforeTime = new TimeDetails("10/20/2017", "11/30/2017", "10:00", true, "5:30", false);
        String beforeStatus = "booked";
        testOverrideListing = new Listing(beforeID, beforeName, beforeAddr, beforeDesc, beforePrice, beforeOwnerID, beforeEmail, beforeTime, beforeStatus);
    }

    @Test
    public void testSetListingId() throws Exception {
        String afterID = "10";

        //Valid set ID for empty listing
        testEmptyListing.setListingId(afterID);
        assertEquals(afterID, testEmptyListing.getListingId());

        //Valid change ID for listing with existing info
        testOverrideListing.setListingId(afterID);
        assertEquals(afterID, testOverrideListing.getListingId());
    }

    @Test
    public void testSetListingName() throws Exception {
        String afterName = "After Listing";

        //Valid set name for empty listing
        testEmptyListing.setListingName(afterName);
        assertEquals(afterName, testEmptyListing.getListingName());

        //Valid change name for listing with existing info
        testOverrideListing.setListingName(afterName);
        assertEquals(afterName, testOverrideListing.getListingName());
    }

    @Test
    public void testSetListingAddress() throws Exception {
        String afterAddr = "456 Testing Ave";

        //Valid set address for empty listing
        testEmptyListing.setListingAddress(afterAddr);
        assertEquals(afterAddr, testEmptyListing.getListingAddress());

        //Valid change address for listing with existing info
        testOverrideListing.setListingAddress(afterAddr);
        assertEquals(afterAddr, testOverrideListing.getListingAddress());
    }

    @Test
    public void testSetListingDescription() throws Exception {
        String afterDesc = "Best listing!!";

        //Valid set description for empty listing
        testEmptyListing.setListingDescription(afterDesc);
        assertEquals(afterDesc, testEmptyListing.getListingDescription());

        //Valid change description for listing with existing info
        testOverrideListing.setListingDescription(afterDesc);
        assertEquals(afterDesc, testOverrideListing.getListingDescription());
    }

    @Test
    public void testSetListingPrice() throws Exception {
        String afterPrice = "20";

        //Valid set price for empty listing
        testEmptyListing.setListingPrice(afterPrice);
        assertEquals(afterPrice, testEmptyListing.getListingPrice());

        //Valid change price for listing with existing info
        testOverrideListing.setListingPrice(afterPrice);
        assertEquals(afterPrice, testOverrideListing.getListingPrice());
    }

    @Test
    public void testSetOwnerId() throws Exception {
        String afterOwnerID = "20";

        //Valid set owner ID for empty listing
        testEmptyListing.setOwnerId(afterOwnerID);
        assertEquals(afterOwnerID, testEmptyListing.getOwnerId());

        //Valid change owner ID for listing with existing info
        testOverrideListing.setOwnerId(afterOwnerID);
        assertEquals(afterOwnerID, testOverrideListing.getOwnerId());
    }

    @Test
    public void testSetOwnerEmail() throws Exception {
        String afterEmail = "listing@gmail.com";

        //Valid set owner email for empty listing
        testEmptyListing.setOwnerEmail(afterEmail);
        assertEquals(afterEmail, testEmptyListing.getOwnerEmail());

        //Valid change owner email for listing with existing info
        testOverrideListing.setOwnerEmail(afterEmail);
        assertEquals(afterEmail, testOverrideListing.getOwnerEmail());
    }

    @Test
    public void testSetListingStatus() throws Exception {
        String afterStatus = "available";

        //Valid set listing status for empty listing
        testEmptyListing.setListingStatus(afterStatus);
        assertEquals(afterStatus, testEmptyListing.getListingStatus());

        //Valid change listing status for listing with existing info
        testOverrideListing.setListingStatus(afterStatus);
        assertEquals(afterStatus, testOverrideListing.getListingStatus());
    }

    @Test
    public void testSetTimeDetails() throws Exception {
        TimeDetails afterTime = new TimeDetails("12/10/2017", "12/25/2017", "8:00", true, "11:45", true);

        //Valid set TimeDetails for empty listing
        testEmptyListing.setTimeDetails(afterTime);
        assertEquals(afterTime, testEmptyListing.getTimeDetails());

        //Valid change TimeDetails for listing with existing info
        testOverrideListing.setTimeDetails(afterTime);
        assertEquals(afterTime, testOverrideListing.getTimeDetails());
    }

    @Test
    public void testSetListingRating() throws Exception {
        String rating = "5";

        //Valid set listing rating for empty listing
        testEmptyListing.setListingRating(rating);
        assertEquals(rating, testEmptyListing.getListingRating());

        //Valid set listing rating for listing with existing info
        testOverrideListing.setListingRating(rating);
        assertEquals(rating, testOverrideListing.getListingRating());
    }

    @Test
    public void testSetRenterId() throws Exception {
        String renterID = "100";

        //Valid set renter ID for empty listing
        testEmptyListing.setRenterId(renterID);
        assertEquals(renterID, testEmptyListing.getRenterId());

        //Valid set renter ID for listing with existing info
        testOverrideListing.setRenterId(renterID);
        assertEquals(renterID, testOverrideListing.getRenterId());
    }
}