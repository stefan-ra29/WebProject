package beans;

import java.util.UUID;

public class Comment {
	private String customerID;
	private String facilityID;
	private String text;
	private int grade;
	private String id;
	private boolean isApproved;
	private boolean isFilled;
	
	public Comment(String customer, String facility, String text, int grade, boolean isApproved, boolean isFilled) {
		super();
		this.id = UUID.randomUUID().toString();
		this.customerID = customer;
		this.facilityID = facility;
		this.text = text;
		this.grade = grade;
		this.isApproved = isApproved;
		this.isFilled = isFilled;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(boolean approved) {
		isApproved = approved;
	}

	public boolean getIsFilled() {
		return isFilled;
	}

	public void setIsFilled(boolean filled) {
		isFilled = filled;
	}

	public String getId() {
		return id;
	}
}
