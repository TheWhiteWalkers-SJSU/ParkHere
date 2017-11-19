package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreateListingCheckBookingInvalidTest extends TestCase {

    private CreateListingActivity testActivity;
    private TimeDetails testTimeDetails;
    private String errorDateInvalid;
    private String errorTimeInvalid;

    //default constructor
    public CreateListingCheckBookingInvalidTest() {}

    @Before
    public void setUp() throws Exception {
        testActivity = new CreateListingActivity();
        errorDateInvalid = "starting date should be before ending date!";
        errorTimeInvalid = "times can't be the same!";

        //Set time details for testing
        testTimeDetails = new TimeDetails();
        testActivity.setTimeDetails(testTimeDetails);
    }

    @Test
    public void testCheckBookingInvalidDate() throws Exception {
        //Invalid date with starting date after ending date
        assertEquals(errorDateInvalid, testActivity.checkBookingDate("11/15/2017", "10/20/2017"));
        assertEquals(errorDateInvalid, testActivity.checkBookingDate("10/20/2018", "11/15/2017"));
    }

    @Test
    public void testCheckBookingInvalidTime() throws Exception {
        //Invalid time with same start and end time, both toggle set to AM
        testActivity.setToggleAM(true, true);
        assertEquals(errorTimeInvalid, testActivity.checkBookingTime("10:00", "10:00"));

        //Invalid time with same start and end time, both toggle set to PM
        testActivity.setToggleAM(false, false);
        assertEquals(errorTimeInvalid, testActivity.checkBookingTime("5:30", "5:30"));
    }
}