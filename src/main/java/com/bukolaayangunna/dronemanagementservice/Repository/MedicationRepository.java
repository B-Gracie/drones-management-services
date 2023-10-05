package com.bukolaayangunna.dronemanagementservice.Repository;

import com.bukolaayangunna.dronemanagementservice.Model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long>
{

}

