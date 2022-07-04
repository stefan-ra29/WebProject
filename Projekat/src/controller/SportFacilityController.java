package controller;

import beans.SportFacility;
import com.google.gson.Gson;
import repository.ManagerRepository;
import service.ManagerService;
import service.SportFacilityService;

import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;

public class SportFacilityController {
    private static Gson gson = new Gson();
    public static SportFacilityService sportFacilityService = new SportFacilityService();
    private static ManagerRepository managerRepository = new ManagerRepository();
    private static ManagerService managerService = new ManagerService();

    public static void GetAllSportFacilities(){
        get("rest/facilities/get_all", (req, res) ->{
            res.type("application/json");

            return sportFacilityService.getAll();

        });
    }

    public static void GetSportFacility(){
        get("rest/facilities/get_one", (req, res) ->{
            res.type("application/json");

            String id = req.queryParams("id");
            return sportFacilityService.getOne(id);

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

    public static void CreateNew() {
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

    public static void GetAvailableManagers(){
        get("rest/managers/getAvailableManagers", (req, res) ->{
            res.type("application/json");

            return managerService.getAvailableManagersUsernames();
        });
    }

}
