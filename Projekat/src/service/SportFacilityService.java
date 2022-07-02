package service;

import beans.SportFacility;
import com.google.gson.Gson;
import repository.SportFacilityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public String SearchSportFacilities(String criteria, String searchInput, String gradeCriteria){

        List<SportFacility> allFacilities = sportFacilityRepository.getAll();
        ArrayList<SportFacility> filteredList = new ArrayList<SportFacility>();

        for( SportFacility facility : allFacilities){
            switch (criteria) {
                case "name":
                    if(facility.getName().toLowerCase().contains(searchInput.toLowerCase().trim()))
                        filteredList.add(facility);
                    break;
                case "type":
                    if(facility.getType().getType().toLowerCase().contains(searchInput.toLowerCase().trim()))
                        filteredList.add(facility);
                    break;
                case "location":
                    if(facility.getLocation().getCity().toLowerCase().contains(searchInput.toLowerCase().trim()))
                        filteredList.add(facility);
                    break;

                case "average_grade":
                    switch(gradeCriteria){
                        case "1-2":
                            if(facility.getAverageGrade() > 1 && facility.getAverageGrade() <= 2)
                                filteredList.add(facility);
                            break;
                        case "2-3":
                            if(facility.getAverageGrade() > 2 && facility.getAverageGrade() <= 3)
                                filteredList.add(facility);
                            break;
                        case "3-4":
                            if(facility.getAverageGrade() > 3 && facility.getAverageGrade() <= 4)
                                filteredList.add(facility);
                            break;
                        case "4-5":
                            if(facility.getAverageGrade() > 4 && facility.getAverageGrade() <= 5)
                                filteredList.add(facility);
                            break;
                    }
                    break;
            }
        }

        return gson.toJson(filteredList);
    }

    public String SortSportFacilities(String sortBy ){

        List<SportFacility> allFacilities = sportFacilityRepository.getAll();
        ArrayList<SportFacility> filteredList = new ArrayList<SportFacility>();

        switch (sortBy) {
            case "name_increasing":
                Collections.sort(allFacilities, new Comparator<SportFacility>() {
                    @Override
                    public int compare(SportFacility o1, SportFacility o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                break;
            case "name_decreasing":
                Collections.sort(allFacilities, new Comparator<SportFacility>() {
                    @Override
                    public int compare(SportFacility o1, SportFacility o2) {
                        int i = o2.getName().compareTo(o1.getName());
                        return i;
                    }
                });
            case "location_increasing":
                Collections.sort(allFacilities, new LocationComparator());
                break;

            case "location_decreasing":
                Collections.sort(allFacilities, new LocationComparator().reversed());
                break;

            case "average_grade_increasing":
                Collections.sort(allFacilities, new AverageGradeComparator());
                break;

            case "average_grade_decreasing":
                Collections.sort(allFacilities, new AverageGradeComparator().reversed());
                break;
        }

        return gson.toJson(allFacilities);
    }
}
