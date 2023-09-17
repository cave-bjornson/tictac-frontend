package com.considlia.tictac.utils;

import java.time.Duration;
import java.time.Instant;

public class TimerState {
    Instant startTime;
    Instant runTime;
    Duration elapsedTime;
    RunningState runningState;

    private static TimerState INSTANCE;

    public static TimerState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TimerState();
        }
        return INSTANCE;
    }

    private TimerState() {
        this.runningState = RunningState.STOPPED;
        this.startTime = Instant.MIN;
        this.runTime = Instant.MIN;
        this.elapsedTime = Duration.ZERO;
    }

    public TimerState set(RunningState runningState, long startTime, long runTime, long elapsedTime) {
        this.runningState = runningState;
        this.startTime = Instant.ofEpochMilli(startTime);
        this.runTime = Instant.ofEpochMilli(runTime);
        this.elapsedTime = Duration.ofMillis(elapsedTime);
        return this;
    }

    public void start() {
        startTime = Instant.now();
        runTime = startTime;
        runningState = RunningState.RUNNING;
    }

    public void pause() {
        elapsedTime = elapsedTime.plus(Duration.between(runTime, Instant.now()));
        runningState = RunningState.PAUSED;
    }

    public Duration resume() {
        runTime = Instant.now();
        runningState = RunningState.RUNNING;
        return elapsedTime;
    }

    public Duration stop() {
        pause();
        Duration finalTime = elapsedTime;
        set(RunningState.STOPPED, 0L, 0L, 0L);
        return finalTime;
    }

    public Duration wake() {
        return elapsedTime.plus(Duration.between(runTime, Instant.now()));
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getRunTime() {
        return runTime;
    }

    public Duration getElapsedTime() {
        return elapsedTime;
    }

    public RunningState getRunningState() {
        return runningState;
    }
}
