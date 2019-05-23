package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class TimestampedEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

   @PrePersist
   protected void onCreate() {
       this.createdAt = this.updatedAt = new Date();
   }

   @PreUpdate
   protected void onUpdate() {
       this.updatedAt = new Date();
   }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
