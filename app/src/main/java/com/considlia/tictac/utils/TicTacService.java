package com.considlia.tictac.utils;

import com.considlia.tictac.models.Project;
import com.considlia.tictac.models.TicTactivity;
import com.considlia.tictac.models.TimeLog;
import com.considlia.tictac.models.User;

import java.util.List;

public interface TicTacService {

    User getUser(long userId);

    List<Project> getProjectsByUserId(long userId);

    TicTactivity getActivity(long activityId);

    void updateActivity(TicTactivity ticTactivity);

    void deleteTimeLog(long id);

    TimeLog saveTimeLog(TimeLog timeLog);
}
