package com.considlia.tictac.fragments;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.considlia.tictac.utils.RunningState;
import com.considlia.tictac.utils.TimerState;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TimeFragmentTest extends TestCase {

    FragmentScenario<TimeFragment> scenario;

    @BeforeClass
    public static void setupAll() {
    }

    @Before
    public void setUp() throws Exception {
        scenario = FragmentScenario.launch(TimeFragment.class);
        scenario.onFragment(timeFragment -> {
            timeFragment.sessionManager.setTimerState(TimerState.getInstance());
        });
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetChronometerState_whenStopped() {
        scenario.onFragment(timeFragment -> {
            Assert.assertEquals(RunningState.STOPPED, timeFragment.sessionManager.getTimerState());
        });
    }

    @Test
    public void testChronometerState_whenResumingRunning() {
        scenario.onFragment(timeFragment -> {
            timeFragment.startTimer();
        });
    }
}