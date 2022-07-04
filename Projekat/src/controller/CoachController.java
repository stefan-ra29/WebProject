package controller;

import com.google.gson.Gson;
import service.CoachService;

import static spark.Spark.post;

public class CoachController {
    private static Gson g = new Gson();
    private static CoachService coachService = new CoachService();

    public static void registerCoach(){
        post("rest/coaches/register", (req, res) -> {
            res.type("application/json");
            return coachService.registerCoach();
        });
    }
}
