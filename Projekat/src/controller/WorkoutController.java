package controller;

import beans.Workout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import service.MembershipService;
import service.WorkoutHistoryService;
import service.WorkoutService;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static spark.Spark.*;

public class WorkoutController {

    private static Gson gson = new Gson();
    public static WorkoutService workoutService = new WorkoutService();
    public static WorkoutHistoryService workoutHistoryService = new WorkoutHistoryService();
    public static MembershipService membershipService = new MembershipService();

    public static void getWorkoutTypes() {
        get("rest/workouts/get_types", (req, res) -> {
            res.type("application/json");

            return workoutService.getWorkoutTypes();
        });
    }
    public static void createWorkout() {
        post("rest/workouts/create_workout", (req, res) -> {
            res.type("application/json");

            Workout workout = gson.fromJson(req.body(), Workout.class);
            return workoutService.createWorkout(workout);
        });
    }

    public static void getWorkoutsByFacility() {
        get("rest/workouts/get_workouts_by_facility", (req, res) -> {
            res.type("application/json");

            String facilityID = req.queryParams("id");
            return workoutService.getWorkoutsByFacility(facilityID);
        });
    }

    public static void getCoachesNamesFromWorkoutList() {
        post("rest/workouts/get_coaches_names", (req, res) -> {
            res.type("application/json");

            ArrayList<Workout> workouts = gson.fromJson(req.body(), new TypeToken<ArrayList<Workout>>(){}.getType());
            return workoutService.getCoachesNamesFromWorkoutList(workouts);
        });
    }
    public static void getWorkoutByID() {
        get("rest/workouts/get_workout_by_id", (req, res) -> {
            res.type("application/json");

            String workoutID = req.queryParams("id");
            return workoutService.getWorkoutByID(workoutID);
        });
    }

    public static void changeWorkout() {
        put("rest/workouts/change_workout", (req, res) -> {
            res.type("application/json");

            Workout workout = gson.fromJson(req.body(), Workout.class);
            return workoutService.changeWorkout(workout);
        });
    }
    public static void searchWorkouts() {
        post("rest/workouts/search", (req, res) -> {
            res.type("application/json");

            ArrayList<Workout> workouts = gson.fromJson(req.body(), new TypeToken<ArrayList<Workout>>(){}.getType());
            String criteria = req.queryParams("criteria");
            String minPrice = req.queryParams("minPrice");
            String maxPrice = req.queryParams("maxPrice");

            return workoutService.searchWorkouts(workouts, criteria, minPrice, maxPrice);
        });
    }
    public static void sortWorkouts(){
        post("rest/workouts/sort", (req, res) ->{
            res.type("application/json");

            ArrayList<Workout> workouts = gson.fromJson(req.body(), new TypeToken<ArrayList<Workout>>(){}.getType());
            String sortBy = req.queryParams("sortBy");
            return workoutService.sortWorkouts(sortBy, workouts);

        });
    }

    public static void filterWorkouts(){
        post("rest/workouts/filter", (req, res) -> {
            res.type("application/json");

            ArrayList<Workout> workouts = gson.fromJson(req.body(), new TypeToken<ArrayList<Workout>>() {
            }.getType());
            String filterBy = req.queryParams("filterBy");
            return workoutService.filterWorkouts(filterBy, workouts);
        });
    }

    public static void checkInToWorkout(){
        post("rest/workouts/checkInToWorkout", (req, res) -> {
            res.type("application/json");

            String customerId = req.queryParams("customerId");
            String workoutId = req.queryParams("workoutID");


            return gson.toJson(workoutHistoryService.addWorkoutHistory(customerId, workoutId));
        });
    }

    public static void getPastMonthWorkoutHistoryForCustomer() {
        get("rest/workouts/getPastMonthWorkoutHistoryForCustomer", (req, res) -> {
            res.type("application/json");

            String customerId = req.queryParams("customerId");
            return gson.toJson(workoutHistoryService.getPastMonthWorkoutHistoryForCustomer(customerId));
        });
    }

    public static void scheduleWorkout(){
        post("rest/workouts/scheduleWorkout", (req, res) -> {
            res.type("application/json");

            String customerId = req.queryParams("customerId");
            String workoutId = req.queryParams("workoutId");
            String scheduleDate = req.queryParams("scheduleDate");
            int scheduleHours = Integer.parseInt(req.queryParams("scheduleHours"));
            int scheduleMinutes = Integer.parseInt(req.queryParams("scheduleMinutes"));

            int year = Integer.parseInt(scheduleDate.substring(0,4));
            int month = Integer.parseInt(scheduleDate.substring(5,7));
            int day = Integer.parseInt(scheduleDate.substring(8,10));

            LocalDateTime dateTime = LocalDateTime.of(year, month, day, scheduleHours, scheduleMinutes);

            boolean isMembershipActiveOnScheduledDate = membershipService.isMembershipActiveOnScheduledDate(dateTime, customerId);

            boolean isVisitLeft = membershipService.isAvailableVisitLeft(customerId);

            if(!isMembershipActiveOnScheduledDate)
                return "membershipExpired";

            else if(!isVisitLeft)
                return "noVisitsLeft";

            workoutService.scheduleWorkout(customerId, workoutId, dateTime);

            return "true";
        });
    }

    public static void checkPastScheduledWorkouts(){
        post("rest/workouts/checkPastScheduledWorkouts", (req, res) -> {
            res.type("application/json");

            workoutHistoryService.logPastScheduledWorkouts();

            return true;
        });
    }

    public static void getCoachesScheduledWorkouts(){
        get("rest/workouts/getCoachesScheduledWorkouts", (req, res) -> {
            res.type("application/json");

            String coachId = req.queryParams("coachId");

            return gson.toJson(workoutService.getCoachesScheduledWorkouts(coachId));
        });
    }

    public static void getCoachesGroupWorkouts(){
        get("rest/workouts/getCoachesGroupWorkouts", (req, res) -> {
            res.type("application/json");

            String coachId = req.queryParams("coachId");

            return gson.toJson(workoutService.getCoachesGroupWorkouts(coachId));
        });
    }

    public static void getCoachesOtherWorkouts(){
        get("rest/workouts/getCoachesOtherWorkouts", (req, res) -> {
            res.type("application/json");

            String coachId = req.queryParams("coachId");

            return gson.toJson(workoutService.getCoachesOtherWorkouts(coachId));
        });
    }

    public static void cancelScheduledWorkout(){
        post("rest/workouts/cancelScheduledWorkout", (req, res) -> {
            res.type("application/json");

            String id = req.queryParams("id");

            if(workoutService.isNowTwoDaysBeforeWorkoutTime(id))
            {
                workoutService.cancelScheduledWorkout(id);
                return true;
            }

            return false;
        });

    }
}
