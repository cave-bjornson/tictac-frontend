package com.considlia.tictac.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

public class TimeLog implements Comparable<TimeLog> {

    private Long id;
    private final UUID internalId;
    private Duration duration;
    private boolean reported;
    private boolean submitted;
    private LocalDateTime dateTime;

    private TicTactivity ticTactivity;

    public TimeLog() {
        internalId = UUID.randomUUID();
    }

    public String getHHMMSS() {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getInternalId() {
        return internalId;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getDurationAsString() {
        return duration.toString();
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setDuration(String durationAsString) {
        this.duration = Duration.parse(durationAsString);
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getDate() {
        return dateTime;
    }

    public OffsetDateTime getDateTime() {
        return OffsetDateTime.of(dateTime, ZoneOffset.UTC);
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDateTime(OffsetDateTime offsetDateTime) {
        this.dateTime = offsetDateTime.toLocalDateTime();
    }

    public void setDate(LocalDate date) {
        this.dateTime = date.atStartOfDay();
    }

    public TicTactivity getTicTactivity() {
        return ticTactivity;
    }

    public void setTicTactivity(TicTactivity ticTactivity) {
        this.ticTactivity = ticTactivity;
    }

    @Override
    public int compareTo(TimeLog o) {
        return this.dateTime.compareTo(o.getDate());
    }

    @Override
    public String toString() {
        return "TimeLog{" +
                "id=" + internalId +
                ", duration=" + duration +
                ", reported=" + reported +
                ", submitted=" + submitted +
                ", dateTime=" + dateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeLog timeLog = (TimeLog) o;
        return internalId.equals(timeLog.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalId);
    }
}
