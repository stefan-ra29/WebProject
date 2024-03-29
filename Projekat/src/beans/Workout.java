package beans;

import java.util.UUID;

public class Workout {

	private String name;
	private String id;
	private WorkoutType workoutType;
	private String sportFacilityID;
	private int duration;
	private String coachID;
	private String description;
	private String picture;
	private int supplement;

	public Workout(){

	}
	public Workout(String name, WorkoutType workoutType, String sportFacilityID, int duration, String coachID,
			String description, String picture, int supplement) {
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.workoutType = workoutType;
		this.sportFacilityID = sportFacilityID;
		this.duration = duration;
		this.coachID = coachID;
		this.description = description;
		this.picture = picture;
		this.supplement = supplement;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WorkoutType getWorkoutType() {
		return workoutType;
	}
	public void setWorkoutType(WorkoutType workoutType) {
		this.workoutType = workoutType;
	}
	public String getSportFacilityID() {
		return sportFacilityID;
	}
	public void setSportFacilityID(String sportFacilityID) {
		this.sportFacilityID = sportFacilityID;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getCoachID() {
		return coachID;
	}
	public void setCoachID(String coachID) {
		this.coachID = coachID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getId() {
		return id;
	}
	public void setId() { this.id = UUID.randomUUID().toString();}

	public int getSupplement() {
		return supplement;
	}

	public void setSupplement(int supplement) {
		this.supplement = supplement;
	}
}
