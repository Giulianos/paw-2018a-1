package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PublicationForm {
	@Size(min = 6, max = 30, message="{description.size}")
	@Pattern(regexp = "[a-zA-Z][ a-zA-Z0-9]+", message="{publication.description.pattern}")
	private String description;

	@Size(min = 1, max = 10, message="{price.size}")
	@Pattern(regexp = "[1-9][0-9]*.?[0-9]{0,3}", message="{publication.price.pattern}")
	private String price;

	@Size(min = 1, max = 6, message="{quantity.size}")
	@Pattern(regexp = "[1-9][0-9]*", message="{publication.quantity.pattern}")
	private String quantity;

	@Size(min = 1, max = 6, message="{quantity.size}")
	@Pattern(regexp = "[1-9][0-9]*", message="{publication.quantity.pattern}")
	private String ownerQuantity;

//	@Size(min = 10, max = 50)
//	@Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]+")
//	private String image;

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

	public String getOwnerQuantity() {
		return ownerQuantity;
	}

	public void setOwnerQuantity(String ownerQuantity) {
		this.ownerQuantity = ownerQuantity;
	}
	
	public boolean quantityCheck() {
		return Integer.parseInt(getOwnerQuantity()) < Integer.parseInt(getQuantity());
	}
	
//	public String getImage() {
//		return image;
//	}
//	
//	public void setImage(String image) {
//		this.image = image;
//	}
}