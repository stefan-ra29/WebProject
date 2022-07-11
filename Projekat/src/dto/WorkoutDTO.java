package dto;

public class WorkoutDTO {
    private String name;
    private String facilityName;
    private String type;

    public WorkoutDTO(String name, String facilityName, String type) {
        this.name = name;
        this.facilityName = facilityName;
        this.type = type;
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
