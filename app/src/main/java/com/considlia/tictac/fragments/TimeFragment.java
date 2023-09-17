package com.considlia.tictac.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.considlia.tictac.BuildConfig;
import com.considlia.tictac.MyViewModel;
import com.considlia.tictac.R;
import com.considlia.tictac.activities.MainActivity;
import com.considlia.tictac.models.Project;
import com.considlia.tictac.models.TicTactivity;
import com.considlia.tictac.models.TimeLog;
import com.considlia.tictac.utils.SessionManager;
import com.considlia.tictac.utils.TimerState;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class TimeFragment extends Fragment {

    MyViewModel myViewModel;
    Chronometer chronometer;
    ImageButton pauseBtn, startBtn, stopBtn;
    List<ImageButton> buttonList;
    ConstraintLayout constraintLayout;
    Spinner projectSpinner, activitySpinner;
    ArrayAdapter<Project> projectAdapter;
    ArrayAdapter<TicTactivity> activityAdapter;
    SessionManager sessionManager;
    SimpleDateFormat simpleDateFormat;
    TimerState timerState;
    TicTactivity selectedActivity;
    Project selectedProject;

    void setButtonState() {
        startBtn.setEnabled(timerState.getRunningState().isStartEnabled);
        startBtn.setAlpha(timerState.getRunningState().startAlphaValue);
        pauseBtn.setEnabled(timerState.getRunningState().isPauseEnabled);
        pauseBtn.setAlpha(timerState.getRunningState().pauseAlphaValue);
        stopBtn.setEnabled(timerState.getRunningState().isStopEnabled);
        stopBtn.setAlpha(timerState.getRunningState().stopAlphaValue);
    }

    void startTimer() {
        timerState.start();
        sessionManager.setTimerState(timerState);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    void pauseTimer() {
        timerState.pause();
        sessionManager.setTimerState(timerState);
        chronometer.stop();
    }

    void unPauseTimer() {
        Duration elapsedTime = timerState.resume();
        sessionManager.setTimerState(timerState);
        chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime.toMillis());
        chronometer.start();
    }

    void stopTimer() {
        Duration finalDuration = timerState.stop();
        TimeLog timeLog = new TimeLog();
        timeLog.setDate(LocalDateTime.now());
        timeLog.setDuration(finalDuration);
        timeLog.setReported(Boolean.FALSE);
        timeLog.setSubmitted(Boolean.FALSE);
        timeLog.setTicTactivity((TicTactivity) activitySpinner.getSelectedItem());
        myViewModel.addTimeLog(timeLog);
        sessionManager.setTimerState(timerState);
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    View.OnClickListener startListener = startButton -> {
        startTimer();
        setButtonState();
        ((MainActivity) getActivity()).startService();
        projectSpinner.setEnabled(false);
        activitySpinner.setEnabled(false);
        selectedProject = (Project) projectSpinner.getSelectedItem();
        selectedActivity = (TicTactivity) activitySpinner.getSelectedItem();
        sessionManager.setSelectedActivityIds(Pair.create(selectedProject.getId(), selectedActivity.getId()));
    };

    View.OnClickListener unPauseListener = unPauseButton -> {
        unPauseTimer();
        setButtonState();
    };

    View.OnClickListener pauseListener = pauseButton -> {
        pauseTimer();
        setButtonState();
        startBtn.setOnClickListener(unPauseListener);
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_time, container, false);
        projectSpinner = v.findViewById(R.id.spinner_projects);
        activitySpinner = v.findViewById(R.id.spinner_activities);
        constraintLayout = v.findViewById(R.id.cLayout1);
        chronometer = v.findViewById(R.id.chronometer);
        startBtn = v.findViewById(R.id.startBtn);
        pauseBtn = v.findViewById(R.id.pauseBtn);
        stopBtn = v.findViewById(R.id.stopBtn);
        buttonList = List.of(startBtn, pauseBtn, stopBtn);
        sessionManager = new SessionManager(getContext());
        simpleDateFormat = new SimpleDateFormat("hh:mm:ss aa");
        timerState = sessionManager.getTimerState();

        startBtn.setOnClickListener(startListener);

        stopBtn.setOnClickListener(stopButton -> {
            stopTimer();
            setButtonState();

            View view = getActivity().findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(view, "Time is sent to reports", Snackbar.LENGTH_LONG);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            snackbar.show();
            ((MainActivity) getActivity()).stopService();
            projectSpinner.setEnabled(true);
            activitySpinner.setEnabled(true);
            startBtn.setOnClickListener(startListener);
        });

        pauseBtn.setOnClickListener(pauseListener);

        if (BuildConfig.DEBUG) {
            chronometer.setOnLongClickListener(chronometerBtn -> {
                timerState.stop();
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime());
                sessionManager.setTimerState(timerState);
                sessionManager.setSelectedActivityIds(Pair.create(0L, 0L));
                setButtonState();
                projectSpinner.setEnabled(true);
                activitySpinner.setEnabled(true);
                startBtn.setOnClickListener(startListener);
                return false;
            });
        }


        projectAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectSpinner.setAdapter(projectAdapter);

        activityAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(activityAdapter);


        projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedProject = (Project) parent.getSelectedItem();
                activityAdapter.clear();
                activityAdapter.addAll(selectedProject.getActivities());
                activitySpinner.setSelection(activityAdapter.getPosition(selectedActivity));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Pair<Long, Long> projectActivitiesIds = sessionManager.getProjectActivityIds();
        Log.i("projects ids", projectActivitiesIds.toString());
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        myViewModel.getProjects().observe(getViewLifecycleOwner(), projects -> {
            projectAdapter.addAll(projects);
            Log.i("projects", projects.stream().map(Project::getValues).collect(Collectors.joining()));
            projects.stream()
                    .filter(project -> project.getId() == projectActivitiesIds.first)
                    .findFirst()
                    .ifPresent(project -> {
                        projectSpinner.setSelection(projectAdapter.getPosition(project));
                        project.getActivities().stream()
                                .filter(activity -> activity.getId() == projectActivitiesIds.second)
                                .findFirst()
                                .ifPresent(activity -> activitySpinner.setSelection(activityAdapter.getPosition(activity)));
                    });
        });
        switch (timerState.getRunningState()) {
            case STOPPED:
                chronometer.setBase(SystemClock.elapsedRealtime());
                projectSpinner.setEnabled(true);
                activitySpinner.setEnabled(true);
                break;
            case PAUSED:
                chronometer.setBase(SystemClock.elapsedRealtime() - timerState.getElapsedTime().toMillis());
                startBtn.setOnClickListener(unPauseListener);
                projectSpinner.setEnabled(false);
                activitySpinner.setEnabled(false);
                break;
            case RUNNING:
                Duration duration = timerState.wake();
                chronometer.setBase(SystemClock.elapsedRealtime() - duration.toMillis());
                chronometer.start();
                projectSpinner.setEnabled(false);
                activitySpinner.setEnabled(false);
                break;
        }
        setButtonState();
    }

    public static TimeFragment newInstance(String text) {

        TimeFragment f = new TimeFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}