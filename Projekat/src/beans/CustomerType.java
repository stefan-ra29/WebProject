package beans;

public class CustomerType {
	
	private CustomerTypes type;
	private int discount;
	private int neededPoints;
	
	public CustomerType(CustomerTypes type, int discount, int neededPoints) {
		super();
		this.type = type;
		this.discount = discount;
		this.neededPoints = neededPoints;
	}

	public CustomerTypes getType() {
		return type;
	}

	public void setType(CustomerTypes type) {
		this.type = type;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getNeededPoints() {
		return neededPoints;
	}

	public void setNeededPoints(int neededPoints) {
		this.neededPoints = neededPoints;
	}
	
	
}
