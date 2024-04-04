package deme.ahmadou.securedoc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import deme.ahmadou.securedoc.domains.RequestContext;
import deme.ahmadou.securedoc.exceptions.ApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;


import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();

    @NotNull
    private Long createdBy;

    @NotNull
    private Long updatedBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforePersist(){
        var userId= RequestContext.getUserId();
        if(userId == null){
            throw new ApiException("Cannot persist entity without user ID in request context for this thread");
        }
        setCreatedBy(userId);
        setUpdatedBy(userId);
        setCreatedAt(LocalDateTime.now());
        setUpdatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void beforeUpdate(){
        var userId= RequestContext.getUserId();
        if(userId == null){
            throw new ApiException("Cannot persist entity without user ID in request context for this thread");
        }
        setUpdatedBy(userId);
        setUpdatedAt(LocalDateTime.now());
    }
}
