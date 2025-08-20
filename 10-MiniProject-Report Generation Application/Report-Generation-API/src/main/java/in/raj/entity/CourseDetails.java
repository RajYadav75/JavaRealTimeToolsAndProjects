package in.raj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COURSE_DETAILS")
public class CourseDetails {
    @Id
    @GeneratedValue
    private Integer courseId;

    private String courseName;

    private String location;

    private String courseCategory;

    private String facultyName;

    private Double fee;

    private String adminName;

    private Long adminContact;

    private String trainingMode;

    private LocalDateTime startDate;

    private String courseStatus;
    @CreationTimestamp
    @Column(updatable = false,insertable = true)
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @Column(updatable = true,insertable = false)
    private LocalDateTime updationDate;

    private String createdBy;

    private String updatedBy;

}
