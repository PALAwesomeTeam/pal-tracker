package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository repo = null;
    public TimeEntryController(TimeEntryRepository repo){
        this.repo = repo;
    }


    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry){

        ResponseEntity<TimeEntry> returnValue;
        TimeEntry returnEntry;
        returnEntry = repo.create(timeEntry);

        returnValue = new ResponseEntity<>(returnEntry,HttpStatus.CREATED);

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

        return returnValue;

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){
        ResponseEntity<List<TimeEntry>> returnValue;

        returnValue = new ResponseEntity<List<TimeEntry>>(repo.list(),HttpStatus.OK);

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


        return returnValue;
    }
}
