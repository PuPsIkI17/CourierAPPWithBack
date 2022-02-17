package com.tazz.parameters;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CourierOrder")
public class CourierOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "CourierOrder")
    @Column(name = "actions", nullable = false)
    private List<Action> actions;

    public Long getId() {
        return id;
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        String ordersToString = Long.toString(id);
        if (actions != null)
            for (Action action : actions) {
                ordersToString += " " + action;
            }
        return ordersToString;
    }
}
