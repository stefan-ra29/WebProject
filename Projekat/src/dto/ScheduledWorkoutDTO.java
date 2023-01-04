package dto;

import java.time.LocalDateTime;

public class ScheduledWorkoutDTO {

    private String name;
    private String facilityName;
    private String type;
    private String customer;
    private LocalDateTime scheduledTime;
    private String id;

    public ScheduledWorkoutDTO(String name, String facilityName, String customer, LocalDateTime scheduledTime, String id) {
        this.name = name;
        this.facilityName = facilityName;
        this.customer = customer;
        this.scheduledTime = scheduledTime;
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
