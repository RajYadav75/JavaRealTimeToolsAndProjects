package in.raj.crudapi.service.implementationClass;

import in.raj.crudapi.entity.PlanCategory;
import in.raj.crudapi.entity.TravelPlan;
import in.raj.crudapi.repo.IPlanCategoryRepository;
import in.raj.crudapi.repo.ITravelPlanRepository;
import in.raj.crudapi.service.ITravelPlanMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TravelPlanMgmtServiceImpl implements ITravelPlanMgmtService {
    @Autowired
    private IPlanCategoryRepository planCategoryRepo;
    @Autowired
    private ITravelPlanRepository travelPlanRepo;
    @Override
    public String registerTravelPlan(TravelPlan travelPlan) {
        //TODO:- Save The Object

        TravelPlan saveTravelPlan = travelPlanRepo.save(travelPlan);
        return saveTravelPlan.getId()!=null?"TravelPlan is registered successfully :: "+saveTravelPlan.getId():"TravelPlan already Not register";
    }

    @Override
    public Map<Integer, String> getTravelPlansCategories() {
        //TODO:- Get All Travel Plan Categories

        List<PlanCategory> planCategoryList = planCategoryRepo.findAll();
        Map<Integer,String> planCategoryMap = new HashMap<>();
        planCategoryList.forEach(planCategory -> {
            planCategoryMap.put(planCategory.getCategoryId(), planCategory.getCategoryName());
        });
        return planCategoryMap;
    }

    @Override
    public List<TravelPlan> showAllTravelPlan() {
        return travelPlanRepo.findAll();
    }

    @Override
    public TravelPlan showTravelPlanById(Integer id) {
        //TODO:- First Way
        //return travelPlanRepo.findById(id).orElseThrow(()->new IllegalArgumentException("TravelPlan Not Found"));
        //TODO:- Second Way
        Optional<TravelPlan> travelPlanId = travelPlanRepo.findById(id);
        if (travelPlanId.isPresent()){
            return travelPlanId.get();
        }else {
            throw new IllegalArgumentException("TravelPlan Not Found");
        }
    }

    @Override
    public String updateTravelPlan(TravelPlan travelPlan) {
        Optional<TravelPlan> getTravelPlan = travelPlanRepo.findById(travelPlan.getId());
        if (getTravelPlan.isPresent()){
            //save the object
            travelPlanRepo.save(travelPlan);
            return "TravelPlan Updated Successfully"+travelPlan.getId();
        }
        else{
            throw new IllegalArgumentException("TravelPlan Not Found");
        }
    }

    @Override
    public String deleteTravelPlan(Integer id) {
        Optional<TravelPlan> getTravelPlanId = travelPlanRepo.findById(id);
        if (getTravelPlanId.isPresent()){
            travelPlanRepo.deleteById(id);
            return "TravelPlan Deleted Successfully"+id;
        }else{
            throw new IllegalArgumentException("TravelPlan Not Found :: "+id);
        }
    }

    @Override
    public String changeTravelPlanStatus(Integer planId, String status) {
        Optional<TravelPlan> getTravelPlanById = travelPlanRepo.findById(planId);
        if (getTravelPlanById.isPresent()){
            TravelPlan travelPlan = getTravelPlanById.get();
            travelPlan.setActivateSW(status);
            travelPlanRepo.save(travelPlan);
            return "TravelPlan Changed Successfully"+planId;
        }else{
            throw new IllegalArgumentException("TravelPlan Not Found for updation :: "+planId);
        }
    }

}
