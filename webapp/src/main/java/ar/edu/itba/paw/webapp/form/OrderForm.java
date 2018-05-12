package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderForm {

	@Size(min = 1, max = 6, message="{quantity.size}")
	@Pattern(regexp = "[1-9][0-9]*", message="{publication.quantity.pattern}")
	private String quantity;
	
	@Pattern(regexp = "[1-9][0-9]*")
	private String publication_id;
	
	
	public String getPublicationId() {
		return publication_id;
	}
	
	public void setPublicationId(String publication_id) {
		this.publication_id = publication_id;
	}
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
