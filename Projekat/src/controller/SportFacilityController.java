package controller;

import beans.SportFacility;

import java.util.List;

public class SportFacilityController {
    SportFacilityController sportFacilityController = new SportFacilityController();

    public List<SportFacility> getAll() {
        return sportFacilityController.getAll();
    }
    public SportFacility getOne(String id) {
        return sportFacilityController.getOne(id);
    }

    public boolean addOne(SportFacility newSportFacility) {
        return sportFacilityController.addOne(newSportFacility);
    }

    public void update(String id, SportFacility newSportFacility) {
        sportFacilityController.update(id, newSportFacility);
    }

    public void delete(String id) {
        sportFacilityController.delete(id);
    }
}
