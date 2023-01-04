package controller;

import beans.WorkoutHistory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CustomerWorkoutHistoryDTO;
import service.WorkoutHistoryService;
import service.WorkoutService;

import java.util.ArrayList;

import static spark.Spark.post;

public class WorkoutHistoryController {
    private static Gson gson = new Gson();
    public static WorkoutService workoutService = new WorkoutService();
    public static WorkoutHistoryService workoutHistoryService = new WorkoutHistoryService();

    public static void searchHistoryWorkouts() {
        post("rest/history_workouts/search", (req, res) -> {
            res.type("application/json");

            ArrayList<WorkoutHistory> workouts = gson.fromJson(req.body(), new TypeToken<ArrayList<WorkoutHistory>>(){}.getType());
            String criteria = req.queryParams("criteria");
            String minPrice = req.queryParams("minPrice");
            String maxPrice = req.queryParams("maxPrice");
            String minDate = req.queryParams("minDate");
            String maxDate = req.queryParams("maxDate");
            String nameSearch = req.queryParams("nameSearch");

            return workoutHistoryService.searchHistoryWorkouts(workouts, criteria, minPrice, maxPrice, minDate, maxDate, nameSearch);
        });
    }
    public static void sortHistoryWorkouts() {
        post("rest/history_workouts/sort", (req, res) -> {
            res.type("application/json");
            ArrayList<CustomerWorkoutHistoryDTO> workouts = gson.fromJson(req.body(), new TypeToken<ArrayList<CustomerWorkoutHistoryDTO>>(){}.getType());
            String sortBy = req.queryParams("sortBy");

            return workoutHistoryService.sortHistoryWorkouts(workouts, sortBy);
        });
    }

    public static void filterHistoryWorkouts() {
        post("rest/history_workouts/filter", (req, res) -> {
            res.type("application/json");
            ArrayList<WorkoutHistory> workouts = gson.fromJson(req.body(), new TypeToken<ArrayList<WorkoutHistory>>(){}.getType());
            String filterBy = req.queryParams("filterBy");
            String filterType = req.queryParams("filterType");

            return workoutHistoryService.filterHistoryWorkouts(workouts, filterType,  filterBy);
        });
    }
}
