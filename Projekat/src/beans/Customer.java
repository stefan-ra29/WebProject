package beans;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends User {
	
	private Membership membership;
	private ArrayList<String> visitedFacilitiesIds;
	private double points;
	private String customerTypeName;
	
	public Customer(Membership membership, ArrayList<String> visitedFacilities, double points,
			String customerTypeName) {
		super();
		this.membership = membership;
		this.visitedFacilitiesIds = visitedFacilities;
		this.points = points;
		this.customerTypeName = customerTypeName;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(User user){
		super(user);
	}

	public Customer(String firstName, String lastName, String email, String username, String password, String gender,
			LocalDate dob, Role role) {
		super(firstName, lastName, email, username, password, gender, dob, role);
		// TODO Auto-generated constructor stub
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public ArrayList<String> getVisitedFacilitiesIds() {
		return visitedFacilitiesIds;
	}

	public void setVisitedFacilities(ArrayList<String> visitedFacilitiesIds) {
		this.visitedFacilitiesIds = visitedFacilitiesIds;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}
}
