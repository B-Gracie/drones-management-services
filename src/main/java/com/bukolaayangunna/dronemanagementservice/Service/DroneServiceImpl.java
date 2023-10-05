
package com.bukolaayangunna.dronemanagementservice.Service;

import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Repository.DroneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationService medicationService;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, MedicationService medicationService) {
        this.droneRepository = droneRepository;
        this.medicationService = medicationService;
    }

    @Async
    @Override
    public CompletableFuture<List<Drone>> getAllDronesAsync() {
        List<Drone> drones = droneRepository.findAll();
        return CompletableFuture.completedFuture(drones);
    }

    @Async
    @Override
    public CompletableFuture<Drone> getDroneByIdAsync(Long id) {
        Optional<Drone> optionalDrone = droneRepository.findById(id);
        return CompletableFuture.completedFuture(optionalDrone.orElse(null));
    }

    @Async
    @Override
    public CompletableFuture<Void> registerDroneAsync(Drone drone) {
        if (validateDrone(drone)) {
            droneRepository.save(drone);
            return CompletableFuture.completedFuture(null);
        } else {
            throw new IllegalArgumentException("Invalid drone data");
        }
    }

    //Validate Json data in the drone entity

    private boolean validateDrone(Drone drone) {
        return isWeightLimitValid(drone.getWeightLimit()) && isBatteryCapacityValid(drone.getBatteryCapacity());
    }

    private boolean isWeightLimitValid(double weightLimit) {
        return weightLimit > 0 && weightLimit <= 500;
    }

    private boolean isBatteryCapacityValid(int batteryCapacity) {
        return batteryCapacity >= 25 && batteryCapacity <= 100;
    }

    @Transactional
    @Override
    public CompletableFuture<Void> updateDroneBatteryCapacityAsync(Long droneId, int newBatteryCapacity) {
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);

        if (optionalDrone.isPresent()) {
            Drone drone = optionalDrone.get();

            // Update the battery capacity
            drone.setBatteryCapacity(newBatteryCapacity);

            // Save the updated drone in the repository
            droneRepository.save(drone);

            return CompletableFuture.completedFuture(null);
        } else {
            throw new IllegalArgumentException("Drone not found for ID: " + droneId);
        }
    }

    @Transactional
    @Override
    public CompletableFuture<Void> updateDroneStateAsync(Long droneId, Drone.DroneState newState) {
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);

        if (optionalDrone.isPresent()) {
            Drone drone = optionalDrone.get();

            // Update the drone state
            drone.setState(newState);

            // Save the updated drone in the repository
            droneRepository.save(drone);

            return CompletableFuture.completedFuture(null);
        } else {
            throw new IllegalArgumentException("Drone not found for ID: " + droneId);
        }
    }
    public CompletableFuture<Void> loadMedicationsAsync(long droneId, List<Long> medicationIds) {
        try {
            // Call the repository method to load medications onto the drone
            droneRepository.loadMedications(droneId, medicationIds);
            return CompletableFuture.completedFuture(null);
        } catch (Exception ex) {
            // Handle exceptions or rethrow as needed
            throw new RuntimeException("Error loading medications onto the drone.", ex);
        }
    }


}
