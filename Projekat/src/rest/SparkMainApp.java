package rest;

import controller.CoachController;
import controller.CustomerController;
import controller.UserController;
import controller.SportFacilityController;

import java.io.File;
import java.io.IOException;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class SparkMainApp {

    public static void main(String[] args) throws IOException {
        port(8081);
        staticFiles.externalLocation(new File("./static").getCanonicalPath());
        CustomerController.registerCustomer();
        UserController.login();
        SportFacilityController.GetAllSportFacilities();
        SportFacilityController.SearchSportFacilities();
        SportFacilityController.SortSportFacilities();
        SportFacilityController.GetSportFacilityTypes();
        SportFacilityController.FilterSportFacilities();
        SportFacilityController.GetCurrentlyOpenedSportFacilities();
        SportFacilityController.GetSportFacility();
        SportFacilityController.CreateNew();
        SportFacilityController.GetAvailableManagers();
        UserController.getLoggedUser();
        UserController.updateUserInfo();
        CoachController.registerCoach();
    }
}
