package in.raj.crudapi.ms;

import in.raj.crudapi.entity.TravelPlan;
import in.raj.crudapi.service.ITravelPlanMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel-api") //Global Path
public class TravelPlanOperationController {
    @Autowired
    private ITravelPlanMgmtService travelService;

    @GetMapping("/categories")
    public ResponseEntity<?> showTravelPlanCategories(){
        try {
            Map<Integer, String> travelPlansCategories = travelService.getTravelPlansCategories();
            return new ResponseEntity<Map<Integer, String>>(travelPlansCategories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerTravelPlan(@RequestBody TravelPlan travelPlan){
        try {
            String RegisteringTravelPlan = travelService.registerTravelPlan(travelPlan);
            return new ResponseEntity<>(RegisteringTravelPlan, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllTravelPlans")
    public ResponseEntity<?> getAllTravelPlans(){
        try {
            List<TravelPlan> travelPlans = travelService.showAllTravelPlan();
            return new ResponseEntity<>(travelPlans, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<?> getTravelPlanById(@PathVariable int id){
        try {
            TravelPlan travelPlan = travelService.showTravelPlanById(id);
            return new ResponseEntity<>(travelPlan, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateTravelPlan(@RequestBody TravelPlan travelPlan) {
        try {
            String updateTravelPlan = travelService.updateTravelPlan(travelPlan);
            return new ResponseEntity<>(updateTravelPlan, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Integer id) {
        try {
            String deleteTravelPlan = travelService.deleteTravelPlan(id);
            return new ResponseEntity<>(deleteTravelPlan, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/status-change/{planId}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer planId, @PathVariable String status) {
        try {
            String updatedStatus = travelService.changeTravelPlanStatus(planId, status);
            return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
