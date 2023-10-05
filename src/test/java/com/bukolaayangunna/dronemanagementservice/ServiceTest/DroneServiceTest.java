package com.bukolaayangunna.dronemanagementservice.ServiceTest;

import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Repository.DroneRepository;
import com.bukolaayangunna.dronemanagementservice.Service.DroneServiceImpl;
import com.bukolaayangunna.dronemanagementservice.Service.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private DroneServiceImpl droneService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDronesAsync() {
        // Arrange
        List<Drone> drones = new ArrayList<>();
        drones.add(new Drone());
        drones.add(new Drone());

        when(droneRepository.findAll()).thenReturn(drones);

        // Act
        CompletableFuture<List<Drone>> result = droneService.getAllDronesAsync();

        // Assert
        assertTrue(result.join().size() == 2);
        verify(droneRepository, times(1)).findAll();
    }

    @Test
    public void testGetDroneByIdAsync() {
        // Arrange
        Long droneId = 1L;
        Drone drone = new Drone();
        drone.setId(droneId);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // Act
        CompletableFuture<Drone> result = droneService.getDroneByIdAsync(droneId);

        // Assert
        assertEquals(droneId, result.join().getId());
        verify(droneRepository, times(1)).findById(droneId);
    }

    @Test
    public void testRegisterDroneAsync_ValidData() {
        // Arrange
        Drone drone = new Drone();
        drone.setWeightLimit(250.0);
        drone.setBatteryCapacity(50);

        when(droneRepository.save(drone)).thenReturn(drone);

        // Act
        CompletableFuture<Void> result = droneService.registerDroneAsync(drone);

        // Assert
        assertDoesNotThrow(result::join);
        verify(droneRepository, times(1)).save(drone);
    }

    @Test
    public void testRegisterDroneAsync_InvalidData() {
        // Arrange
        Drone drone = new Drone();
        drone.setWeightLimit(600.0); // Invalid weight limit

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> droneService.registerDroneAsync(drone).join());
        verify(droneRepository, never()).save(drone);
    }

    @Test
    public void testUpdateDroneBatteryCapacityAsync() {
        // Arrange
        Long droneId = 1L;
        int newBatteryCapacity = 80;
        Drone drone = new Drone();
        drone.setId(droneId);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(drone)).thenReturn(drone);

        // Act
        CompletableFuture<Void> result = droneService.updateDroneBatteryCapacityAsync(droneId, newBatteryCapacity);

        // Assert
        assertDoesNotThrow(result::join);
        assertEquals(newBatteryCapacity, drone.getBatteryCapacity());
        verify(droneRepository, times(1)).findById(droneId);
        verify(droneRepository, times(1)).save(drone);
    }

    @Test
    public void testUpdateDroneBatteryCapacityAsync_DroneNotFound() {
        // Arrange
        Long droneId = 1L;
        int newBatteryCapacity = 80;

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> droneService.updateDroneBatteryCapacityAsync(droneId, newBatteryCapacity).join());
        verify(droneRepository, times(1)).findById(droneId);
        verify(droneRepository, never()).save(any(Drone.class));
    }

    @Test
    public void testUpdateDroneStateAsync() {
        // Arrange
        Long droneId = 1L;
        Drone.DroneState newState = Drone.DroneState.LOADED;
        Drone drone = new Drone();
        drone.setId(droneId);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        when(droneRepository.save(drone)).thenReturn(drone);

        // Act
        CompletableFuture<Void> result = droneService.updateDroneStateAsync(droneId, newState);

        // Assert
        assertDoesNotThrow(result::join);
        assertEquals(newState, drone.getState());
        verify(droneRepository, times(1)).findById(droneId);
        verify(droneRepository, times(1)).save(drone);
    }

    @Test
    public void testUpdateDroneStateAsync_DroneNotFound() {
        // Arrange
        Long droneId = 1L;
        Drone.DroneState newState = Drone.DroneState.LOADED;

        when(droneRepository.findById(droneId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> droneService.updateDroneStateAsync(droneId, newState).join());
        verify(droneRepository, times(1)).findById(droneId);
        verify(droneRepository, never()).save(any(Drone.class));
    }

    @Test
    public void testLoadMedicationsAsync() {
        // Arrange
        Long droneId = 1L;
        List<Long> medicationIds = new ArrayList<>();
        medicationIds.add(1L);
        medicationIds.add(2L);

        doNothing().when(droneRepository).loadMedications(droneId, medicationIds);

        // Act
        CompletableFuture<Void> result = droneService.loadMedicationsAsync(droneId, medicationIds);

        // Assert
        assertDoesNotThrow(result::join);
        verify(droneRepository, times(1)).loadMedications(droneId, medicationIds);
    }
}
