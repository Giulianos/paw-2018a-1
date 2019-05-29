package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "publications")
public class Publication extends TimestampedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @Access(AccessType.PROPERTY)
    private User supervisor;

    @OneToMany(
        mappedBy = "publication",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @Access(AccessType.PROPERTY)
    private Set<Order> orders = new HashSet<>();

    @Column(length = 50, nullable = false)
    private String description;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Boolean fulfilled = false;

    @Column(length = 1000)
    private String detailedDescription;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
        name = "publication_tags",
        joinColumns = { @JoinColumn(name = "publication_id", referencedColumnName = "id")},
        inverseJoinColumns = { @JoinColumn(name = "tag_id", referencedColumnName = "id")}
    )
    private Set<Tag> tags = new HashSet<>();

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

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Long getAvailableQuantity() {
        return this.quantity - (orders == null ? 0 : orders.stream().map(Order::getQuantity).reduce(0L, Long::sum));
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setPublication(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setPublication(null);
    }

    public void addTag(final Tag tag) {
        tags.add(tag);
        tag.addPublication(this);
    }

    public void removeTag(final Tag tag) {
        tags.remove(tag);
        tag.removePublication(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}