package com.considlia.tictac.network;

import android.util.Log;

import com.considlia.tictac.models.Project;
import com.considlia.tictac.models.TicTactivity;
import com.considlia.tictac.models.TimeLog;
import com.considlia.tictac.models.User;
import com.considlia.tictac.utils.TicTacService;

import org.modelmapper.ModelMapper;
import org.openapitools.client.api.ActivitiesApiApi;
import org.openapitools.client.api.ProjectsApiApi;
import org.openapitools.client.api.TimelogsApiApi;
import org.openapitools.client.api.UsersApiApi;
import org.openapitools.client.model.ActivityDto;
import org.openapitools.client.model.ProjectDto;
import org.openapitools.client.model.ProjectsByUserDto;
import org.openapitools.client.model.TimeLogDto;
import org.openapitools.client.model.UserDto;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class RestService implements TicTacService {
    UsersApiApi userApi;
    ProjectsApiApi projectApi;
    ActivitiesApiApi activityApi;
    TimelogsApiApi timeLogsApi;
    ModelMapper modelMapper;

    public RestService(UsersApiApi userApi, ProjectsApiApi projectApi, ActivitiesApiApi activityApi, TimelogsApiApi timeLogsApi) {
        this.userApi = userApi;
        this.projectApi = projectApi;
        this.activityApi = activityApi;
        this.timeLogsApi = timeLogsApi;
        this.modelMapper = new ModelMapper();
    }

    public User getUser(long id) {
        UserDto userDto = userApi.getUserById(id).subscribeOn(Schedulers.io()).blockingFirst();
        return modelMapper.map(userDto, User.class);
    }

    public TicTactivity getActivity(long id) {
        ActivityDto activityDto = activityApi.getActivityById(id).subscribeOn(Schedulers.io()).blockingFirst();
        return modelMapper.map(activityDto, TicTactivity.class);
    }

    public void updateActivity(TicTactivity activity) {
        ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
        Log.i("updateActivity", activityDto.toString());
        activityApi.updateActivity(activity.getId(), activityDto).subscribeOn(Schedulers.io()).blockingFirst();
    }

    public List<Project> getProjectsByUserId(long id) {
        ProjectsByUserDto projectsByUserDto = projectApi.getProjectsByUserId(id).subscribeOn(Schedulers.io()).blockingFirst();
        return projectsByUserDto.getProjects()
                .stream()
                .map(projectDto -> projectDtoProject(projectDto))
                .collect(Collectors.toList());
    }

    public void deleteTimeLog(long id) {
        timeLogsApi.deleteTimeLog(id).subscribeOn(Schedulers.io()).blockingFirst();
    }

    public TimeLog saveTimeLog(TimeLog timeLog) {
        TimeLogDto savedTimeLog = timeLogsApi.saveTimeLog(timeLog.getTicTactivity().getId(),
                modelMapper.map(timeLog, TimeLogDto.class)).subscribeOn(Schedulers.io()).blockingFirst();
        return modelMapper.map(savedTimeLog, TimeLog.class);
    }

    Project projectDtoProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setTitle(projectDto.getTitle());
        project.setId(projectDto.getId());
        project.setActivities(projectDto.getActivities().stream()
                .map(activityDto -> activityDtoActivity(activityDto))
                .collect(Collectors.toList()));
        return project;
    }

    TicTactivity activityDtoActivity(ActivityDto activityDto) {
        TicTactivity activity = new TicTactivity();
        activity.setTitle(activityDto.getTitle());
        activity.setId(activityDto.getId());
        activity.setTimeLogs(activityDto.getTimeLogs()
                .stream()
                .map(timeLogDto -> modelMapper.map(timeLogDto, TimeLog.class))
                .collect(Collectors.toList()));
        return activity;
    }
}
