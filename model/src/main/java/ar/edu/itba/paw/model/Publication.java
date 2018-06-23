package ar.edu.itba.paw.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "publications")
public class Publication {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publications_pubid_seq")
	@SequenceGenerator(sequenceName = "publications_pubid_seq", name = "publications_pubid_seq", allocationSize = 1)
	@Column(name = "publication_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private User supervisor;
	
	@Column(length = 30)
	private String description;
	
	@Column
	private Float price;
	
	@Column
	private Integer quantity;
	
	@Column
	private String image;
	
	@Column
	private Boolean confirmed;
	
	@Column
	private Integer remainingQuantity;

	private List<User> subscribers;
	
	public Publication(final long id, final User supervisor, final String description, final float price, final int quantity, final String image, final boolean confirmed) {
	    this.id = id; 
	    this.supervisor = supervisor; 
	    this.description = description; 
	    this.price = price; 
	    this.quantity = quantity; 
	    this.image = image; 
	    this.confirmed = confirmed; 
	    this.remainingQuantity = quantity; 
	} 
	
	public Publication(final long id, final User supervisor, final String description, final float price, final int quantity, final String image) {
		this(id,supervisor,description,price,quantity,image,false);
	}
	
	public Publication(final long id, final User supervisor, final String description, final float price, final int quantity) {
		this(id,supervisor,description,price,quantity,"");
	}

	public long getId() {
		return this.id;
	}

	public User getSupervisor() {
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
		if(image.equals(""))
			return defaultImage;
		return this.image;
	}

	public boolean getConfirmed() {
		return confirmed;
	}
	
	public int getRemainingQuantity() {
		return remainingQuantity;
	}
	
	public void setRemainingQuantity(final int remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	
	public void setSubscribers(List<User> subscribers) {
		this.subscribers = subscribers;
	}
	
	public List<User> getSubscribers() {
		return subscribers;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publication other = (Publication) obj;
		if (id != other.id)
			return false;
		return true;
	}



	static final String defaultImage = "data:image/png;base64,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\n" + 
			"M2DquSEIHq5b6tTJqJNA5+G6NalSp4HOwnVrUqVOBJ2D69akSp0KOgPXrUmVOhl0etetSZU6HXRy\n" + 
			"161JlTohdGrXrUmVOiV0YtetSZU6KXRa161JlTotdFLXrUmVOjF0StetSZU6NXRC161JlTo5dDrX\n" + 
			"rUmVOj10MtfvTLLUT02yrp8q9QRdV+rqulJX15W6uq7U1XWlrq4rdXVdqavrSl1dV+rqulJX19V1\n" + 
			"dV1dV9fVdXVdXVfX1fWurt/Rp74kon5+Sx3vKWtk+YTEe1qhIqbrSj0V6uq6uq7U1XWlrq4rdXVd\n" + 
			"qavrSl1dV+p9U8emvsc29S2M1Ff72SZsU0f5lqsfOl5wTf0MI/XK22+vuap+ufIl9zEsMuMHnpnf\n" + 
			"G4wRafWx8SXmHONl5SvCPYdmRlgcgaVupaV+A5b6nbTU4R5Dz4Rlvo0zW5AQgPOutSGefUAuNGyq\n" + 
			"o1x1ksg/QCfbhajU90HniaImNT+jLYvY/96XoKkXyf7eRY3xN8Cpy5nVwO8j2VShy8GOsXn4Ucbw\n" + 
			"/oSx2Svi2X6Ms9srYBEzQdowfsM+893lCCn+Zs79B7TMR6OLOecR7gT3eOTx3fwFy5i/X440NDQ0\n" + 
			"NDQ0NDQ0NDQ0hhX/AWh5oxlwQTZtAAAAAElFTkSuQmCC";
	
}