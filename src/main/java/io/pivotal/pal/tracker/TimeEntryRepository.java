package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry timeEntry);
    public TimeEntry update(Long id, TimeEntry timeEntry);
    public List<TimeEntry> list();
    public TimeEntry delete(Long id);
    public TimeEntry find(Long id);

}
