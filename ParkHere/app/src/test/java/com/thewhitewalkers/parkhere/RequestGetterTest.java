package com.thewhitewalkers.parkhere;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RequestGetterTest {

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
    public void getRequestID() throws Exception {
        String returnedRequestId = testRequest.getRequestID();
        assertThat(returnedRequestId, is("testRequestId"));
    }

    @Test
    public void getRecipientID() throws Exception {
        String returnedRecipientId = testRequest.getRecipientID();
        assertThat(returnedRecipientId, is("testListingOwner"));
    }

    @Test
    public void getSenderID() throws Exception {
        String returnedRequestId = testRequest.getSenderID();
        assertThat(returnedRequestId, is("testFromId"));
    }

    @Test
    public void getSenderEmail() throws Exception {
        String returnedRequestId = testRequest.getSenderEmail();
        assertThat(returnedRequestId, is("testFromEmail"));
    }

    @Test
    public void getListingID() throws Exception {
        String returnedRequestId = testRequest.getListingID();
        assertThat(returnedRequestId, is("testListingId"));
    }

    @Test
    public void getSubject() throws Exception {
        String returnedRequestId = testRequest.getSubject();
        assertThat(returnedRequestId, is("testSubjectLine"));
    }

    @Test
    public void getMessage() throws Exception {
        String returnedRequestId = testRequest.getMessage();
        assertThat(returnedRequestId, is("testMessage"));
    }

    @Test
    public void getRequestType() throws Exception {
        int returnedRequestType = testRequest.getRequestType();
        assertThat(returnedRequestType, is(0));
    }

}