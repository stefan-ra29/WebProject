package beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Coach extends User{
	
	private ArrayList<String> workoutHistoryIds;
	
	public Coach(ArrayList<String> workoutHistoryIds) {
		super();
		this.workoutHistoryIds = workoutHistoryIds;
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

	public ArrayList<String> getWorkoutHistory() {
		return workoutHistoryIds;
	}

	public void setWorkoutHistory(ArrayList<String> workoutHistoryIds) {
		this.workoutHistoryIds = workoutHistoryIds;
	}
	

}
