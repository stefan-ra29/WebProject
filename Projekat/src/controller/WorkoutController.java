package controller;

import beans.Workout;
import com.google.gson.Gson;
import service.WorkoutService;

import static spark.Spark.get;
import static spark.Spark.post;

public class WorkoutController {

    private static Gson gson = new Gson();
    public static WorkoutService workoutService = new WorkoutService();

    public static void GetWorkoutTypes() {
        get("rest/workouts/get_types", (req, res) -> {
            res.type("application/json");

            return workoutService.GetWorkoutTypes();
        });

    }
    public static void CreateWorkout() {
        post("rest/workouts/create_workout", (req, res) -> {
            res.type("application/json");

            Workout workout = gson.fromJson(req.body(), Workout.class);
            return workoutService.CreateWorkout(workout);
        });

    }
}
