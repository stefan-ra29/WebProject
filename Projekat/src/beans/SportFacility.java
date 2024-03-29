package beans;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class SportFacility {

	private String id;
	private String name;
	private FacilityType type;
	private ArrayList<String> workouts;
	private boolean isOpen;
	private Location location;
	private String logo;
	private double averageGrade;
	private LocalTime startHour;
	private LocalTime closingHour;

	public SportFacility(){
		super();
	}
	
	public SportFacility(String name, FacilityType type, ArrayList<String> offer, boolean isOpen,
			Location location, String logo, double averageGrade, LocalTime startHour, LocalTime closingHour) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.type = type;
		this.workouts = offer;
		this.isOpen = isOpen;
		this.location = location;
		this.logo = logo;
		this.averageGrade = averageGrade;
		this.startHour = startHour;
		this.closingHour = closingHour;
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

	public ArrayList<String> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(ArrayList<String> workouts) {
		this.workouts = workouts;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
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

	public String getId() {
		return id;
	}
	public void setId(String id) { this.id = id;}
}
