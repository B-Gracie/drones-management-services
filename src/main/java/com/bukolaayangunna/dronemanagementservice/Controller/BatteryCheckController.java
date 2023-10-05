package com.bukolaayangunna.dronemanagementservice.Controller;


import com.bukolaayangunna.dronemanagementservice.Model.BatteryLog;
import com.bukolaayangunna.dronemanagementservice.Model.BatteryLogDTO;
import com.bukolaayangunna.dronemanagementservice.Service.BatteryCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/batterylogs")
public class BatteryCheckController {

    private final BatteryCheckService batteryCheckService;

    @Autowired
    public BatteryCheckController(BatteryCheckService batteryCheckService) {
        this.batteryCheckService = batteryCheckService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BatteryLogDTO>> getAllBatteryLogs() {
        // Retrieve battery logs from service
        List<BatteryLog> batteryLogs = batteryCheckService.getAllBatteryLogs();

        // Convert BatteryLog entities to BatteryLogDTOs
        List<BatteryLogDTO> batteryLogDTOs = batteryLogs.stream()
                .map(batteryLog -> new BatteryLogDTO(
                        //batteryLog.getId(),
                        batteryLog.getDrone() != null ? batteryLog.getDrone().getId() : null,
                        batteryLog.getBatteryCapacity(),
                        batteryLog.getTimestamp()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(batteryLogDTOs);
    }

}



