package beans;

import java.time.LocalDateTime;

public class WorkoutHistory {
	
	private LocalDateTime startTime;
	private Workout workout;
	private Customer customer;
	private Coach coach;
	
	public WorkoutHistory(LocalDateTime startTime, Workout workout, Customer customer, Coach coach) {
		super();
		this.startTime = startTime;
		this.workout = workout;
		this.customer = customer;
		this.coach = coach;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	
	
}
