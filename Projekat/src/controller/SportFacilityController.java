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
            String criteria = req.queryParams("criteria");
            String searchInput = req.queryParams("searchInput");
            String gradeCriteria = req.queryParams("gradeCriteria");
            return sportFacilityService.searchSportFacilities(criteria, searchInput, gradeCriteria);
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

//            Manager customer = new Manager();
//            customer.setUsername("menadzer1");
//            customer.setPassword("menadzer1");
//            customer.setFirstName("Miki");
//            customer.setLastName("Roki");
//            customer.setRole(Role.Manager);
//            customer.setGender("male");
//            customer.setEmail("m1@gmail.com");
//
//            Manager customer2 = new Manager();
//            customer2.setUsername("menadzer2");
//            customer2.setPassword("menadzer2");
//            customer2.setFirstName("Koki");
//            customer2.setLastName("Boki");
//            customer2.setRole(Role.Manager);
//            customer2.setGender("male");
//            customer2.setEmail("m2@gmail.com");
//
//            managerRepository.addOne(customer);
//            managerRepository.addOne(customer2);

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
