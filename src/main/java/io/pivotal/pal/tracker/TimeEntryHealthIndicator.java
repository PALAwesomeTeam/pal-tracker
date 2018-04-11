package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    TimeEntryRepository repo;
    final static int MAX_THRESHOLD_ENTRIES = 5;

    public TimeEntryHealthIndicator(TimeEntryRepository repo) {
        this.repo = repo;
    }

    public Health health() {
        if(repo.list().size() >= MAX_THRESHOLD_ENTRIES) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
