package com.bukolaayangunna.dronemanagementservice.Service;

import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Model.Medication;
import com.bukolaayangunna.dronemanagementservice.Model.MedicationDto;
import com.bukolaayangunna.dronemanagementservice.Repository.DroneRepository;
import com.bukolaayangunna.dronemanagementservice.Repository.MedicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MedicationServiceImpl implements MedicationService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }
    @Async
    @Override
    public CompletableFuture<List<Medication>> getAllMedicationsAsync() {
        try {
            List<Medication> medications = medicationRepository.findAll();
            return CompletableFuture.completedFuture(medications);
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(null); // Return an empty result or handle the error accordingly.
        }
    }

    @Async
    @Override
    public CompletableFuture<Medication> getMedicationByIdAsync(Long id) {
        Optional<Medication> optionalMedication = medicationRepository.findById(id);
        return CompletableFuture.completedFuture(optionalMedication.orElse(null));
    }


    @Async
    @Override
    public CompletableFuture<Void> saveMedicationAsync(Medication medication) {
        medicationRepository.save(medication);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Medication> createMedicationAsync(Medication medication) {

        Medication createdMedication = medicationRepository.save(medication);

        return CompletableFuture.completedFuture(createdMedication);
    }

    @Transactional
    public void addMedicationToDrone(Drone drone, Medication medication) {
        // Associate the medication with the drone
        drone.getMedications().add(medication);
        medication.setDrone(drone);

        // Update the drone and medication in the database
        droneRepository.save(drone);
        medicationRepository.save(medication);
    }

    @Async
    @Transactional
    public CompletableFuture<MedicationDto> createMedicationAndLoadAsync(Long droneId, MedicationDto medicationDto) {

        // Find the Drone object by ID
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);

        if (optionalDrone.isPresent()) {
            Drone drone = optionalDrone.get();

            // Check if the weight of the medication is more than 500
            if (medicationDto.getWeight() > 500) {
                throw new IllegalArgumentException("Medication weight exceeds the maximum allowed weight.");
            }

            // Convert MedicationDto to Medication
            Medication medication = new Medication();
            medication.setName(medicationDto.getName());
            medication.setWeight(medicationDto.getWeight());
            medication.setCode(medicationDto.getCode());
            medication.setDrone(drone);

            // Create the medication and save it asynchronously
            if (drone.getBatteryCapacity() >= 25) {
                // Create the medication and save it asynchronously
                Medication createdMedication = medicationRepository.save(medication);

                // method to associate the medication with the drone, e.g., addMedicationToDrone
                addMedicationToDrone(drone, createdMedication);

                // Convert the created Medication to MedicationDto and return
                MedicationDto createdMedicationDto = convertMedicationToDto(createdMedication);
                return CompletableFuture.completedFuture(createdMedicationDto);
            } else {
                throw new IllegalArgumentException("Drone not found for ID: " + droneId);
            }
        } else {
            // Return a completed exceptionally CompletableFuture if the drone is not found
            return CompletableFuture.failedFuture(new IllegalArgumentException("Drone not found for ID: " + droneId));
        }
    }

    // Utility method to convert Medication entity to MedicationDto
    private MedicationDto convertMedicationToDto(Medication medication) {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setName(medication.getName());
        medicationDto.setWeight(medication.getWeight());
        medicationDto.setCode(medication.getCode());

        return medicationDto;
    }

}

