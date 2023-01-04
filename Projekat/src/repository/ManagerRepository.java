package repository;

import beans.Manager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagerRepository extends Repository<Manager, String>{
    @Override
    protected String getKey(Manager manager) {
        return manager.getUsername();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<Manager>>>() {}.getType();
    }
}
