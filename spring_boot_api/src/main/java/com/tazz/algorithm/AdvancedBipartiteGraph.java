// This algorithm represents a changed version that focuses on selecting the solution with minimum total distance
//travelled by couriers

package com.tazz.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tazz.parameters.Courier;
import com.tazz.parameters.Data;

import com.tazz.parameters.Action;
import com.tazz.parameters.CourierOrder;

public class AdvancedBipartiteGraph {
    Data data;

    public AdvancedBipartiteGraph(Data data) {
        this.data = data;
    }

    public Map<Long, Courier> assignCouriers() {
        List<Courier> couriers = data.getCouriers();
        List<CourierOrder> orders = data.getOrders();

        // Saves the assigned courier for an order with a specific id
        Map<Long, Courier> assign = new HashMap<Long, Courier>();

        for (Courier courier : couriers) {
            // Saves the already used orders
            Map<Long, Boolean> seen = new HashMap<Long, Boolean>();
            checkOptions(orders, courier, seen, assign, Long.valueOf(-1), -1);
        }

        Map<Long, CourierOrder> ordersMap = new HashMap<Long, CourierOrder>();
        for (CourierOrder courierOrder : orders) {
            ordersMap.put(courierOrder.getId(), courierOrder);
        }
        assign = improveSolution(assign, couriers, ordersMap);
        return assign;
    }

    private Map<Long, Courier> improveSolution(Map<Long, Courier> assignments, List<Courier> couriers,
            Map<Long, CourierOrder> ordersMap) {

        int check = 0;
        while (check == 0) {
            check = 1;
            for (Map.Entry<Long, Courier> assignment : assignments.entrySet()) {
                for (Courier courier : couriers) {

                    if (!assignments.containsValue(courier)) {
                        double distance = courierDistance(courier, ordersMap.get(assignment.getKey()));
                        float time = (float) (distance / data.getConfiguration().getCourierSpeedKmH() * 60);
                        if (time < assignment.getValue().getDeliveryTimeMinutes()) {
                            courier.setDeliveryTimeMinutes(Float.valueOf(time));
                            assignments.put(assignment.getKey(), courier);
                            check = 0;
                            break;
                        }
                    }

                }
                if (check == 0)
                    break;
            }
        }
        check = 0;
        while (check == 0) {
            check = 1;
            for (Map.Entry<Long, Courier> assignment1 : assignments.entrySet()) {
                for (Map.Entry<Long, Courier> assignment2 : assignments.entrySet()) {
                    Courier courier1 = assignment1.getValue();
                    Courier courier2 = assignment2.getValue();
                    if (courier1.getId() == courier2.getId()) {
                        continue;
                    }
                    CourierOrder courierOrder1 = ordersMap.get(assignment1.getKey());
                    CourierOrder courierOrder2 = ordersMap.get(assignment2.getKey());
                    double courierDistance1 = courierDistance(courier1, courierOrder2);
                    double courierDistance2 = courierDistance(courier2, courierOrder1);
                    Float maxDistance = data.getConfiguration().getCourierSpeedKmH()
                            * data.getConfiguration().getMaxDeliveryTimeMinutes() / 60;
                    if (courierDistance1 > maxDistance) {
                        continue;
                    }
                    if (courierDistance2 > maxDistance) {
                        continue;
                    }
                    float time1 = (float) (courierDistance1 / data.getConfiguration().getCourierSpeedKmH() * 60);
                    float time2 = (float) (courierDistance2 / data.getConfiguration().getCourierSpeedKmH() * 60);
                    float previousTime = courier1.getDeliveryTimeMinutes() + courier2.getDeliveryTimeMinutes();
                    if (time1 + time2 < previousTime) {
                        assignments.put(courierOrder2.getId(), courier1);
                        courier1.setDeliveryTimeMinutes(Float.valueOf(time2));
                        assignments.put(courierOrder1.getId(), courier2);
                        courier1.setDeliveryTimeMinutes(Float.valueOf(time1));
                    }
                }
                if (check == 0)
                    break;
            }
        }

        return assignments;
    }

    private boolean checkOptions(List<CourierOrder> orders, Courier courier, Map<Long, Boolean> seen,
            Map<Long, Courier> assign, Long previousId, double previousDistance) {

        for (CourierOrder order : orders) {
            double distance = courierDistance(courier, order);
            // Assume that the distance is in km, calculate the time to deliver the order
            float time = (float) (distance / data.getConfiguration().getCourierSpeedKmH() * 60);

            if (checkOrder(distance, time)) {
                if (seen.get(order.getId()) == null) {
                    seen.put(order.getId(), true);
                    if (assign.get(order.getId()) == null || checkOptions(orders, assign.get(order.getId()),
                            seen, assign, order.getId(), distance)) {
                        assign.put(order.getId(), courier);
                        courier.setDeliveryTimeMinutes(Float.valueOf(time));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkOrder(double distance, double time) {
        if (time < data.getConfiguration().getMaxDeliveryTimeMinutes())
            return true;
        return false;
    }

    private double courierDistance(Courier courier, CourierOrder order) {
        List<Action> actions = order.getActions();

        // if we don't have one pickup and one delivery we skip this Order
        if (actions.size() != 2)
            return -1;
        int pickupId = 0;
        int deliveryId = 1;
        if (actions.get(1).getType().equals("PICKUP")) {
            pickupId = 1;
            deliveryId = 0;
        } else if (!actions.get(0).getType().equals("PICKUP")) {
            return -1;
        }

        // calculate the courier distance from his start point to the pickup destination
        // and then to the delivery destination
        double distance = 0.0F;
        distance += calculateDistance(courier.getLat(), courier.getLon(), actions.get(pickupId).getLat(),
                actions.get(pickupId).getLon());
        distance += calculateDistance(actions.get(pickupId).getLat(),
                actions.get(pickupId).getLon(), actions.get(deliveryId).getLat(), actions.get(deliveryId).getLon());
        return distance;
    }

    private double calculateDistance(Float lat1, Float lon1, Float lat2, Float lon2) {
        Float diff1 = lat2 - lat1;
        Float diff2 = lon2 - lon1;
        return Math.sqrt(diff1 * diff1 + diff2 * diff2);
    }
}