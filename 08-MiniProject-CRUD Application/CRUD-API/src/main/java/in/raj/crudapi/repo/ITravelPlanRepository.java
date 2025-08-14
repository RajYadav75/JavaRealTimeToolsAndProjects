package in.raj.crudapi.repo;

import in.raj.crudapi.entity.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITravelPlanRepository extends JpaRepository<TravelPlan, Integer> {
}
