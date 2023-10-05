package com.bukolaayangunna.dronemanagementservice.Repository;


import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    @Modifying
    @Query("UPDATE Drone d SET d.medications = ?2 WHERE d.id = ?1")
    void loadMedications(long droneId, List<Long> medicationIds);
}

