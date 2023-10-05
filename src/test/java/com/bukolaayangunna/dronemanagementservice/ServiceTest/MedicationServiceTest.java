package com.bukolaayangunna.dronemanagementservice.ServiceTest;

import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Model.Medication;
import com.bukolaayangunna.dronemanagementservice.Model.MedicationDto;
import com.bukolaayangunna.dronemanagementservice.Repository.DroneRepository;
import com.bukolaayangunna.dronemanagementservice.Repository.MedicationRepository;
import com.bukolaayangunna.dronemanagementservice.Service.MedicationServiceImpl;
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

public class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private MedicationServiceImpl medicationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMedicationsAsync() {
        // Arrange
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication());
        medications.add(new Medication());

        when(medicationRepository.findAll()).thenReturn(medications);

        // Act
        CompletableFuture<List<Medication>> result = medicationService.getAllMedicationsAsync();

        // Assert
        assertTrue(result.join().size() == 2);
        verify(medicationRepository, times(1)).findAll();
    }

    @Test
    public void testGetMedicationByIdAsync() {
        // Arrange
        Long medicationId = 1L;
        Medication medication = new Medication();
        medication.setId(medicationId);

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medication));

        // Act
        CompletableFuture<Medication> result = medicationService.getMedicationByIdAsync(medicationId);

        // Assert
        assertEquals(medicationId, result.join().getId());
        verify(medicationRepository, times(1)).findById(medicationId);
    }

    @Test
    public void testSaveMedicationAsync() {
        // Arrange
        Medication medication = new Medication();

        when(medicationRepository.save(medication)).thenReturn(medication);

        // Act
        CompletableFuture<Void> result = medicationService.saveMedicationAsync(medication);

        // Assert
        assertDoesNotThrow(result::join);
        verify(medicationRepository, times(1)).save(medication);
    }

    @Test
    public void testCreateMedicationAsync() {
        // Arrange
        Medication medication = new Medication();
        medication.setName("Test Medication");
        medication.setWeight(2.5);
        medication.setCode("ABC123");

        when(medicationRepository.save(medication)).thenReturn(medication);

        // Act
        CompletableFuture<Medication> result = medicationService.createMedicationAsync(medication);

        // Assert
        assertNotNull(result.join());
        assertEquals("Test Medication", result.join().getName());
        assertEquals(2.5, result.join().getWeight());
        assertEquals("ABC123", result.join().getCode());
        verify(medicationRepository, times(1)).save(medication);
    }

    @Test
    public void testCreateMedicationAndLoadAsync_DroneFound_InvalidWeight() {
        // Arrange
        Long droneId = 1L;
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setName("Test Medication");
        medicationDto.setWeight(600.0); // Invalid weight
        medicationDto.setCode("ABC123");

        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(50);

        when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> medicationService.createMedicationAndLoadAsync(droneId, medicationDto).join());
        verify(droneRepository, times(1)).findById(droneId);
        verify(medicationRepository, never()).save(any(Medication.class));
    }

}

