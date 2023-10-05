package com.bukolaayangunna.dronemanagementservice.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Drone {

    @JsonIgnore
    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medication> medications = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;
    private String model;
    private double weightLimit;
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    private DroneState state;


    public enum DroneState {
        IDLE,
        LOADING,
        LOADED,
        DELIVERING,
        DELIVERED,
        RETURNING
    }


    // Constructors

    public Drone() {}


    public Long getId()
    {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public double getWeightLimit()
    {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit)
    {
        this.weightLimit = weightLimit;
    }

    public int getBatteryCapacity()
    {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity)
    {
        this.batteryCapacity = batteryCapacity;
    }

    public DroneState getState()
    {
        return state;
    }

    public void setState(DroneState state)
    {
        this.state = state;
    }

    public List<Medication> getMedications()
    {
        return medications;
    }

    public void setMedications(List<Medication> medications)
    {
        this.medications = medications;
    }
}

