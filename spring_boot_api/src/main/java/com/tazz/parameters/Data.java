package com.tazz.parameters;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Data")
public class Data implements Serializable {
    @Column(name = "configuration", nullable = false)
    private Configuration configuration;

    @OneToMany(mappedBy = "Data")
    @Column(name = "couriers", nullable = false)
    private List<Courier> couriers;

    @OneToMany(mappedBy = "Data")
    @Column(name = "orders", nullable = false)
    private List<CourierOrder> orders;

    public Configuration getConfiguration() {
        return configuration;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public List<CourierOrder> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        if (configuration == null)
            return "error";
        String dataToString = configuration.toString();

        if (couriers != null)
            for (Courier courier : couriers) {
                dataToString += " " + courier;
            }

        if (orders != null)
            for (CourierOrder order : orders) {
                dataToString += " " + order;
            }
        return dataToString;
    }
}