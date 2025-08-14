package in.raj.crudapi.repo;

import in.raj.crudapi.entity.PlanCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanCategoryRepository extends JpaRepository<PlanCategory, Integer> {
}
