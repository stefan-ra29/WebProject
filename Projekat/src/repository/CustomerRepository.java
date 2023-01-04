package repository;

import beans.Customer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CustomerRepository extends Repository<Customer, String>{
    @Override
    protected String getKey(Customer customer) {
        return customer.getUsername();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<Customer>>>() {}.getType();
    }
}
