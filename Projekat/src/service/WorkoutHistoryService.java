package service;

import beans.Membership;
import beans.Workout;
import beans.WorkoutHistory;
import com.google.gson.Gson;
import repository.CoachRepository;
import repository.WorkoutHistoryRepository;
import repository.WorkoutRepository;

import java.time.LocalDateTime;

public class WorkoutHistoryService {
    private WorkoutHistoryRepository workoutHistoryRepository = new WorkoutHistoryRepository();
    private Gson gson = new Gson();
    private CoachRepository coachRepository = new CoachRepository();
    private CustomerService customerService = new CustomerService();
    private WorkoutRepository workoutRepository = new WorkoutRepository();
    private MembershipService membershipService = new MembershipService();
    private CoachService coachService = new CoachService();

    public boolean addWorkoutHistory(String customerId, String workoutId){

        Workout workout = workoutRepository.getOne(workoutId);
        String facilityId = workout.getSportFacilityID();

        String coachId = workout.getCoachID();

        Membership membership = membershipService.getActiveMembershipIfExists(customerId);

        if(membership.getAvailableVisits() == 0)
            return false;

        WorkoutHistory workoutHistory = new WorkoutHistory(LocalDateTime.now(), workoutId, customerId, coachId);


        workoutHistoryRepository.addOne(workoutHistory);



        customerService.logWorkoutToCustomer(customerId, facilityId);

        membershipService.subtractOneVisitFromMembership(customerId);

        if(!coachId.equals("")){
            coachService.addWorkoutToCoachesWorkoutHistory(coachId, workoutHistory.getId());
        }

        return true;
    }

}
