package beans;

import java.time.LocalDate;

public class Manager extends User {
	
	private String sportFacilityId;

	public Manager(String sportFacilityId) {
		super();
		this.sportFacilityId = sportFacilityId;
	}

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager(String firstName, String lastName, String email, String username, String password, String gender,
				   LocalDate dob, Role role) {
		super(firstName, lastName, email, username, password, gender, dob, role);
		// TODO Auto-generated constructor stub
	}

	public String getSportFacilityId() {
		return sportFacilityId;
	}

	public void setSportFacilityId(String sportFacilityId) {
		this.sportFacilityId = sportFacilityId;
	}
	
	
}
