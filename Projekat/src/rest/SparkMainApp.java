package rest;

import controller.SportFacilityController;
import controller.TestController;
import repository.SportFacilityRepository;
import service.SportFacilityService;

import java.io.File;
import java.io.IOException;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class SparkMainApp {
    public static SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
    public static SportFacilityService sportFacilityService = new SportFacilityService();
    public static SportFacilityController sportFacilityController = new SportFacilityController();
    public static void main(String[] args) throws IOException {
        port(8081);
        staticFiles.externalLocation(new File("./static").getCanonicalPath());
        TestController.test();
    }
}
