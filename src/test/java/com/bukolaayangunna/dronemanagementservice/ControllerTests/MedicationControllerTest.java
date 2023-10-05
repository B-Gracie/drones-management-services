package com.bukolaayangunna.dronemanagementservice.ControllerTests;

import com.bukolaayangunna.dronemanagementservice.Controller.MedicationController;
import com.bukolaayangunna.dronemanagementservice.Model.Medication;
import com.bukolaayangunna.dronemanagementservice.Model.MedicationDto;
import com.bukolaayangunna.dronemanagementservice.Service.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MedicationControllerTest {

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController medicationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
    }

    // Define test data directly in the test class
    private Medication createMedication(String name, double weight, String code) {
        Medication medication = new Medication();
        medication.setId(1L);
        medication.setName(name);
        medication.setWeight(weight);
        medication.setCode(code);
        // Set other fields as needed
        return medication;
    }

    private MedicationDto createMedicationDto(String name, double weight, String code) {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setName(name);
        medicationDto.setWeight(weight);
        medicationDto.setCode(code);
        // Set other fields as needed
        return medicationDto;
    }

    @Test
    public void testGetAllMedications() throws Exception {
        List<Medication> medications = new ArrayList<>();
        medications.add(createMedication("Test Medication 1", 2.5, "ABC123"));
        medications.add(createMedication("Test Medication 2", 3.0, "XYZ789"));

        when(medicationService.getAllMedicationsAsync()).thenReturn(CompletableFuture.completedFuture(medications));

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetMedicationById() throws Exception {
        Long medicationId = 1L;
        Medication medication = createMedication("Test Medication", 2.5, "ABC123");
        medication.setId(medicationId);

        when(medicationService.getMedicationByIdAsync(medicationId)).thenReturn(CompletableFuture.completedFuture(medication));

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/" + medicationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testCreateAndLoadMedicationAsyncSuccess() throws InterruptedException, ExecutionException {
        // Arrange
        Long droneId = 1L;
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setName("Test Medication");
        medicationDto.setWeight(2.5);
        medicationDto.setCode("ABC123");

        Medication createdMedication = new Medication();
        createdMedication.setId(1L);
        createdMedication.setName("Test Medication");
        createdMedication.setWeight(2.5);
        createdMedication.setCode("ABC123");

        CompletableFuture<MedicationDto> future = CompletableFuture.completedFuture(createdMedication).thenApply(medication -> {
            MedicationDto dto = new MedicationDto();
            dto.setName(medication.getName());
            dto.setWeight(medication.getWeight());
            dto.setCode(medication.getCode());
            return dto;
        });

        when(medicationService.createMedicationAndLoadAsync(droneId, medicationDto)).thenReturn(future);

        // Act
        ResponseEntity<MedicationDto> response = medicationController.createAndLoadMedicationAsync(droneId, medicationDto).get();

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        MedicationDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Test Medication", responseBody.getName());
        assertEquals(2.5, responseBody.getWeight());
        assertEquals("ABC123", responseBody.getCode());

        verify(medicationService, times(1)).createMedicationAndLoadAsync(droneId, medicationDto);
    }

    @Test
    public void testCreateAndLoadMedicationAsyncFailure() throws InterruptedException, ExecutionException {
        // Arrange
        Long droneId = 1L;
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setName("Test Medication");
        medicationDto.setWeight(2.5);
        medicationDto.setCode("ABC123");

        CompletableFuture<MedicationDto> future = CompletableFuture.failedFuture(new RuntimeException("Test exception"));

        when(medicationService.createMedicationAndLoadAsync(droneId, medicationDto)).thenReturn(future);

        // Act
        ResponseEntity<MedicationDto> response = medicationController.createAndLoadMedicationAsync(droneId, medicationDto).get();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        verify(medicationService, times(1)).createMedicationAndLoadAsync(droneId, medicationDto);
    }

}

