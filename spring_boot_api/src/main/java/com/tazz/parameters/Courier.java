package com.tazz.parameters;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Courier")
public class Courier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lat", nullable = false)
    private Float lat;

    @Column(name = "lon", nullable = false)
    private Float lon;

    private Float deliveryTimeMinutes;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setDeliveryTimeMinutes(Float deliveryTimeMinutes) {
        this.deliveryTimeMinutes = deliveryTimeMinutes;
    }

    public Float getDeliveryTimeMinutes() {
        return deliveryTimeMinutes;
    }

    @Override
    public String toString() {
        return Long.toString(id) + " " + name + " " + Float.toString(lat) + " " + Float.toString(lon);
    }
}
