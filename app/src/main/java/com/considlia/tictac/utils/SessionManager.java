package com.considlia.tictac.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

public class SessionManager {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("TicTac", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public TimerState getTimerState() {
        return TimerState.getInstance().set(
                RunningState.valueOf(sharedPreferences.getString("KEY_STATE", "STOPPED")),
                sharedPreferences.getLong("KEY_START_TIME", 0L),
                sharedPreferences.getLong("KEY_RUN_TIME", 0L),
                sharedPreferences.getLong("KEY_ELAPSED_TIME", 0L));
    }

    public void setTimerState(TimerState timerState) {
        editor.putString("KEY_STATE", timerState.runningState.name());
        editor.putLong("KEY_START_TIME", timerState.startTime.toEpochMilli());
        editor.putLong("KEY_RUN_TIME", timerState.runTime.toEpochMilli());
        editor.putLong("KEY_ELAPSED_TIME", timerState.elapsedTime.toMillis());
        editor.apply();
    }

    public Pair<Long, Long> getProjectActivityIds() {
        return Pair.create(sharedPreferences.getLong("KEY_PROJECT_ID", 0L),
                sharedPreferences.getLong("KEY_ACTIVITY_ID", 0L));
    }

    public void setSelectedActivityIds(Pair<Long, Long> projectActivityIds) {
        editor.putLong("KEY_PROJECT_ID", projectActivityIds.first);
        editor.putLong("KEY_ACTIVITY_ID", projectActivityIds.second);
        editor.apply();
    }
}
