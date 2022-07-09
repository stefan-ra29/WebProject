package beans;

public class CustomerType {
	
	private String typeName;
	private int discount;
	private int neededPoints;
	
	public CustomerType(String typeName, int discount, int neededPoints) {
		super();
		this.typeName = typeName;
		this.discount = discount;
		this.neededPoints = neededPoints;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
