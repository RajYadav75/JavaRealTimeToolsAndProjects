package in.raj.crudapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "PLAN_CATEGORY")
public class PlanCategory {
    @Id
    @Column(name = "CATEGORY_ID")
    @SequenceGenerator(name = "gen1",sequenceName = "seq1", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer categoryId;

    @Column(name = "CATEGORY_NAME",length = 30)
    private String categoryName;

    @Column(name = "ACTIVE_STATUS",length = 15)
    private String activeSW;

    @Column(name = "CREATED_DATE",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE",insertable = false,updatable = true)
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column(name = "CREATED_BY",length = 20)
    private String createdBy;

    @Column(name = "UPDATED_By" ,length = 20)
    private String updatedBy;
}
