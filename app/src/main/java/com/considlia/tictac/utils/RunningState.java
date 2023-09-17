package com.considlia.tictac.utils;

public enum RunningState {
    STOPPED(true, false, false, 1.0f, 0.5f, 0.5f),
    RUNNING(false, true, true, 0.5f, 1.0f, 1.0f),
    PAUSED(true, false, true, 1.0f, 0.5f, 1.0f);

    public final boolean isStartEnabled;
    public final boolean isPauseEnabled;
    public final boolean isStopEnabled;
    public final float startAlphaValue;
    public final float pauseAlphaValue;
    public final float stopAlphaValue;

    RunningState(boolean isStartEnabled, boolean isPauseEnabled, boolean isStopEnabled, float startAlphaValue, float pauseAlphaValue, float stopAlphaValue) {
        this.isStartEnabled = isStartEnabled;
        this.isPauseEnabled = isPauseEnabled;
        this.isStopEnabled = isStopEnabled;
        this.startAlphaValue = startAlphaValue;
        this.pauseAlphaValue = pauseAlphaValue;
        this.stopAlphaValue = stopAlphaValue;
    }
}