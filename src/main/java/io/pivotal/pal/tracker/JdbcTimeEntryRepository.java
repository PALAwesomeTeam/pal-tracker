package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.SqlValue;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate template;
    public JdbcTimeEntryRepository(DataSource ds){
        template = new JdbcTemplate(ds);
    }

    public TimeEntry create(TimeEntry timeEntry) {
        int returnID;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        returnID = template.update( connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " +
                            "VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS

            );
            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4,timeEntry.getHours());
            return statement;
        }, keyHolder);

        return find(keyHolder.getKey().longValue());
    }


    public TimeEntry update(Long id, TimeEntry timeEntry) {

        template.update( connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? " +
                            "WHERE id = ?"
            );
            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4,timeEntry.getHours());
            statement.setLong(5, id);
            return statement;
        });

        return find(id);
    }


    public List<TimeEntry> list() {
        List<TimeEntry> returnList;
        returnList = template.query("SELECT id, project_id, user_id, date, hours" +
                " FROM time_entries", getTimeEntryRowMapper());

        return returnList;

    }


    public TimeEntry delete(Long id) {

        TimeEntry returnValue = find(id);

        template.update( connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM time_entries WHERE id = ?"
            );
            statement.setLong(1, id);
            return statement;
        });

        return returnValue;
    }


    public TimeEntry find(Long id) {
        List<TimeEntry> returnList;
        returnList = template.query("SELECT id, project_id, user_id, date, hours\n" +
                 " FROM time_entries where id = ?", getTimeEntryRowMapper(), id);

        if(!returnList.isEmpty()){
            return returnList.get(0);
        }
        else{
            //ToDo Find out what to do if we don't get a value back
            return null;
        }

    }

    RowMapper<TimeEntry> getTimeEntryRowMapper() {
        return new RowMapper<TimeEntry>() {
            public TimeEntry mapRow(ResultSet rs, int rownumber) throws SQLException {
                TimeEntry timeEntry = new TimeEntry();
                timeEntry.setId(rs.getLong("id"));
                timeEntry.setDate(rs.getDate("date").toLocalDate());
                timeEntry.setHours(rs.getInt("hours"));
                timeEntry.setProjectId(rs.getLong("project_id"));
                timeEntry.setUserId(rs.getLong("user_id"));
                return timeEntry;
            }
        };

    }
}
