package beans;

public class Comment {
	private Customer customer;
	private SportFacility facility;
	private String text;
	private int grade;
	
	public Comment(Customer customer, SportFacility facility, String text, int grade) {
		super();
		this.customer = customer;
		this.facility = facility;
		this.text = text;
		this.grade = grade;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public SportFacility getFacility() {
		return facility;
	}

	public void setFacility(SportFacility facility) {
		this.facility = facility;
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

}
