package com.considlia.tictac.network;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.considlia.tictac.utils.SessionManager;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SessionManagerTest extends TestCase {

    SessionManager sessionManager;
    Context appContext;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        sessionManager = new SessionManager(appContext);
    }

    @After
    public void tearDown() throws Exception {
    }

//    @Test
//    public void setFlag() {
//        sessionManager.setFlag(true);
//        assertTrue(sessionManager.getFlag());
//    }
//
//    @Test
//    public void getFlag() {
//        assertFalse(sessionManager.getFlag());
//    }
//
//    @Test
//    public void setCurrentTime() {
//        sessionManager.storeTime("12:34");
//        assertEquals("12:34", sessionManager.getStoredTime());
//    }
//
//    @Test
//    public void getCurrentTime() {
//        assertEquals("", sessionManager.getStoredTime());
//    }
}