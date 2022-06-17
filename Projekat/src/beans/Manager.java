package beans;

import java.time.LocalDate;

public class Manager extends User {
	
	private SportFacility sportFacility;

	public Manager(SportFacility sportFacility) {
		super();
		this.sportFacility = sportFacility;
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

	public SportFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportFacility sportFacility) {
		this.sportFacility = sportFacility;
	}
	
	
}
