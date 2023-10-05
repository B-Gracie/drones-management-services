package com.bukolaayangunna.dronemanagementservice.Service;

import com.bukolaayangunna.dronemanagementservice.Model.Drone;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface DroneService {
    CompletableFuture<List<Drone>> getAllDronesAsync();

    CompletableFuture<Void> updateDroneBatteryCapacityAsync(Long droneId, int newBatteryCapacity);

    CompletableFuture<Void> updateDroneStateAsync(Long droneId, Drone.DroneState newState);

    CompletableFuture<Drone> getDroneByIdAsync(Long id);

    CompletableFuture<Void> registerDroneAsync(Drone drone);

    CompletableFuture<Void> loadMedicationsAsync(long droneId, List<Long> medicationIds);
}
