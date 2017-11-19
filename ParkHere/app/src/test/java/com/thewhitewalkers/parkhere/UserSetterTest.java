package com.thewhitewalkers.parkhere;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserSetterTest extends TestCase {

    User testUser = null;

    @Before
    public void setUp() throws Exception {
        testUser = new User("testUserId", "test@parkhere.com", "Park", "Tester", "testingLocation", "555-555-5555");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetEmail() throws Exception {
        String newEmail = "newTest@parkhere.com";
        testUser.setEmail(newEmail);
        assertThat(testUser.getEmail(), is(newEmail));
    }

    @Test
    public void testSetFirstName() throws Exception {
        String newFirstName = "newPark";
        testUser.setEmail(newFirstName);
        assertThat(testUser.getEmail(), is(newFirstName));
    }

    @Test
    public void testSetLastName() throws Exception {
        String newLastName = "NewTester";
        testUser.setEmail(newLastName);
        assertThat(testUser.getEmail(), is(newLastName));
    }

    @Test
    public void testSetLocation() throws Exception {
        String newLocation = "newTestingLocation";
        testUser.setEmail(newLocation);
        assertThat(testUser.getEmail(), is(newLocation));
    }

    @Test
    public void testSetPhoneNumber() throws Exception {
        testUser.setEmail("newTest@parkhere.com");
        assertThat(testUser.getEmail(), is("newTest@parkhere.com"));
    }

}
