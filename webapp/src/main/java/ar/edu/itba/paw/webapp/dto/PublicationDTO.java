package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.webapp.config.WebConfig;
import ar.edu.itba.paw.webapp.utils.URLResolver;

public class PublicationDTO {
    private Long id;
    private Long supervisorId;
    private String supervisorUrl;
    private String description;
    private Double unitPrice;
    private Long quantity;
    private String detailedDescription;

    public PublicationDTO(Publication publication) {
        this.id = publication.getId();
        this.supervisorId = publication.getSupervisor().getId();
        this.supervisorUrl = URLResolver.getFullURL("/users/" + this.supervisorId);
        this.description = publication.getDescription();
        this.unitPrice = publication.getUnitPrice();
        this.quantity = publication.getQuantity();
        this.detailedDescription = publication.getDetailedDescription();
    }

    public PublicationDTO() {
        // Empty constructor needed by JAX-RS
    }

    public Long getId() {
        return id;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public String getSupervisorUrl() {
        return supervisorUrl;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public void setSupervisorUrl(String supervisorUrl) {
        this.supervisorUrl = supervisorUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }
}
