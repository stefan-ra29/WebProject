package beans;

import java.time.LocalDate;
import java.util.UUID;

public class Membership {
	
	private String id;
	private String type;
	private LocalDate paymentDate;
	private LocalDate expirationDate;
	private int fee;
	private String customerId;
	private boolean isActive;
	private int availableVisits;

	public Membership(){

	}

	public Membership(String type, LocalDate paymentDate, LocalDate expirationDate, int fee,
			String customerId, boolean isActive, int availableVisits) {
		super();
		this.id = UUID.randomUUID().toString();
		this.type = type;
		this.paymentDate = paymentDate;
		this.expirationDate = expirationDate;
		this.fee = fee;
		this.customerId = customerId;
		this.isActive = isActive;
		this.availableVisits = availableVisits;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getAvailableVisits() {
		return availableVisits;
	}
	public void setAvailableVisits(int availableVisits) {
		this.availableVisits = availableVisits;
	}
	
	
}
