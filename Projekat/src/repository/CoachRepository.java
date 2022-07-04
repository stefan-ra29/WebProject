package repository;

import beans.Coach;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CoachRepository  extends Repository<Coach, String>{
    @Override
    protected String getKey(Coach coach) {
        return coach.getUsername();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<Coach>>>() {}.getType();
    }
}