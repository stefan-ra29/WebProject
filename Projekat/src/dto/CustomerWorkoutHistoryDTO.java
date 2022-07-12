package dto;

import java.time.LocalDateTime;

public class CustomerWorkoutHistoryDTO {
    private String workoutName;
    private String facilityName;
    private LocalDateTime startTime;
    private String workoutId;
    private String customerId;
    private int supplement;

    public CustomerWorkoutHistoryDTO(String workoutName, String facilityName, LocalDateTime startTime, String workoutId, String customerId, int supplement) {
        this.workoutName = workoutName;
        this.facilityName = facilityName;
        this.startTime = startTime;
        this.workoutId = workoutId;
        this.customerId = customerId;
        this.supplement = supplement;
    }

    public int getSupplement() {
        return supplement;
    }

    public void setSupplement(int supplement) {
        this.supplement = supplement;
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
