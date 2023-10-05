package com.bukolaayangunna.dronemanagementservice.Service;

import com.bukolaayangunna.dronemanagementservice.Model.BatteryLog;
import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Repository.BatteryLogRepository;
import com.bukolaayangunna.dronemanagementservice.Repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BatteryCheckServiceImpl implements BatteryCheckService {

    @Autowired
    public DroneRepository droneRepository;

    @Autowired
    public BatteryLogRepository batteryLogRepository;

    // Define a scheduled method to periodically check battery levels and create logs
    @Scheduled(fixedRate = 3600000) // Run every hour (you can adjust the rate as needed)
    public void checkBatteryLevelsAndCreateLogs() {
        // Retrieve all drones from the database
        List<Drone> drones = droneRepository.findAll();

        // Iterate through the drones and check their battery levels
        for (Drone drone : drones) {
            // Get the current battery capacity of the drone
            int batteryCapacity = drone.getBatteryCapacity();

            // Create a battery log entry
            BatteryLog batteryLog = new BatteryLog();
            batteryLog.setDrone(drone);
            batteryLog.setBatteryCapacity(batteryCapacity);
            batteryLog.setTimestamp(new Date()); // Set the timestamp to the current time

            // Save the battery log entry to the database
            batteryLogRepository.save(batteryLog);
        }
    }


    @Override
    public List<BatteryLog> getAllBatteryLogs() {
        return batteryLogRepository.findAll();
    }

    public void saveBatteryLog(Drone drone) {
        // Create a battery log entry
        BatteryLog batteryLog = new BatteryLog();
        batteryLog.setDrone(drone);
        batteryLog.setBatteryCapacity(drone.getBatteryCapacity());
        batteryLog.setTimestamp(new Date()); // Set the timestamp to the current time

        // Save the battery log entry to the database
        batteryLogRepository.save(batteryLog);
    }


    }



