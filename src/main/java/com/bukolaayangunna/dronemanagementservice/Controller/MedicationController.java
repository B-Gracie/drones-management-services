package com.bukolaayangunna.dronemanagementservice.Controller;

import com.bukolaayangunna.dronemanagementservice.Model.Medication;
import com.bukolaayangunna.dronemanagementservice.Model.MedicationDto;
import com.bukolaayangunna.dronemanagementservice.Service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }


    @GetMapping("/all")
    public CompletableFuture<ResponseEntity<List<Medication>>> getAllMedications() {
        return medicationService.getAllMedicationsAsync()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    ex.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(null); // Return an empty body or handle the error as needed
                });
    }

    @GetMapping("/{id}")
    public CompletableFuture<Medication> getMedicationById(@PathVariable Long id) {
        return medicationService.getMedicationByIdAsync(id);
    }

    @PostMapping("create-and-load-medications/{droneId}")
    public CompletableFuture<ResponseEntity<MedicationDto>> createAndLoadMedicationAsync(
            @PathVariable Long droneId,
            @RequestBody MedicationDto medicationDto) {
        return medicationService.createMedicationAndLoadAsync(droneId, medicationDto)
                .thenApply(createdMedicationDto -> ResponseEntity.status(HttpStatus.CREATED).body(createdMedicationDto))
                .exceptionally(ex -> {
                    // Handle exceptions and return an error response
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

}

