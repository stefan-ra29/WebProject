package repository;

import beans.Membership;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MembershipRepository extends Repository<Membership, String>{

    @Override
    protected String getKey(Membership membership) {
        return membership.getId();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<Membership>>>() {}.getType();
    }
}
