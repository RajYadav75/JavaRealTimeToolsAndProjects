package in.raj.repo;

import in.raj.entity.CourseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ICourseDetailsRepository extends JpaRepository<CourseDetails, Integer> {
    @Query("select  distinct(courseCategory)  from  CourseDetails")
    public  Set<String>    getUniqueCourseCategories();

    @Query("select  distinct(facultyName)  from  CourseDetails")
    public  Set<String>    getUniqueFacultyNames();

    @Query("select  distinct(trainingMode)  from  CourseDetails")
    public default Set<String> getUniqueTraniningModes() {
        return null;
    }
}
