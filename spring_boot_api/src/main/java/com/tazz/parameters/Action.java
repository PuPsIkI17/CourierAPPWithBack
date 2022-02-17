package com.tazz.parameters;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Action")
public class Action implements Serializable {

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "lat", nullable = false)
    private Float lat;

    @Column(name = "lon", nullable = false)
    private Float lon;

    public String getType() {
        return type;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return type + " " + Float.toString(lat) + " " + Float.toString(lon);
    }
}
