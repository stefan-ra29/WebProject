package dto;

import beans.FacilityType;
import beans.Location;
import beans.WorkoutType;

import java.time.LocalTime;
import java.util.ArrayList;

public class SportFacilityDTO {
    private String id;
    private String name;
    private FacilityType type;
    private ArrayList<WorkoutType> offer;
    private boolean isOpen;
    private Location location;
    private String logo;
    private double averageGrade;
    private LocalTime startHour;
    private LocalTime closingHour;

    public SportFacilityDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityType getType() {
        return type;
    }

    public void setType(FacilityType type) {
        this.type = type;
    }

    public ArrayList<WorkoutType> getOffer() {
        return offer;
    }

    public void setOffer(ArrayList<WorkoutType> offer) {
        this.offer = offer;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(LocalTime closingHour) {
        this.closingHour = closingHour;
    }
}
