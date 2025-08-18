package in.raj.crudapi.service;

import in.raj.crudapi.entity.TravelPlan;

import java.util.List;
import java.util.Map;

public interface ITravelPlanMgmtService {

    //TODO-: For Save Operation
    String registerTravelPlan(TravelPlan travelPlan);

    //TODO-: For Select Operation
    Map<Integer,String> getTravelPlansCategories();

    //TODO-: For Select Operation
    List<TravelPlan> showAllTravelPlan();

    //TODO-: For Edit Operation Form Launch(To Show the existing record for editing)
    TravelPlan showTravelPlanById(Integer id);

    //TODO-:
}
