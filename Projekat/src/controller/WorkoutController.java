package controller;

import beans.Workout;
import com.google.gson.Gson;
import service.WorkoutService;

import static spark.Spark.get;
import static spark.Spark.post;

public class WorkoutController {

    private static Gson gson = new Gson();
    public static WorkoutService workoutService = new WorkoutService();

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
}
