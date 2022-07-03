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
            String facilities = req.body();
            System.out.println(facilities);
            return sportFacilityService.SearchSportFacilities(criteria, searchInput, gradeCriteria, facilities);
        });
    }

    public static void SortSportFacilities(){
        get("rest/facilities/sort", (req, res) ->{
            res.type("application/json");

            String sortBy = req.queryParams("sortBy");
            return sportFacilityService.SortSportFacilities(sortBy);

        });
    }

    public static void GetSportFacilityTypes(){
        get("rest/facilities/get_facility_types", (req, res) ->{
            res.type("application/json");

            return sportFacilityService.GetSportFacilityTypes();

        });
    }
    public static void FilterSportFacilities(){
        get("rest/facilities/filter", (req, res) ->{
            res.type("application/json");

            String filterBy = req.queryParams("filterBy");
            return sportFacilityService.FilterSportFacilities(filterBy);

        });
    }

    public static void GetCurrentlyOpenedSportFacilities(){
        get("rest/facilities/get_currently_opened_facilities", (req, res) ->{
            res.type("application/json");

            return sportFacilityService.GetCurrentlyOpenedSportFacilities();

        });
    }
}
