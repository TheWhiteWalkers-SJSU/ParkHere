package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserGetterTest extends TestCase{

    User testUser = null;

    @Before
    public void setUp() throws Exception {
        testUser = new User("testUserId", "test@parkhere.com", "Park", "Tester", "testingLocation", "555-555-5555");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetUserId() throws Exception {
        String returnedUserId = testUser.getUserId();
        assertThat(returnedUserId, is("testUserId"));
    }

    @Test
    public void testGetEmail() throws Exception {
        String returnedEmail = testUser.getEmail();
        assertThat(returnedEmail, is("test@parkhere.com"));
    }

    @Test
    public void testGetFirstName() throws Exception {
        String returnedFirstName = testUser.getFirstName();
        assertThat(returnedFirstName, is("Park"));
    }

    @Test
    public void testGetLastName() throws Exception {
        String returnedLastName = testUser.getLastName();
        assertThat(returnedLastName, is("Tester"));
    }

    @Test
    public void testGetPhoneNumber() throws Exception {
        String returnedPhoneNumber = testUser.getPhoneNumber();
        assertThat(returnedPhoneNumber, is("555-555-5555"));
    }

    @Test
    public void testGetLocation() throws Exception {
        String returnedLocation = testUser.getLocation();
        assertThat(returnedLocation, is("testingLocation"));
    }


    @Test
    public void setEmail() throws Exception {
    }

    @Test
    public void setFirstName() throws Exception {
    }

    @Test
    public void setLastName() throws Exception {
    }

    @Test
    public void setLocation() throws Exception {
    }

    @Test
    public void setPhoneNumber() throws Exception {
    }

}