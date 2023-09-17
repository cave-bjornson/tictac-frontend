package com.considlia.tictac.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.considlia.tictac.utils.TicTacUtils;
import com.google.android.material.snackbar.Snackbar;

import java.time.Duration;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class SummaryFragment extends Fragment {

    private MyViewModel mViewModel;
    private RecyclerView mSummaryRecyclerView;
    private TimeLogAdapter timeLogAdapter;
    private List<TimeLog> timeLogList;
    private TextView totalTimeTextView;
    private TextView totalTimeHeaderTextView;
    private Spinner monthSpinner;
    private ArrayAdapter<Month> monthAdapter;
    private Month selectedMonth;

    private class TimeLogHolder extends RecyclerView.ViewHolder {

        private final TextView mTitleTextView;
        private final TextView mDateTextView;
        private final TextView mDurationTextView;
        private final ImageButton removeButton;

        public TimeLogHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.activity_title);
            mDateTextView = itemView.findViewById(R.id.activity_date);
            mDurationTextView = itemView.findViewById(R.id.activity_duration);
            removeButton = itemView.findViewById(R.id.removeButton);
        }

        public void bind(TimeLog timeLog) {
            mTitleTextView.setText(timeLog.getTicTactivity().getTitle());
            mDateTextView.setText(timeLog.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            mDurationTextView.setText(timeLog.getHHMMSS());
            removeButton.setOnClickListener(button -> {
                timeLog.setReported(false);
                mViewModel.updateTimeLog(timeLog);
                View view = getActivity().findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(view, "Log has been unreported", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", undoView -> {
                    timeLog.setReported(true);
                    mViewModel.updateTimeLog(timeLog);
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

        // Called when Recycler needs a new Viewholder to display an item with
        @NonNull
        @Override
        public TimeLogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_activity, parent, false);
            return new TimeLogHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SummaryFragment.TimeLogHolder holder, int position) {
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
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        mSummaryRecyclerView = (RecyclerView) view.findViewById(R.id.summary_recycler_view);
        mSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        timeLogList = new ArrayList<>();
        timeLogAdapter = new TimeLogAdapter(timeLogList);
        mSummaryRecyclerView.setAdapter(timeLogAdapter);
        totalTimeTextView = view.findViewById(R.id.totalTimeTextView);
        totalTimeHeaderTextView = view.findViewById(R.id.totalTimeHeaderTextView);
        monthSpinner = view.findViewById(R.id.spinner_month);
        monthAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        monthAdapter.addAll(Month.values());
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Month is 1 - 12, position is 0 - 11
                mViewModel.setSelectedMonth(Month.of(position + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthSpinner.setSelection(mViewModel.getSelectedMonth().getValue().getValue() - 1);


        // TODO Refactor duplicate code
        mViewModel.getTimeLogs().observe(getViewLifecycleOwner(), timeLogs -> {
            Month month = mViewModel.getSelectedMonth().getValue();
            timeLogList.clear();
            timeLogList.addAll(timeLogs.stream()
                    .filter(timeLog -> timeLog.getDate().getMonth().equals(month))
                    .sorted()
                    .filter(TimeLog::isReported)
                    .collect(Collectors.toList()));
            timeLogAdapter.notifyDataSetChanged();

            totalTimeHeaderTextView.setText("Total reported time for " + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            totalTimeTextView.setText(TicTacUtils.getDurationAsHHMM(timeLogList.stream()
                    .filter(timeLog -> timeLog.getDate().getMonth().equals(month))
                    .map(TimeLog::getDuration)
                    .collect(Collectors.toList())
                    .stream()
                    .reduce(Duration::plus)
                    .orElse(Duration.ZERO))
            );
        });

        mViewModel.getSelectedMonth().observe(getViewLifecycleOwner(), month -> {
            timeLogList.clear();
            timeLogList.addAll(mViewModel.getTimeLogs().getValue().stream()
                    .filter(timeLog -> timeLog.getDate().getMonth().equals(month))
                    .sorted()
                    .filter(TimeLog::isReported)
                    .collect(Collectors.toList()));
            timeLogAdapter.notifyDataSetChanged();

            totalTimeHeaderTextView.setText("Total reported time for " + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            totalTimeTextView.setText(TicTacUtils.getDurationAsHHMM(timeLogList.stream()
                    .filter(timeLog -> timeLog.getDate().getMonth().equals(month))
                    .map(TimeLog::getDuration)
                    .collect(Collectors.toList())
                    .stream()
                    .reduce(Duration::plus)
                    .orElse(Duration.ZERO))
            );
        });

    }

    public static SummaryFragment newInstance(String text) {

        SummaryFragment f = new SummaryFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}