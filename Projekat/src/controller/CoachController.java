package controller;

import com.google.gson.Gson;
import dto.CustomerDTO;
import service.CoachService;

import static spark.Spark.post;

public class CoachController {
    private static Gson g = new Gson();
    private static CoachService coachService = new CoachService();

    public static void registerCoach(){
        post("rest/coaches/register/", (req, res) -> {
            res.type("application/json");

            CustomerDTO customerDTO = g.fromJson(req.body(), CustomerDTO.class);
            return coachService.registerCoach(customerDTO);
        });
    }
}
