package service;

import beans.SportFacility;
import repository.SportFacilityRepository;

import java.util.List;

public class SportFacilityService {

    SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();

    public List<SportFacility> getAll() {
        return sportFacilityRepository.getAll();
    }
    public SportFacility getOne(String id) {
        return sportFacilityRepository.getOne(id);
    }

    public boolean addOne(SportFacility newSportFacility) {
        return sportFacilityRepository.addOne(newSportFacility);
    }

    public void update(String id, SportFacility newSportFacility) {
        sportFacilityRepository.update(id, newSportFacility);
    }

    public void delete(String id) {
        sportFacilityRepository.delete(id);
    }

//    private void save(List<LogicalEntity<Entity>> entites) {
//        try {
//            FileWriter fw = new FileWriter(filePath);
//            gson.toJson(entites, fw);
//            fw.close();
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
