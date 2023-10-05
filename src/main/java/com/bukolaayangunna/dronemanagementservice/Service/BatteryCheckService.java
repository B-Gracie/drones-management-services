package com.bukolaayangunna.dronemanagementservice.Service;

import com.bukolaayangunna.dronemanagementservice.Model.BatteryLog;
import com.bukolaayangunna.dronemanagementservice.Model.Drone;

import java.util.List;

public interface BatteryCheckService {
    List<BatteryLog> getAllBatteryLogs();
    void saveBatteryLog(Drone drone);

}

