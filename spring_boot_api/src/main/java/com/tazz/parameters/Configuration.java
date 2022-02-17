package com.tazz.parameters;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Configuration")
public class Configuration implements Serializable {

    @Column(name = "maxDeliveryTimeMinutes", nullable = false)
    private Float maxDeliveryTimeMinutes;

    @Column(name = "courierSpeedKmH", nullable = false)
    private Float courierSpeedKmH;

    public Float getMaxDeliveryTimeMinutes() {
        return maxDeliveryTimeMinutes;
    }

    public Float getCourierSpeedKmH() {
        return courierSpeedKmH;
    }

    @Override
    public String toString() {
        return Float.toString(maxDeliveryTimeMinutes) + " " + Float.toString(courierSpeedKmH);
    }
}
