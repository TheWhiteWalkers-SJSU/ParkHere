package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateListingCheckBookingFormatTest extends TestCase {

    private CreateListingActivity testActivity;
    private TimeDetails testTimeDetails;
    private String errorDateFormat;
    private String errorTimeFormat;

    //default constructor
    public CreateListingCheckBookingFormatTest() {}

    @Before
    public void setUp() throws Exception {
        testActivity = new CreateListingActivity();
        errorDateFormat = "dates must be in MM/DD/YYYY format!";
        errorTimeFormat = "times must be in HH:MM format!";

        //Set time details for testing
        testTimeDetails = new TimeDetails();
        testActivity.setTimeDetails(testTimeDetails);

        //Set starting toggle to AM and ending toggle to PM
        testActivity.setToggleAM(true, false);
    }

    @Test
    public void testCheckBookingFormatDate() throws Exception {
        //Invalid starting date format
        assertEquals(errorDateFormat, testActivity.checkBookingDate("10202017", "11/15/2017"));

        //Invalid ending date format
        assertEquals(errorDateFormat, testActivity.checkBookingDate("10/20/2017", "11/15/17"));

        //Invalid starting and ending date format
        assertEquals(errorDateFormat, testActivity.checkBookingDate("102017", "11_15_17"));
    }

    @Test
    public void testCheckBookingFormatTime() throws Exception {
        //Invalid starting date format
        assertEquals(errorTimeFormat, testActivity.checkBookingTime("1000", "5:30"));

        //Invalid ending date format
        assertEquals(errorTimeFormat, testActivity.checkBookingTime("10:00", "5"));

        //Invalid starting and ending date format
        assertEquals(errorTimeFormat, testActivity.checkBookingTime("10-00", "5 o'clock"));
    }
}