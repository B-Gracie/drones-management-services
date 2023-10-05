package com.bukolaayangunna.dronemanagementservice.Repository;

import com.bukolaayangunna.dronemanagementservice.Model.BatteryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryLogRepository extends JpaRepository<BatteryLog, Long> {

}

