package service;

import beans.*;
import com.google.gson.Gson;
import dto.CustomerWorkoutHistoryDTO;
import repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class WorkoutHistoryService {
    private WorkoutHistoryRepository workoutHistoryRepository = new WorkoutHistoryRepository();
    private ScheduledPersonalWorkoutRepository scheduledPersonalWorkoutRepository = new ScheduledPersonalWorkoutRepository();
    private Gson gson = new Gson();
    private CoachRepository coachRepository = new CoachRepository();
    private CustomerService customerService = new CustomerService();
    private WorkoutRepository workoutRepository = new WorkoutRepository();
    private MembershipService membershipService = new MembershipService();
    private CoachService coachService = new CoachService();
    private SportFacilityRepository sportFacilityRepository =  new SportFacilityRepository();

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

    public ArrayList<CustomerWorkoutHistoryDTO> getPastMonthWorkoutHistoryForCustomer(String customerId){
        ArrayList<CustomerWorkoutHistoryDTO> history = new ArrayList<CustomerWorkoutHistoryDTO>();

        for(WorkoutHistory workoutHistory : workoutHistoryRepository.getAll()){
            if(workoutHistory.getCustomerId().equals(customerId) && workoutHistory.getStartTime().plusMonths(1).isAfter(LocalDateTime.now())){

                Workout workout = workoutRepository.getOne(workoutHistory.getWorkoutId());
                SportFacility sportFacility = sportFacilityRepository.getOne(workout.getSportFacilityID());

                CustomerWorkoutHistoryDTO dto = new CustomerWorkoutHistoryDTO(workout.getName(), sportFacility.getName(), workoutHistory.getStartTime());

                history.add(dto);
            }
        }

        return history;
    }

    public void logPastScheduledWorkouts() {
        for (ScheduledPersonalWorkout scheduledPersonalWorkout : scheduledPersonalWorkoutRepository.getAll()) {
            if(scheduledPersonalWorkout.getScheduledTime().isBefore(LocalDateTime.now())) {
                WorkoutHistory workoutHistory = new WorkoutHistory(scheduledPersonalWorkout);

                workoutHistoryRepository.addOne(workoutHistory);
                scheduledPersonalWorkoutRepository.delete(scheduledPersonalWorkout.getId());

                Workout workout = workoutRepository.getOne(workoutHistory.getWorkoutId());

                customerService.logWorkoutToCustomer(workoutHistory.getCustomerId(), workout.getSportFacilityID());

                membershipService.subtractOneVisitFromMembership(workoutHistory.getCustomerId());

                if(!workoutHistory.getCoachId().equals("")){
                    coachService.addWorkoutToCoachesWorkoutHistory(workoutHistory.getCoachId(), workoutHistory.getId());
                }

            }
        }
    }

}
