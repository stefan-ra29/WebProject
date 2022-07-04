package repository;

import beans.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagerRepository extends Repository<User, String>{
    @Override
    protected String getKey(User user) {
        return user.getUsername();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<User>>>() {}.getType();
    }
}
