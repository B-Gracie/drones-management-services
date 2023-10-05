package com.bukolaayangunna.dronemanagementservice.ControllerTests;

import com.bukolaayangunna.dronemanagementservice.Controller.BatteryCheckController;
import com.bukolaayangunna.dronemanagementservice.Model.BatteryLog;
import com.bukolaayangunna.dronemanagementservice.Model.BatteryLogDTO;
import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Service.BatteryCheckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class BatteryCheckControllerTest {

    @Mock
    private BatteryCheckService batteryCheckService;

    private BatteryCheckController batteryCheckController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        batteryCheckController = new BatteryCheckController(batteryCheckService);
    }

    @Test
    public void testGetAllBatteryLogs()
    {
        Drone drone1 = new Drone();
        drone1.setId(1L);
        drone1.setSerialNumber("X101");


        Drone drone2 = new Drone();
        drone2.setId(2L);
        drone2.setSerialNumber("Y102");

        BatteryLog batteryLog1 = new BatteryLog();
        batteryLog1.setDrone(drone1);
        batteryLog1.setBatteryCapacity(80);
        batteryLog1.setTimestamp(new Date());

        BatteryLog batteryLog2 = new BatteryLog();
        batteryLog2.setDrone(drone2);
        batteryLog2.setBatteryCapacity(75);
        batteryLog2.setTimestamp(new Date());

        // Create a list of sample battery logs
        List<BatteryLog> batteryLogs = Arrays.asList(batteryLog1, batteryLog2);

        // Mock the behavior of batteryCheckService
        when(batteryCheckService.getAllBatteryLogs()).thenReturn(batteryLogs);

        // Call the controller method
        ResponseEntity<List<BatteryLogDTO>> responseEntity = batteryCheckController.getAllBatteryLogs();

        verify(batteryCheckService, times(1)).getAllBatteryLogs();

        // Assert the response
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
    }
}
