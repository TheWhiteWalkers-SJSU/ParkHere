package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateListingCheckBookingTest extends TestCase{

    private CreateListingActivity testActivity;
    private TimeDetails testTimeDetails;

    //default constructor
    public CreateListingCheckBookingTest() {}

    @Before
    public void setUp() throws Exception {
        testActivity = new CreateListingActivity();

        //Set time details for testing
        testTimeDetails = new TimeDetails();
        testActivity.setTimeDetails(testTimeDetails);
    }

    @Test
    public void testCheckBookingDate() throws Exception {
        //Valid date and format with "/" separators
        assertEquals("", testActivity.checkBookingDate("10/20/2017", "11/15/2017"));

        //Valid date and format with "." separators
        assertEquals("", testActivity.checkBookingDate("10.20.2017", "11.15.2017"));

        //Valid date
        assertEquals("", testActivity.checkBookingDate("12/31/2017", "1/28/2018"));

        //Valid date
        assertEquals("", testActivity.checkBookingDate("12/30/2017", "12/31/2017"));
    }

    @Test
    public void testCheckBookingTime() throws Exception {
        //Valid time and format with start toggle AM and end toggle PM
        testActivity.setToggleAM(true, false);
        assertEquals("", testActivity.checkBookingTime("10:00", "5:30"));

        //Valid time and format with start toggle PM and end toggle AM
        //and start and end being the same time
        testActivity.setToggleAM(false, true);
        assertEquals("", testActivity.checkBookingTime("10:00", "10:00"));

        //Valid time and format with start toggle AM and end toggle AM
        testActivity.setToggleAM(true, true);
        assertEquals("", testActivity.checkBookingTime("2:00", "5:30"));

        //Valid time and format with start toggle PM and end toggle PM
        testActivity.setToggleAM(false, false);
        assertEquals("", testActivity.checkBookingTime("10:00", "12:30"));
    }

}