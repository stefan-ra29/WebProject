package beans;

import java.time.LocalDate;

public class Membership {
	
	private String id;
	private MembershipType type;
	private LocalDate paymentDate;
	private LocalDate expirationDate;
	private int fee;
	private Customer customer;
	private boolean isActive;
	private int dailyLimit;
	public Membership(String id, MembershipType type, LocalDate paymentDate, LocalDate expirationDate, int fee,
			Customer customer, boolean isActive, int dailyLimit) {
		super();
		this.id = id;
		this.type = type;
		this.paymentDate = paymentDate;
		this.expirationDate = expirationDate;
		this.fee = fee;
		this.customer = customer;
		this.isActive = isActive;
		this.dailyLimit = dailyLimit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public MembershipType getType() {
		return type;
	}
	public void setType(MembershipType type) {
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getDailyLimit() {
		return dailyLimit;
	}
	public void setDailyLimit(int dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	
	
}
