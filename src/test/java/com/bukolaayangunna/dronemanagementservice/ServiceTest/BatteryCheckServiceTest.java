package com.bukolaayangunna.dronemanagementservice.ServiceTest;

import com.bukolaayangunna.dronemanagementservice.Model.BatteryLog;
import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Repository.BatteryLogRepository;
import com.bukolaayangunna.dronemanagementservice.Repository.DroneRepository;
import com.bukolaayangunna.dronemanagementservice.Service.BatteryCheckServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BatteryCheckServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private BatteryLogRepository batteryLogRepository;

    private BatteryCheckServiceImpl batteryCheckService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        batteryCheckService = new BatteryCheckServiceImpl();
        batteryCheckService.droneRepository = droneRepository;
        batteryCheckService.batteryLogRepository = batteryLogRepository;
    }

    @Test
    public void testCheckBatteryLevelsAndCreateLogs() {
        // Create a list of drones for testing
        List<Drone> drones = new ArrayList<>();
        Drone drone1 = new Drone();
        drone1.setId(1L);
        drone1.setBatteryCapacity(80);
        drones.add(drone1);

        when(droneRepository.findAll()).thenReturn(drones);

        // Call the method to check battery levels and create logs
        batteryCheckService.checkBatteryLevelsAndCreateLogs();

        // Verify that batteryLogRepository.save was called for each drone
        verify(batteryLogRepository, times(1)).save(any(BatteryLog.class));
    }

    @Test
    public void testSaveBatteryLog() {
        Drone drone = new Drone();
        drone.setId(1L);
        drone.setBatteryCapacity(70);

        // Call the method to save a battery log
        batteryCheckService.saveBatteryLog(drone);

        // Verify that batteryLogRepository.save was called with the expected parameters
        verify(batteryLogRepository, times(1)).save(any(BatteryLog.class));
    }
}
