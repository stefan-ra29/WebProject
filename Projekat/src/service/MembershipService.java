package service;

import beans.Membership;
import com.google.gson.Gson;
import repository.MembershipRepository;

public class MembershipService {
    MembershipRepository membershipRepository = new MembershipRepository();
    Gson gson = new Gson();

    public boolean addOne(Membership membership){
        return membershipRepository.addOne(membership);
    }

    public void deactivatePreviousMemberships(String customerId){
        for(Membership membership : membershipRepository.getAll()){
            if(membership.getCustomerId().equals(customerId) && membership.getIsActive() == true){
                membership.setIsActive(false);
                membershipRepository.update(membership.getId(), membership);
            }
        }
    }
}
