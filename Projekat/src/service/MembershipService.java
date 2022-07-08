package service;

import beans.Membership;
import com.google.gson.Gson;
import repository.MembershipRepository;

import java.time.LocalDate;

public class MembershipService {
    MembershipRepository membershipRepository = new MembershipRepository();
    Gson gson = new Gson();

    public boolean addOne(Membership membership){
        return membershipRepository.addOne(membership);
    }

    public void deactivatePreviousMemberships(String customerId){
        for(Membership membership : membershipRepository.getAll()){
            if(membership.getCustomerId().equals(customerId) && (membership.getIsActive() == true || membership.getExpirationDate().compareTo(LocalDate.now()) < 0))
            {
                membership.setIsActive(false);
                membershipRepository.update(membership.getId(), membership);
            }
        }
    }

    public Membership getActiveMembershipIfExists(String customerId){

        for(Membership membership : membershipRepository.getAll()){
            if(membership.getCustomerId().equals(customerId) && (membership.getIsActive() == true && membership.getExpirationDate().compareTo(LocalDate.now()) > 0))
                return membership;
        }

        return null;
    }

    public void subtractOneVisitFromMembership(String customerId){
        Membership membership = this.getActiveMembershipIfExists(customerId);

        membership.setAvailableVisits( membership.getAvailableVisits() - 1 );

        membershipRepository.update(membership.getId(), membership);
    }

}
