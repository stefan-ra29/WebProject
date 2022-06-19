package repository;

import beans.SportFacility;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SportFacilityRepository extends Repository<SportFacility, String>{
    @Override
    protected String getKey(SportFacility sportFacility) {
        return sportFacility.getId();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<SportFacility>>>() {}.getType();
    }
}
