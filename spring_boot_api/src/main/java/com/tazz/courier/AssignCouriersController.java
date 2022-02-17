package com.tazz.courier;

import java.util.Map;

import com.tazz.algorithm.AdvancedBipartiteGraph;
//import com.tazz.algorithm.ComlexSolution;
//import com.tazz.algorithm.BipartiteGraph;
import com.tazz.parameters.Data;
import com.tazz.parameters.Courier;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://courier-assignment.netlify.app/", allowedHeaders = "*")
@RestController
public class AssignCouriersController {

    @RequestMapping(value = "/assignCouriers", method = RequestMethod.POST)
    public String assignCouriers(@RequestBody Data data) {
        if (data == null) {
            return "error";
        }
        // BipartiteGraph bipartite = new BipartiteGraph(data);
        AdvancedBipartiteGraph bipartite = new AdvancedBipartiteGraph(data);
        // ComlexSolution comlexSolution = new ComlexSolution();
        Map<Long, Courier> assignments = bipartite.assignCouriers();
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Long, Courier> assignment : assignments.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("courierId", assignment.getValue().getId());
            jsonObject.put("orderId", assignment.getKey());
            jsonObject.put("deliveryTimeMinutes", assignment.getValue().getDeliveryTimeMinutes());
            jsonArray.put(jsonObject);
        }
        JSONObject jsonAssignments = new JSONObject();
        jsonAssignments.put("assignments", jsonArray);
        return jsonAssignments.toString();
    }
}