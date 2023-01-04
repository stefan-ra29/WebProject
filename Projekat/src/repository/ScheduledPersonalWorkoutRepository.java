package repository;

import beans.ScheduledPersonalWorkout;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ScheduledPersonalWorkoutRepository extends Repository<ScheduledPersonalWorkout, String>{

    @Override
    protected String getKey(ScheduledPersonalWorkout scheduledPersonalWorkout) {
        return scheduledPersonalWorkout.getId();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<ScheduledPersonalWorkout>>>() {}.getType();
    }
}
