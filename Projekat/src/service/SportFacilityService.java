package service;

import beans.SportFacility;
import com.google.gson.Gson;
import repository.SportFacilityRepository;

public class SportFacilityService {

    SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
    Gson gson = new Gson();
    public String getAll() {

//        ArrayList<WorkoutType> list1 = new ArrayList<WorkoutType>();
//        list1.add(new WorkoutType("Grupni"));
//        //list1.add(new WorkoutType("Personalni"));
////        list1.add(new WorkoutType("Zenski"));
////        list1.add(new WorkoutType("Muski"));
//
//        ArrayList<WorkoutType> list2 = new ArrayList<WorkoutType>();
//        list2.add(new WorkoutType("Grupni"));
//        list2.add(new WorkoutType("Personalni"));
//
//        //Location location1 = new Location(10,10,"Petra Drapsina", "117", "Sajkascity", 21244);
//        Location location3 = new Location(50,50,"Pa Pavla", "50", "Novi Sad", 21200);
//
//        SportFacility sportFacility2 = new SportFacility("Jaka teretana", new FacilityType("Teretana"),
//                list2, true,
//                location3, "", 4.4, LocalTime.of(5,00), LocalTime.of(22,00));
//        addOne(sportFacility2);

        return gson.toJson(sportFacilityRepository.getAll());
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
