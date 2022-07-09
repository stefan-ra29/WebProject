package dto;

import java.time.LocalDateTime;

public class CustomerWorkoutHistoryDTO {
    private String workoutName;
    private String facilityName;
    private LocalDateTime startTime;

    public CustomerWorkoutHistoryDTO(String workoutName, String facilityName, LocalDateTime startTime) {
        this.workoutName = workoutName;
        this.facilityName = facilityName;
        this.startTime = startTime;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
