package service;

import beans.*;
import com.google.gson.Gson;
import dto.CustomerWorkoutHistoryDTO;
import repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

                CustomerWorkoutHistoryDTO dto = new CustomerWorkoutHistoryDTO(workout.getName(), sportFacility.getName(), workoutHistory.getStartTime(), workoutHistory.getWorkoutId(), workoutHistory.getCustomerId(), workout.getSupplement());

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

    public String searchHistoryWorkouts(ArrayList<WorkoutHistory>workouts, String criteria, String minPrice, String maxPrice, String minDate, String maxDate, String nameSearch){
        ArrayList<WorkoutHistory> filteredWorkouts = new ArrayList<WorkoutHistory>();
        ArrayList<CustomerWorkoutHistoryDTO> workoutsDTO = createDTOFromBeans(workouts);

        switch (criteria) {
            case "facilityName":

                for(CustomerWorkoutHistoryDTO workoutDTO : workoutsDTO){
                    if(workoutDTO.getFacilityName().toLowerCase().contains(nameSearch)){
                        filteredWorkouts.add(workouts.get(workoutsDTO.indexOf(workoutDTO)));
                    }
                }
                break;
            case "withoutSupplement":

                for(WorkoutHistory workout : workouts){
                    Workout w = workoutRepository.getOne(workout.getWorkoutId());
                    if(w.getSupplement() == 0){
                        filteredWorkouts.add(workout);
                    }
                }
                break;
            case "withSupplement":

                if(Integer.valueOf(minPrice) > Integer.valueOf(maxPrice)){
                    return gson.toJson(filteredWorkouts);
                }
                for(WorkoutHistory workout : workouts){
                    Workout w = workoutRepository.getOne(workout.getWorkoutId());
                    if(w.getSupplement() >= Integer.valueOf(minPrice) && w.getSupplement() <= Integer.valueOf(maxPrice) )
                        filteredWorkouts.add(workout);
                }
                break;
            case "workoutDate":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateMin = LocalDate.parse(minDate, formatter);
                LocalDate dateMax = LocalDate.parse(maxDate, formatter);
                LocalDateTime dateMinWithTime = LocalDateTime.of(dateMin.getYear(), dateMin.getMonth(), dateMin.getDayOfMonth(), 0, 0);
                LocalDateTime dateMaxWithTime = LocalDateTime.of(dateMax.getYear(), dateMax.getMonth(), dateMax.getDayOfMonth(), 0, 0);
                if(dateMinWithTime.isAfter(dateMaxWithTime) ){
                    return gson.toJson(filteredWorkouts);
                }
                for(WorkoutHistory workout : workouts){
                    if(workout.getStartTime().isAfter(dateMinWithTime) && workout.getStartTime().isBefore(dateMaxWithTime) )
                        filteredWorkouts.add(workout);
                }
                break;
        }
        ArrayList<CustomerWorkoutHistoryDTO> workoutsForSending = createDTOFromBeans(filteredWorkouts);
        return gson.toJson(workoutsForSending);
    }
    public ArrayList<CustomerWorkoutHistoryDTO> createDTOFromBeans(ArrayList<WorkoutHistory>workouts){

        ArrayList<CustomerWorkoutHistoryDTO> workoutsDTO = new ArrayList<CustomerWorkoutHistoryDTO>();
        for(WorkoutHistory w : workouts){

            Workout workout = workoutRepository.getOne(w.getWorkoutId());
            SportFacility sportFacility = sportFacilityRepository.getOne(workout.getSportFacilityID());

            CustomerWorkoutHistoryDTO dto = new CustomerWorkoutHistoryDTO(workout.getName(), sportFacility.getName(), w.getStartTime(), w.getWorkoutId(), w.getCustomerId(), workout.getSupplement());

            workoutsDTO.add(dto);
        }
        return  workoutsDTO;
    }
}
