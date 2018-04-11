package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository repo = null;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository repo,
                               CounterService counter,
                               GaugeService gauge) {
        this.counter = counter;
        this.gauge = gauge;
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){

        ResponseEntity<TimeEntry> returnValue;
        TimeEntry returnEntry;
        returnEntry = repo.create(timeEntry);

        returnValue = new ResponseEntity<>(returnEntry,HttpStatus.CREATED);

        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", repo.list().size());

        return returnValue;
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id){
        ResponseEntity<TimeEntry> returnValue;

        TimeEntry returnEntry = repo.find(id);

        if(returnEntry != null){
            returnValue = new ResponseEntity<TimeEntry>(returnEntry,HttpStatus.OK);
        }
        else{
            returnValue = new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }

        counter.increment("TimeEntry.read");

        return returnValue;

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
        ResponseEntity<List<TimeEntry>> returnValue;

        returnValue = new ResponseEntity<List<TimeEntry>>(repo.list(),HttpStatus.OK);

        counter.increment("TimeEntry.list");

        return returnValue;
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry timeEntry){
        ResponseEntity<TimeEntry> returnValue;
        TimeEntry returnEntry;



        returnEntry = repo.update(id,timeEntry);

        if (returnEntry != null){
            returnValue = new ResponseEntity<TimeEntry>(returnEntry,HttpStatus.OK);
        }
        else{
            returnValue = new ResponseEntity<TimeEntry>(HttpStatus.NOT_FOUND);
        }

        counter.increment("TimeEntry.update");

        return returnValue;

    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {

        ResponseEntity<TimeEntry> returnValue;
        TimeEntry returnEntry;


        returnEntry = repo.delete(id);

        if (returnEntry != null){
            returnValue = new ResponseEntity<TimeEntry>(returnEntry,HttpStatus.NO_CONTENT);
        }
        else{
            returnValue = new ResponseEntity<TimeEntry>(HttpStatus.NO_CONTENT);
        }

        counter.increment("TimeEntry.delete");
        gauge.submit("timeEntries.count", repo.list().size());

        return returnValue;
    }
}
