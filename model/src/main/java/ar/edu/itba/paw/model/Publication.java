package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "publications")
public class Publication extends TimestampedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @Access(AccessType.PROPERTY)
    private User supervisor;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    @Access(AccessType.PROPERTY)
    private Set<Order> orders;

    @Column(length = 50, nullable = false)
    private String description;

    @Column
    private Double unitPrice;

    @Column
    private Long quantity;

    @Column(length = 1000)
    private String detailedDescription;

    public Publication() {
        // Hibernate needs this
    }

    public Publication(User supervisor, String description, Double unitPrice, Long quantity, String detailedDescription) {
        this.supervisor = supervisor;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.detailedDescription = detailedDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Long getAvailableQuantity() {
        return this.quantity - (orders == null ? 0 : orders.stream().map(Order::getQuantity).reduce(0L, Long::sum));
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", supervisorId=" + supervisor.getId() +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", detailedDescription='" + detailedDescription + '\'' +
                '}';
    }
}