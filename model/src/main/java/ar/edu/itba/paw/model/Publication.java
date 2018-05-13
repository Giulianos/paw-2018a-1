package ar.edu.itba.paw.model;

public class Publication {

	private final long id;
	private final String supervisor;
	private final String description;
	private final float price;
	private final int quantity;
	private final String image;
	private int remainingQuantity;
	
	public Publication(final long id, final String supervisor, final String description, final float price, final int quantity, final String image) {
		this.id = id;
		this.supervisor = supervisor;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
		this.remainingQuantity = quantity;
	}
	
	public Publication(final long id, final String supervisor, final String description, final float price, final int quantity) {
		this(id,supervisor,description,price,quantity,"");
	}

	public long getId() {
		return this.id;
	}

	public String getSupervisor() {
		return this.supervisor;
	}

	public String getDescription() {
		return this.description;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public int getRemainingQuantity() {
		return remainingQuantity;
	}
	
	public void setRemainingQuantity(final int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
}