package com.bukolaayangunna.dronemanagementservice.Model;

public class MedicationDto {
    private String name;
    private double weight;
    private String code;
    private Long droneId;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getWeight()
    {
        return weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Long getDroneId()
    {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }


}
