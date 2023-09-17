package com.considlia.tictac.models;

import java.util.List;
import java.util.Objects;

public class Project {

    private long id;
    private String title;
    private List<TicTactivity> activities;

    /**
     * Replaces toString method
     *
     * @return all values as should have been returned by toString
     */
    public String getValues() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", activities=" + activities +
                '}';
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

    public List<TicTactivity> getActivities() {
        return activities;
    }

    public void setActivities(List<TicTactivity> activities) {
        this.activities = activities;
    }

    /**
     * The toString method is used for the textlabel in the spinner.
     * So we cant write out anything more.
     *
     * @return the projects title
     */
    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
