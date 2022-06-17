package beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Coach extends User{
	
	private ArrayList<WorkoutHistory> workoutHistory;
	
	public Coach(ArrayList<WorkoutHistory> workoutHistory) {
		super();
		this.workoutHistory = workoutHistory;
	}

	public Coach() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Coach(String firstName, String lastName, String email, String username, String password, String gender,
			LocalDate dob, Role role) {
		super(firstName, lastName, email, username, password, gender, dob, role);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<WorkoutHistory> getWorkoutHistory() {
		return workoutHistory;
	}

	public void setWorkoutHistory(ArrayList<WorkoutHistory> workoutHistory) {
		this.workoutHistory = workoutHistory;
	}
	

}
