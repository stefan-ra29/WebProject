package repository;

import beans.WorkoutHistory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WorkoutHistoryRepository extends Repository<WorkoutHistory, String>{

    @Override
    protected String getKey(WorkoutHistory workoutHistory) {
        return workoutHistory.getId();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<WorkoutHistory>>>() {}.getType();
    }
}
