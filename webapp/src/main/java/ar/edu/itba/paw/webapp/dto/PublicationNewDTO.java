package ar.edu.itba.paw.webapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PublicationNewDTO {
    @Size(min = 4, max = 50)
    private String description;

    @NotNull
    private Double unitPrice;

    @NotNull
    private Long quantity;

    @Size(max = 1000)
    private String detailedDescription;

    public PublicationNewDTO() {
        // Empty constructor needed by JAX-RS
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }
}
