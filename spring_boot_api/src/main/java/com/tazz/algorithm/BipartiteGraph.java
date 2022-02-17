package com.tazz.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tazz.parameters.Courier;
import com.tazz.parameters.Data;

import com.tazz.parameters.Action;
import com.tazz.parameters.CourierOrder;

// Complexity = O(n + m)
public class BipartiteGraph {
    Data data;

    public BipartiteGraph(Data data) {
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
            checkOptions(orders, courier, seen, assign);
        }
        assign = smallestPath(assign);
        return assign;
    }

    private Map<Long, Courier> smallestPath(Map<Long, Courier> assign) {

        return assign;
    }

    private boolean checkOptions(List<CourierOrder> orders, Courier courier, Map<Long, Boolean> seen,
            Map<Long, Courier> assign) {
        for (CourierOrder order : orders) {
            double distance = courierDistance(courier, order);
            // Assume that the distance is in km, calculate the time to deliver the order
            float time = (float) (distance / data.getConfiguration().getCourierSpeedKmH() * 60);

            if (checkOrder(distance, time) && seen.get(order.getId()) == null) {
                seen.put(order.getId(), true);
                if (assign.get(order.getId()) == null || checkOptions(orders, assign.get(order.getId()),
                        seen, assign)) {
                    assign.put(order.getId(), courier);
                    courier.setDeliveryTimeMinutes(Float.valueOf(time));
                    return true;
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