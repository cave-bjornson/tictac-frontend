package com.considlia.tictac;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.considlia.tictac.models.Project;
import com.considlia.tictac.models.TicTactivity;
import com.considlia.tictac.models.TimeLog;
import com.considlia.tictac.models.User;
import com.considlia.tictac.network.NetworkModule;
import com.considlia.tictac.utils.TicTacService;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {
    private final NetworkModule networkModule = NetworkModule.getInstance();
    private final TicTacService networkService = networkModule.getService();

    private MutableLiveData<User> user;
    private MutableLiveData<List<Project>> projects;
    private MutableLiveData<List<TimeLog>> mutableTimeLogs;
    private List<TimeLog> timeLogs;
    private MutableLiveData<LocalDate> selectedDate;
    private MutableLiveData<Month> selectedMonth;
    private MutableLiveData<Long> unreportedTimeLogs;

    public MyViewModel() {
        Log.i("timeLogs viewmodel constructor", "");
        user = new MutableLiveData<>();
        user.setValue(networkService.getUser(1));
        projects = new MutableLiveData<>();
        projects.setValue(networkService.getProjectsByUserId(user.getValue().getId()));
        mutableTimeLogs = new MutableLiveData<>();
        timeLogs = new ArrayList<>();
        for (Project project : projects.getValue()) {
            for (TicTactivity tactivity : project.getActivities()) {
                for (TimeLog timeLog : tactivity.getTimeLogs()) {
                    timeLog.setTicTactivity(tactivity);
                    timeLogs.add(timeLog);
                }
            }
        }
        mutableTimeLogs.setValue(timeLogs);
        selectedMonth = new MutableLiveData<>(LocalDate.now().getMonth());
        unreportedTimeLogs = new MutableLiveData<>(timeLogs.stream().filter(timeLog -> !timeLog.isReported()).count());
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    public LiveData<List<TimeLog>> getTimeLogs() {
        Log.i("timeLogs livedata", mutableTimeLogs.getValue().toString());
        return mutableTimeLogs;
    }

    // If no date chosen in calendar yet, return todays date
    public LiveData<LocalDate> getSelectedDate() {
        if (selectedDate == null) {
            selectedDate = new MutableLiveData<>();
            selectedDate.setValue(LocalDate.now());
        }
        return selectedDate;
    }

    public void setSelectedDate(LocalDate localDate) {
        selectedDate.setValue(localDate);
    }

    public void addTimeLog(TimeLog timeLog) {
        TimeLog savedTimeLog = networkService.saveTimeLog(timeLog);
        savedTimeLog.setTicTactivity(timeLog.getTicTactivity());
        timeLogs.add(savedTimeLog);
        mutableTimeLogs.setValue(timeLogs);
    }

    public void updateTimeLog(TimeLog otherTimeLog) {
        if (timeLogs.contains(otherTimeLog)) {
            timeLogs.set(timeLogs.indexOf(otherTimeLog), otherTimeLog);
            mutableTimeLogs.setValue(timeLogs);
        }
    }

    public void deleteTimeLog(TimeLog timeLog) {
        networkService.deleteTimeLog(timeLog.getId());
        timeLogs.remove(timeLog);
        mutableTimeLogs.setValue(timeLogs);
    }

    public LiveData<Month> getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(Month month) {
        selectedMonth.setValue(month);
    }

    public LiveData<Long> getUnreportedTimeLogs() {
        return unreportedTimeLogs;
    }

    public void setUnreportedTimeLogs(long unreportedTimeLogsNumber) {
        unreportedTimeLogs.setValue(unreportedTimeLogsNumber);
    }

    public void syncActivity(TimeLog timeLog) {
        TicTactivity activityToUpdate = timeLog.getTicTactivity();
        activityToUpdate.getTimeLogs().add(timeLog);
        networkService.updateActivity(activityToUpdate);
    }
}

