package com.bukolaayangunna.dronemanagementservice.Model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BatteryLogDTO {

    private Long droneId;
    private int batteryCapacity;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date timestamp;

    public BatteryLogDTO(Long droneId, int batteryCapacity, Date timestamp) {
        this.droneId = droneId;
        this.batteryCapacity = batteryCapacity;
        this.timestamp = timestamp;
    }
    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}


