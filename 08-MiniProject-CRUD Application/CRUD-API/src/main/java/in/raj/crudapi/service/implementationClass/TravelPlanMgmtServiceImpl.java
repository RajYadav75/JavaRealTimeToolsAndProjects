package in.raj.crudapi.service.implementationClass;

import in.raj.crudapi.config.AppConfig;
import in.raj.crudapi.constants.TravelPlanConstrant;
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
    private Map<String,String> messages;

    @Autowired
    public TravelPlanMgmtServiceImpl(AppConfig appConfig) {
        this.messages = appConfig.getMessages();
    }

    @Override
    public String registerTravelPlan(TravelPlan travelPlan) {
        //TODO:- Save The Object

        TravelPlan saveTravelPlan = travelPlanRepo.save(travelPlan);
        //TODO:- Not Best Practice
        //return saveTravelPlan.getId()!=null?"TravelPlan is registered successfully :: "+saveTravelPlan.getId():"TravelPlan already Not register";
        //TODO:- For Best Practice
        return saveTravelPlan.getId()!=null?messages.get(TravelPlanConstrant.SAVE_SUCCESS)+saveTravelPlan.getId():messages.get(TravelPlanConstrant.SAVE_FAILURE);
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
        //TODO:- Not Best Practice
        //return travelPlanRepo.findById(id).orElseThrow(()->new IllegalArgumentException("TravelPlan Not Found"));
        //TODO:- For Best Practice
//        return travelPlanRepo.findById(id).orElseThrow(()->new IllegalArgumentException(messages.get("find-by-id-failure")));
        //TODO:- Second Way
        Optional<TravelPlan> travelPlanId = travelPlanRepo.findById(id);
        if (travelPlanId.isPresent()){
            return travelPlanId.get();
        }else {
            //throw new IllegalArgumentException("TravelPlan Not Found");
            throw new IllegalArgumentException(messages.get(TravelPlanConstrant.FIND_BY_ID_FAILURE));
        }
    }

    @Override
    public String updateTravelPlan(TravelPlan travelPlan) {
        Optional<TravelPlan> getTravelPlan = travelPlanRepo.findById(travelPlan.getId());
        if (getTravelPlan.isPresent()){
            //save the object
            travelPlanRepo.save(travelPlan);
//            return "TravelPlan Updated Successfully"+travelPlan.getId();
            return messages.get(TravelPlanConstrant.UPDATE_SUCCESS)+travelPlan.getId();
        }
        else{
//            throw new IllegalArgumentException("TravelPlan Not Found");
            return messages.get(TravelPlanConstrant.UPDATE_FAILURE);
        }
    }

    @Override
    public String deleteTravelPlan(Integer id) {
        Optional<TravelPlan> getTravelPlanId = travelPlanRepo.findById(id);
        if (getTravelPlanId.isPresent()){
            travelPlanRepo.deleteById(id);
//            return "TravelPlan Deleted Successfully"+id;
            return messages.get(TravelPlanConstrant.DELETE_SUCCESS)+id;
        }else{
            //throw new IllegalArgumentException("TravelPlan Not Found :: "+id);
            return messages.get(TravelPlanConstrant.DELETE_FAILURE)+id;
        }
    }

    @Override
    public String changeTravelPlanStatus(Integer planId, String status) {
        Optional<TravelPlan> getTravelPlanById = travelPlanRepo.findById(planId);
        if (getTravelPlanById.isPresent()){
            TravelPlan travelPlan = getTravelPlanById.get();
            travelPlan.setActivateSW(status);
            travelPlanRepo.save(travelPlan);
//            return "TravelPlan Changed Successfully"+planId;
            return messages.get(TravelPlanConstrant.STATUS_CHANGE_SUCCESS)+planId;
        }else{
//            throw new IllegalArgumentException("TravelPlan Not Found for updation :: "+planId);
           return messages.get(TravelPlanConstrant.STATUS_CHANGE_FAILURE)+planId;
        }
    }

}
