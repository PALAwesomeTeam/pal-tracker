package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long, TimeEntry> timeEntryHashMap =
            new HashMap<Long, TimeEntry>();


    public TimeEntry create(TimeEntry timeEntry) {

        // TODO: check for collisions
        timeEntryHashMap.put((long) timeEntryHashMap.size()+1, timeEntry);
        timeEntry.setId(timeEntryHashMap.size());

        return timeEntry;
    }

    public TimeEntry find(Long index) {

        TimeEntry te = null;

        if (timeEntryHashMap.containsKey(index)) {
            te = timeEntryHashMap.get(index);
        }

        return te;
    }

    public List<TimeEntry> list() {

        return timeEntryHashMap.values().stream().collect(Collectors.toList());

    }

    public TimeEntry update(Long id, TimeEntry toAdd) {

        TimeEntry retVal = null;

        if (timeEntryHashMap.containsKey(id)) {
            toAdd.setId(id);
            timeEntryHashMap.replace(id, toAdd);
            retVal = this.find(id);
        }

        return retVal;
    }

    public TimeEntry delete(Long id){

        TimeEntry returnEntry = null;
        if (timeEntryHashMap.containsKey(id)) {
            returnEntry = timeEntryHashMap.get(id);

            timeEntryHashMap.remove(id);
        } else {
            return null;
        }

        return  returnEntry;
    }

}
