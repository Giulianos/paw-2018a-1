package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PublicationForm {
	@Size(min = 6, max = 30)
	@Pattern(regexp = "[a-zA-Z][ a-zA-Z0-9]+")
	private String description;

	@Size(min = 1, max = 10)
	@Pattern(regexp = "[1-9][0-9]*.?[0-9]{0,3}")
	private String price;

	@Size(min = 1, max = 6)
	@Pattern(regexp = "[1-9][0-9]*")
	private String quantity;

	@Size(min = 10, max = 50)
	@Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]+")
	private String image;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
}