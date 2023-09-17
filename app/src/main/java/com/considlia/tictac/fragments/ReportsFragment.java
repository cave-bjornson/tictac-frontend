package com.considlia.tictac.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.considlia.tictac.MyViewModel;
import com.considlia.tictac.R;
import com.considlia.tictac.models.TimeLog;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ReportsFragment extends Fragment {

    private MyViewModel mViewModel;
    private RecyclerView mReportsRecyclerView;
    private CalendarView simpleCalendarView; // get the reference of CalendarView
    private TimeLogAdapter timeLogAdapter;
    private List<TimeLog> timeLogList;

    private class TimeLogHolder extends RecyclerView.ViewHolder {
        private final TextView mTitleTextView;
        private final ImageButton addButton;
        private final ImageButton removeButton;

        public TimeLogHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.activity_title);
            addButton = itemView.findViewById(R.id.addButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

        public void bind(TimeLog timeLog) {
            mTitleTextView.setText(timeLog.getTicTactivity().getTitle() + " " + timeLog.getHHMMSS());
            addButton.setOnClickListener(button -> {
                timeLog.setReported(true);
                mViewModel.updateTimeLog(timeLog);
                View view = getActivity().findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(view, "Log has been reported", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", undoView -> {
                    timeLog.setReported(false);
                    mViewModel.updateTimeLog(timeLog);
                });
                snackbar.show();
            });
            removeButton.setOnClickListener(button -> {
                mViewModel.deleteTimeLog(timeLog);
                View view = getActivity().findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(view, "Timelog removed.", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", undoView -> {
                });
                snackbar.show();

            });

        }
    }

    private class TimeLogAdapter extends RecyclerView.Adapter<TimeLogHolder> {
        private List<TimeLog> timeLogs;

        public TimeLogAdapter(List<TimeLog> timeLogs) {
            this.timeLogs = timeLogs;
        }

        // Called when Recycler nees a new Viewholder to display an item with
        @NonNull
        @Override
        public TimeLogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_report_activity, parent, false);
            return new TimeLogHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull TimeLogHolder holder, int position) {
            TimeLog timeLog = timeLogs.get(position);
            holder.bind(timeLog);
        }

        @Override
        public int getItemCount() {
            return timeLogs.size();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reports, container, false);
        mReportsRecyclerView = v.findViewById(R.id.reports_recycler_view);
        mReportsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        timeLogList = new ArrayList<>();
        timeLogAdapter = new TimeLogAdapter(timeLogList);
        mReportsRecyclerView.setAdapter(timeLogAdapter);
        simpleCalendarView = v.findViewById(R.id.calendarView);
        simpleCalendarView.setFirstDayOfWeek(2); // set Monday as the first day of the week

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        mViewModel.getTimeLogs().observe(getViewLifecycleOwner(), timeLogs -> {
            mViewModel.setUnreportedTimeLogs(timeLogs.stream().filter(timeLog -> !timeLog.isReported()).count());
            updateTimeLogAdapter(timeLogs);
        });

        // sets date in calendar from global viewmodel date
        LocalDate localDate = mViewModel.getSelectedDate().getValue();
        simpleCalendarView.setDate(ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.systemDefault()).toInstant().toEpochMilli());

        simpleCalendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            mViewModel.setSelectedDate(LocalDate.of(year, month + 1, dayOfMonth));
            updateTimeLogAdapter(mViewModel.getTimeLogs().getValue());
        });
    }

    private void updateTimeLogAdapter(List<TimeLog> viewModelTimeLog) {
        timeLogList.clear();
        timeLogList.addAll(viewModelTimeLog.stream()
                .sorted()
                .filter(timeLog -> !timeLog.isReported())
                .filter(timeLog -> timeLog.getDate().toLocalDate().equals(mViewModel.getSelectedDate().getValue()))
                .collect(Collectors.toList()));
        timeLogAdapter.notifyDataSetChanged();
    }

    public static ReportsFragment newInstance(String text) {

        ReportsFragment f = new ReportsFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}