package com.bukolaayangunna.dronemanagementservice.Controller;

import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/drones")
public class DroneController
{

    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService)
    {
        this.droneService = droneService;
    }

    @GetMapping("/all")
    public CompletableFuture<List<Drone>> getAllDrones()
    {
        return droneService.getAllDronesAsync();
    }

    @GetMapping("/{id}")
    public CompletableFuture<Drone> getDroneById(@PathVariable Long id)
    {
        return droneService.getDroneByIdAsync(id);
    }

    @PostMapping("/register")
    public CompletableFuture<Void> registerDrone(@RequestBody Drone drone)
    {
        return droneService.registerDroneAsync(drone);
    }
    @PutMapping("/{id}/updateBatteryCapacity")
    public CompletableFuture<Void> updateBatteryCapacity(@PathVariable Long id, @RequestParam int newBatteryCapacity) {
        return droneService.updateDroneBatteryCapacityAsync(id, newBatteryCapacity);
    }

    @PutMapping("/{id}/updateState")
    public CompletableFuture<Void> updateState(@PathVariable Long id, @RequestParam Drone.DroneState newState) {
        return droneService.updateDroneStateAsync(id, newState);
    }

}
