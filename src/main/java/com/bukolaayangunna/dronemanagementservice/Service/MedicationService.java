package com.bukolaayangunna.dronemanagementservice.Service;

import com.bukolaayangunna.dronemanagementservice.Model.Medication;
import com.bukolaayangunna.dronemanagementservice.Model.MedicationDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MedicationService {
    CompletableFuture<List<Medication>> getAllMedicationsAsync();
    CompletableFuture<Medication> getMedicationByIdAsync(Long id);
    //CompletableFuture<Medication> createMedicationAndLoadAsync(Long droneId, MedicationDto medicationDto);
    CompletableFuture<Void> saveMedicationAsync(Medication medication);
    CompletableFuture<Medication> createMedicationAsync(Medication medication);
    CompletableFuture<MedicationDto> createMedicationAndLoadAsync(Long droneId, MedicationDto medicationDto);

}

