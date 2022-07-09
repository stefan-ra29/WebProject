package service;

import beans.SportFacility;
import com.google.gson.Gson;
import comparators.AverageGradeComparator;
import comparators.LocationComparator;
import comparators.NameComparator;
import repository.SportFacilityRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SportFacilityService {

    SportFacilityRepository sportFacilityRepository = new SportFacilityRepository();
    Gson gson = new Gson();
    public String getAll() {

        List<SportFacility> allFacilities = sportFacilityRepository.getAll();
        return gson.toJson(allFacilities);
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
    public String searchSportFacilities(String nameSearch, String typeSearch, String locationSearch,
                                            String gradeCriteria, ArrayList<SportFacility> facilityList){

        ArrayList<SportFacility> filteredList = new ArrayList<SportFacility>();
        ArrayList<SportFacility> listForRemoving = new ArrayList<SportFacility>();

        if (!gradeCriteria.equals("")) {
            for (SportFacility facility : facilityList) {
                switch (gradeCriteria) {
                    case "1-2":
                        if (facility.getAverageGrade() > 1 && facility.getAverageGrade() <= 2)
                            filteredList.add(facility);
                        break;
                    case "2-3":
                        if (facility.getAverageGrade() > 2 && facility.getAverageGrade() <= 3)
                            filteredList.add(facility);
                        break;
                    case "3-4":
                        if (facility.getAverageGrade() > 3 && facility.getAverageGrade() <= 4)
                            filteredList.add(facility);
                        break;
                    case "4-5":
                        if (facility.getAverageGrade() > 4 && facility.getAverageGrade() <= 5)
                            filteredList.add(facility);
                        break;
                }
            }
            //ako nikog nije ubacio za grade, znaci ne postuje niko taj kriterijum, vrati praznu listu
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }

        //ako ne postoji grade i postoji name
        if (gradeCriteria.equals("") && !nameSearch.equals("")) {
            for (SportFacility facility : facilityList) {
                if (facility.getName().toLowerCase().contains(nameSearch.toLowerCase().trim()))
                    filteredList.add(facility);
            }
            //ako nikog nije ubacio za name, znaci ne postuje niko taj kriterijum, vrati praznu listu
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }
        //ako grade postoji i postoji i name
        else if(!gradeCriteria.equals("") && !nameSearch.equals("")){
            for (SportFacility facility : filteredList) {
                if (!facility.getName().toLowerCase().contains(nameSearch.toLowerCase().trim()))
                    listForRemoving.add(facility);
            }
            filteredList.removeAll(listForRemoving);
            listForRemoving.clear();
            //ako je lista sada prazna, znaci da name nije ispostovan iako je grade bio i vrati prazno
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }

        //ako ne postoji grade i name, a postoji type
        if (gradeCriteria.equals("") && nameSearch.equals("") && !typeSearch.equals("")) {
            for (SportFacility facility : facilityList) {
                if (facility.getType().getType().toLowerCase().contains(typeSearch.toLowerCase().trim()))
                    filteredList.add(facility);
            }
            //ako nikog nije ubacio za type, znaci ne postuje niko taj kriterijum, vrati praznu listu
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }
        //ako grade ILI name postoji i postoji i type
        else if((!gradeCriteria.equals("") || !nameSearch.equals("")) && !typeSearch.equals("")){
            for (SportFacility facility : filteredList) {
                if (!facility.getType().getType().toLowerCase().contains(typeSearch.toLowerCase().trim()))
                    listForRemoving.add(facility);
            }
            filteredList.removeAll(listForRemoving);
            listForRemoving.clear();
            //ako je lista sada prazna, znaci da type nije ispostovan iako je grade ili name bio i vrati prazno
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }

        //ako ne postoji grade i name i type, a postoji location
        if (gradeCriteria.equals("") && nameSearch.equals("") && typeSearch.equals("") && !locationSearch.equals("")) {
            for (SportFacility facility : facilityList) {
                if (facility.getLocation().getCity().toLowerCase().contains(locationSearch.toLowerCase().trim()))
                    filteredList.add(facility);
            }
            //ako nikog nije ubacio za locatoin, znaci ne postuje niko taj kriterijum, vrati praznu listu
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }
        //ako grade ILI name ILI type postoji i postoji i location
        else if((!gradeCriteria.equals("") || !nameSearch.equals("") || !typeSearch.equals("")) && !locationSearch.equals("")) {
            for (SportFacility facility : filteredList) {
                if (!facility.getLocation().getCity().toLowerCase().contains(locationSearch.toLowerCase().trim()))
                    listForRemoving.add(facility);
            }
            filteredList.removeAll(listForRemoving);
            listForRemoving.clear();
            //svakako prodje i posalje praznu listu
        }
        return gson.toJson(filteredList);
    }

    public String sortSportFacilities(String sortBy, ArrayList<SportFacility> facilityList ){

        switch (sortBy) {
            case "name_increasing":
                Collections.sort(facilityList, new NameComparator());
                break;
            case "name_decreasing":
                Collections.sort(facilityList, new NameComparator().reversed());
                break;
            case "location_increasing":
                Collections.sort(facilityList, new LocationComparator());
                break;
            case "location_decreasing":
                Collections.sort(facilityList, new LocationComparator().reversed());
                break;
            case "average_grade_increasing":
                Collections.sort(facilityList, new AverageGradeComparator());
                break;
            case "average_grade_decreasing":
                Collections.sort(facilityList, new AverageGradeComparator().reversed());
                break;
        }

        return gson.toJson(facilityList);
    }
    public String getSportFacilityTypes(){

        List<SportFacility> allFacilities = sportFacilityRepository.getAll();
        ArrayList<String> types = new ArrayList<String>();

        for(SportFacility sf : allFacilities){
            if(!types.contains(sf.getType().getType()))
                types.add(sf.getType().getType());
        }

        return gson.toJson(types);
    }
    public String filterSportFacilities(String filter, ArrayList<SportFacility> facilityList){

        ArrayList<SportFacility> filteredFacilities = new ArrayList<SportFacility>();

        for(SportFacility sf : facilityList){
            if(sf.getType().getType().equals(filter))
                filteredFacilities.add(sf);
        }
        return gson.toJson(filteredFacilities);
    }

    public String getCurrentlyOpenedSportFacilities(ArrayList<SportFacility> facilityList){

        ArrayList<SportFacility> currentlyOpenedFacilities = new ArrayList<SportFacility>();

        LocalTime now = LocalTime.now();
        for(SportFacility sf : facilityList){
            if(sf.getStartHour().isBefore(now) && now.isBefore(sf.getClosingHour()))
                currentlyOpenedFacilities.add(sf);
        }
        return gson.toJson(currentlyOpenedFacilities);
    }

    public boolean isFacilityCurrentlyWorking(String facilityId){
        LocalTime now = LocalTime.now();
        for(SportFacility sf : sportFacilityRepository.getAll()) {
            if(sf.getId().equals(facilityId)){
                if(sf.getStartHour().isBefore(now) && now.isBefore(sf.getClosingHour()))
                    return true;
                else
                    return false;
            }
        }

        return false;
    }
}
