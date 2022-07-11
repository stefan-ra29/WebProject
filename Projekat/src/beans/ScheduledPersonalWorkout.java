package beans;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScheduledPersonalWorkout {
    private String workoutId;
    private String customerId;
    private String coachId;
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime scheduledTime;

    public ScheduledPersonalWorkout(){

    }

    public ScheduledPersonalWorkout(String workoutId, String customerId, String coachId, LocalDateTime startTime, LocalDateTime scheduledTime) {
        this.workoutId = workoutId;
        this.customerId = customerId;
        this.coachId = coachId;
        this.startTime = startTime;
        this.scheduledTime = scheduledTime;
        this.id = UUID.randomUUID().toString();
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
