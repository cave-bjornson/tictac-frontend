package com.considlia.tictac.models;

import java.util.List;
import java.util.Objects;

public class TicTactivity {
    private long id;
    private String title;
    private List<TimeLog> timeLogs;

    public TicTactivity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TimeLog> getTimeLogs() {
        return timeLogs;
    }

    public void setTimeLogs(List<TimeLog> timeLogs) {
        this.timeLogs = timeLogs;
    }

    /**
     * The toString method is used for the textlabel in the spinner.
     * So we cant write out anything more.
     *
     * @return the activitys title
     */
    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTactivity tactivity = (TicTactivity) o;
        return id == tactivity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
