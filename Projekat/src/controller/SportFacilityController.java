package controller;

import com.google.gson.Gson;
import service.SportFacilityService;

import static spark.Spark.get;

public class SportFacilityController {
    private static Gson gson = new Gson();
    public static SportFacilityService sportFacilityService = new SportFacilityService();
    public static void GetAllSportFacilities(){
        get("rest/facilities/get_all", (req, res) ->{
            res.type("application/json");

            return sportFacilityService.getAll();

        });
    }
    public static void SearchSportFacilities(){
        get("rest/facilities/search", (req, res) ->{
            res.type("application/json");

            String criteria = req.queryParams("criteria");
            String searchInput = req.queryParams("searchInput");
            String gradeCriteria = req.queryParams("gradeCriteria");
            return sportFacilityService.SearchSportFacilities(criteria, searchInput, gradeCriteria);

        });
    }
}
