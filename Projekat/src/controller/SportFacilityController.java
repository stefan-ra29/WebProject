package controller;

import beans.SportFacility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import repository.ManagerRepository;
import service.ManagerService;
import service.SportFacilityService;

import java.util.ArrayList;
import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;

public class SportFacilityController {
    private static Gson gson = new Gson();
    public static SportFacilityService sportFacilityService = new SportFacilityService();
    private static ManagerRepository managerRepository = new ManagerRepository();
    private static ManagerService managerService = new ManagerService();

    public static void getAllSportFacilities(){
        get("rest/facilities/get_all", (req, res) ->{
            res.type("application/json");

            return sportFacilityService.getAll();

        });
    }

    public static void getSportFacility(){
        get("rest/facilities/get_one", (req, res) ->{
            res.type("application/json");

            String id = req.queryParams("id");
            return sportFacilityService.getOne(id);

        });
    }
    public static void searchSportFacilities(){
        post("rest/facilities/search", (req, res) ->{
            res.type("application/json");

            ArrayList<SportFacility> facilityList = gson.fromJson(req.body(),  new TypeToken<ArrayList<SportFacility>>(){}.getType());
            String nameSearch = req.queryParams("nameSearch");
            String typeSearch = req.queryParams("typeSearch");
            String locationSearch = req.queryParams("locationSearch");
            String gradeCriteria = req.queryParams("gradeCriteria");
            return sportFacilityService.searchSportFacilities(nameSearch, typeSearch, locationSearch, gradeCriteria, facilityList);
        });
    }

    public static void sortSportFacilities(){
        get("rest/facilities/sort", (req, res) ->{
            res.type("application/json");

            String sortBy = req.queryParams("sortBy");
            return sportFacilityService.sortSportFacilities(sortBy);

        });
    }

    public static void getSportFacilityTypes(){
        get("rest/facilities/get_facility_types", (req, res) ->{
            res.type("application/json");

            return sportFacilityService.getSportFacilityTypes();

        });
    }
    public static void filterSportFacilities(){
        get("rest/facilities/filter", (req, res) ->{
            res.type("application/json");

            String filterBy = req.queryParams("filterBy");
            return sportFacilityService.filterSportFacilities(filterBy);

        });
    }

    public static void getCurrentlyOpenedSportFacilities(){
        get("rest/facilities/get_currently_opened_facilities", (req, res) ->{
            res.type("application/json");

            return sportFacilityService.getCurrentlyOpenedSportFacilities();

        });
    }

    public static void createNew() {
        post("rest/facilities/createNew/", (req, res) -> {
            res.type("application/json");

            SportFacility facility = gson.fromJson(req.body(), SportFacility.class);
            facility.setId(UUID.randomUUID().toString());

            managerService.setFacilityToManager(req.queryParams("manager"), facility.getId());

            return sportFacilityService.addOne(facility);
        });
    }

    public static void getAvailableManagers(){
        get("rest/managers/getAvailableManagers", (req, res) ->{
            res.type("application/json");

            return managerService.getAvailableManagersUsernames();
        });
    }

}
