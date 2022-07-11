package rest;

import controller.*;

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
        SportFacilityController.getAllSportFacilities();
        SportFacilityController.searchSportFacilities();
        SportFacilityController.sortSportFacilities();
        SportFacilityController.getSportFacilityTypes();
        SportFacilityController.filterSportFacilities();
        SportFacilityController.getCurrentlyOpenedSportFacilities();
        SportFacilityController.getSportFacility();
        SportFacilityController.createNew();
        SportFacilityController.getAvailableManagers();
        UserController.getLoggedUser();
        UserController.updateUserInfo();
        CoachController.registerCoach();
        ManagerController.registerManager();
        MembershipController.addMonthlyLight();
        WorkoutController.getWorkoutTypes();
        CoachController.getAll();
        WorkoutController.createWorkout();
        WorkoutController.getWorkoutsByFacility();
        CoachController.getCoach();
        WorkoutController.getCoachesNamesFromWorkoutList();
        WorkoutController.getWorkoutByID();
        WorkoutController.changeWorkout();
        WorkoutController.searchWorkouts();
        WorkoutController.sortWorkouts();
        WorkoutController.filterWorkouts();
        SportFacilityController.getIsFacilityCurrentlyWorking();
        WorkoutController.checkInToWorkout();
        UserController.getCoachesAndManagers();
        CustomerController.getAll();
        UserController.searchUsers();
        UserController.sortUsers();
        CustomerController.sortCustomers();
        CustomerController.filterCustomers();
        UserController.filterUsers();
        CoachController.getCoachesFromFacility();
        CustomerController.getCustomersWhoVisitedFacitily();
        CommentController.checkForUnfilledComments();
        CommentController.getCommentsForFacility();
        CommentController.addNewUnapprovedComment();
        CommentController.approveComment();
        CommentController.getUnapprovedComments();
        SportFacilityController.getFacilityNames();
        CommentController.deleteComment();
    }
}
