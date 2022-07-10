package service;

import beans.Customer;
import beans.Membership;
import com.google.gson.Gson;
import repository.CustomerRepository;
import repository.CustomerTypeRepository;
import repository.MembershipRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MembershipService {
    MembershipRepository membershipRepository = new MembershipRepository();
    CustomerRepository customerRepository = new CustomerRepository();
    CustomerTypeRepository customerTypeRepository = new CustomerTypeRepository();
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
            if(membership.getCustomerId().equals(customerId) && (membership.getIsActive() == true && membership.getExpirationDate().compareTo(LocalDate.now()) >= 0))
                return membership;
        }
        return null;
    }

    public Membership getActiveExpiredMembership(String customerId) {

        for(Membership membership : membershipRepository.getAll()) {
            if(membership.getCustomerId().equals(customerId) && (membership.getIsActive() == true && membership.getExpirationDate().compareTo(LocalDate.now()) < 0))
                return membership;
        }
        return null;
    }

    public void subtractOneVisitFromMembership(String customerId){
        Membership membership = this.getActiveMembershipIfExists(customerId);

        membership.setAvailableVisits( membership.getAvailableVisits() - 1 );

        membershipRepository.update(membership.getId(), membership);
    }

    public void checkMembershipExpiration(String customerId) {

        Membership membership = getActiveExpiredMembership(customerId);
        if(membership != null) {
            addPointsToCustomer(customerId, membership);
            setMembershipToInactive(membership);
            changeCustomerTypeIfNeeded(customerId);
        }

    }

    public void addPointsToCustomer(String customerId, Membership membership){
        int visitsUsed;
        switch (membership.getType()) {

            case "Godisnja":
                visitsUsed = 365 - membership.getAvailableVisits();
                break;
            default:
                visitsUsed = 30 - membership.getAvailableVisits();
                break;
        }
        int membershipFee = membership.getFee();

        double pointsToAdd = (membershipFee/1000.0) * visitsUsed;

        subtractPointsFromCustomer(customerId, pointsToAdd, membership, visitsUsed);
    }

    public void subtractPointsFromCustomer(String customerId, double pointsToAdd, Membership membership, int visitsUsed){
        double pointsToSubtract = 0;
        switch (membership.getType()) {

            case "Godisnja":
                if(visitsUsed < 121)
                    pointsToSubtract = membership.getFee()/1000 * 133 * 4;
                break;
            default:
                if(visitsUsed < 10)
                    pointsToSubtract = membership.getFee()/1000 * 133 * 4;
                break;
        }

        double sumOfPoints = pointsToAdd - pointsToSubtract;

        Customer customer = customerRepository.getOne(customerId);
        if(customer.getPoints() + sumOfPoints < 0)
            customer.setPoints(0.0);
        else
            customer.setPoints(customer.getPoints() + sumOfPoints);
        customerRepository.update(customerId, customer);

    }

    public void setMembershipToInactive(Membership membership){
        membership.setIsActive(false);
        membershipRepository.update(membership.getId(), membership);
    }

    public void changeCustomerTypeIfNeeded(String customerId){
        Customer customer = customerRepository.getOne(customerId);

        if((customer.getPoints() >= 800 && customer.getPoints() < 1000))
        {
            customer.setCustomerTypeName("Bronzani");
        }
        else if((customer.getPoints() >= 1000 && customer.getPoints() < 1300))
        {
            customer.setCustomerTypeName("Srebrni");
        }
        else if((customer.getPoints() >= 1300))
        {
            customer.setCustomerTypeName("Zlatni");
        }

        customerRepository.update(customer.getUsername(), customer);
    }

    public boolean isMembershipActiveOnScheduledDate(LocalDateTime date, String customerId){
        Membership membership = getActiveMembershipIfExists(customerId);

        if(date.toLocalDate().isAfter(membership.getExpirationDate())){
            return false;
        }

        return true;
    }

    public boolean isAvailableVisitLeft(String customerId) {
        Membership membership = getActiveMembershipIfExists(customerId);
        if(membership.getAvailableVisits() == 0)
            return false;

        return true;
    }

}
