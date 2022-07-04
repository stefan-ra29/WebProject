package service;

import beans.SportFacility;
import com.google.gson.Gson;
import repository.SportFacilityRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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
    public String getOne(String id) {

        SportFacility sf = sportFacilityRepository.getOne(id);
        return gson.toJson(sf);
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

//        Type facilitiesListType = new TypeToken<ArrayList<SportFacility>>(){}.getType();
//
//        ArrayList<SportFacility> facilitiesArray = gson.fromJson(facilities, facilitiesListType);

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
                Collections.sort(allFacilities, new NameComparator());
                break;
            case "name_decreasing":
                Collections.sort(allFacilities, new NameComparator().reversed());
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
    public String GetSportFacilityTypes(){

        List<SportFacility> allFacilities = sportFacilityRepository.getAll();
        ArrayList<String> types = new ArrayList<String>();

        for(SportFacility sf : allFacilities){
            if(!types.contains(sf.getType().getType()))
                types.add(sf.getType().getType());
        }

        return gson.toJson(types);
    }
    public String FilterSportFacilities(String filter){

        List<SportFacility> allFacilities = sportFacilityRepository.getAll();
        ArrayList<SportFacility> filteredFacilities = new ArrayList<SportFacility>();

        for(SportFacility sf : allFacilities){
            if(sf.getType().getType().equals(filter))
                filteredFacilities.add(sf);
        }
        return gson.toJson(filteredFacilities);
    }

    public String GetCurrentlyOpenedSportFacilities(){

        List<SportFacility> allFacilities = sportFacilityRepository.getAll();
        ArrayList<SportFacility> currentlyOpenedFacilities = new ArrayList<SportFacility>();

        LocalTime now = LocalTime.now();
        for(SportFacility sf : allFacilities){
            if(sf.getStartHour().isBefore(now) && now.isBefore(sf.getClosingHour()))
                currentlyOpenedFacilities.add(sf);
        }
        return gson.toJson(currentlyOpenedFacilities);
    }
}
