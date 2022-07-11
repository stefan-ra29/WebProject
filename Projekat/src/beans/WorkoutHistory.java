package beans;

import java.time.LocalDateTime;
import java.util.UUID;

public class WorkoutHistory {
	
	private LocalDateTime startTime;
	private String workoutId;
	private String customerId;
	private String coachId;
	private String id;

	public WorkoutHistory(){
	}

	public WorkoutHistory(ScheduledPersonalWorkout scheduledPersonalWorkout){
		this.workoutId = scheduledPersonalWorkout.getWorkoutId();
		this.customerId = scheduledPersonalWorkout.getCustomerId();
		this.coachId = scheduledPersonalWorkout.getCoachId();
		this.startTime = scheduledPersonalWorkout.getScheduledTime();
		this.id = UUID.randomUUID().toString();
	}

	public WorkoutHistory(LocalDateTime startTime, String workoutId, String customerId, String coachId) {
		this.startTime = startTime;
		this.workoutId = workoutId;
		this.customerId = customerId;
		this.coachId = coachId;
		this.id = UUID.randomUUID().toString();
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(String workoutId) {
		this.workoutId = workoutId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCoachId() {
		return coachId;
	}

	public void setCoachId(String coachId) {
		this.coachId = coachId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
