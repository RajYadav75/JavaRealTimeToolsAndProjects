package in.raj.repo;

import in.raj.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseDetailsRepository extends JpaRepository<CourseDetails, Integer> {
}
