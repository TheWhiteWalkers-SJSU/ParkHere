package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestSetterTest extends TestCase {

    Request testRequest = null;
    TimeDetails testTimeDetails = null;

    @Before
    public void setUp() throws Exception {
        testTimeDetails = new TimeDetails("testStartingDate", "testEndDate", "testStartTime", true , "testEndTime", true);
        testRequest = new Request("testRequestId", "testListingOwner", "testFromId", "testFromEmail", "testListingId", "testSubjectLine", "testMessage", testTimeDetails, 0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetRecipientID() throws Exception {
        String newRecipientID = "newRecipientID";
        testRequest.setRecipientID(newRecipientID);
        assertThat(testRequest.getRecipientID(), is(newRecipientID));
    }

    @Test
    public void testSetSenderID() throws Exception {
        String newSenderID = "newSenderID";
        testRequest.setSenderID(newSenderID);
        assertThat(testRequest.getSenderID(), is(newSenderID));
    }

    @Test
    public void testSetListingID() throws Exception {
        String newListingID = "newListingID";
        testRequest.setListingID(newListingID);
        assertThat(testRequest.getListingID(), is(newListingID));
    }

    @Test
    public void testSetSubject() throws Exception {
        String newSubject = "newSubject";
        testRequest.setSubject(newSubject);
        assertThat(testRequest.getSubject(), is(newSubject));
    }

    @Test
    public void testSetMessage() throws Exception {
        String newMessage = "newMessage";
        testRequest.setMessage(newMessage);
        assertThat(testRequest.getMessage(), is(newMessage));
    }

    @Test
    public void testSetRequestType() throws Exception {
        int newRequestType = 2;
        testRequest.setRequestType(newRequestType);
        assertThat(testRequest.getRequestType(), is(newRequestType));
    }

}
