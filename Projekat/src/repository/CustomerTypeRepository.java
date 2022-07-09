package repository;

import beans.CustomerType;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerTypeRepository extends Repository<CustomerType, String>{
    @Override
    protected String getKey(CustomerType customerType) {
        return customerType.getTypeName();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<CustomerType>>>() {}.getType();
    }
}
