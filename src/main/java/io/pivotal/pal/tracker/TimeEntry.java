package io.pivotal.pal.tracker;

import java.time.LocalDate;
import java.util.List;

public class TimeEntry {

    private long id;
    private long projectId;
    private long userId;
    private LocalDate date;
    private int hours;

    public TimeEntry() {}

    public TimeEntry(long id, long projectId, long userId,
                     LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntry(long projectId, long userId,
                     LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object te) {
        if (te.getClass().getName().equals("io.pivotal.pal.tracker.TimeEntry")){
            TimeEntry tmp = (TimeEntry)te;
            if (this.id == tmp.id && this.projectId == tmp.projectId && this.date.equals(tmp.date) && this.hours == tmp.hours)
                return true;
            else
                return false;
        }
        else
            return false;


    }
}
