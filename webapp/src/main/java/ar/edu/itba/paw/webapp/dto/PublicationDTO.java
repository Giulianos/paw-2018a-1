package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.Tag;
import ar.edu.itba.paw.webapp.config.WebConfig;
import ar.edu.itba.paw.webapp.utils.URLResolver;

import java.util.List;
import java.util.stream.Collectors;

public class PublicationDTO {
    private Long id;
    private Long supervisorId;
    private String supervisorUrl;
    private String description;
    private Double unitPrice;
    private Long quantity;
    private Long availableQuantity;
    private String detailedDescription;
    private String status;
    private List<String> tags;
    private List<String> imagesUrls;

    public PublicationDTO(Publication publication) {
        this.id = publication.getId();
        if(publication.getSupervisor() != null) {
            this.supervisorId = publication.getSupervisor().getId();
            this.supervisorUrl = URLResolver.getFullURL("/users/" + this.supervisorId);
        }
        this.description = publication.getDescription();
        this.unitPrice = publication.getUnitPrice();
        this.quantity = publication.getQuantity();
        this.detailedDescription = publication.getDetailedDescription();
        this.status = publication.getState().toString();
        this.availableQuantity = publication.getAvailableQuantity();
        this.tags = publication.getTags().stream().map(Tag::getTag).collect(Collectors.toList());
        this.imagesUrls = publication
            .getImages()
            .stream()
            .map(i -> URLResolver.getFullURL("/images/" + i.getId()))
            .collect(Collectors.toList());
    }

    public PublicationDTO() {
        // Empty constructor needed by JAX-RS
    }

    public Long getId() {
        return id;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getImagesUrls() {
        return imagesUrls;
    }

    public void setImagesUrls(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
