package com.bukolaayangunna.dronemanagementservice.ControllerTests;

import com.bukolaayangunna.dronemanagementservice.Controller.DroneController;
import com.bukolaayangunna.dronemanagementservice.Model.Drone;
import com.bukolaayangunna.dronemanagementservice.Service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DroneController.class)
public class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;


    @BeforeEach
   public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDrones() throws Exception
    {
        List<Drone> drones = new ArrayList<>();
        Drone drone1 = new Drone();
        drone1.setId(105L);
        drone1.setSerialNumber("X105");
        drone1.setModel("Middleweight");
        drone1.setWeightLimit(500.0);
        drone1.setBatteryCapacity(100);
        drone1.setState(Drone.DroneState.IDLE);
        drones.add(drone1);

        // Mock the behavior of the droneService
        when(droneService.getAllDronesAsync()).thenReturn(CompletableFuture.completedFuture(drones));

        // Perform the HTTP GET request to test the endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/drones/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetDroneById() throws Exception {
        Long droneId = 105L;
        Drone drone = new Drone();
        drone.setId(droneId);

        when(droneService.getDroneByIdAsync(droneId)).thenReturn(CompletableFuture.completedFuture(drone));

        mockMvc.perform(MockMvcRequestBuilders.get("/drones/{id}", droneId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterDrone() throws Exception
    {
        Drone drone = new Drone();
        drone.setSerialNumber("12345");
        drone.setModel("Model XYZ");
        drone.setWeightLimit(10.0);
        drone.setBatteryCapacity(100);
        drone.setState(Drone.DroneState.IDLE);

        when(droneService.registerDroneAsync(any(Drone.class))).thenReturn(CompletableFuture.completedFuture(null));

        mockMvc.perform(MockMvcRequestBuilders.post("/drones/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"serialNumber\": \"12345\", \"model\": \"Model XYZ\", \"weightLimit\": 10.0, \"batteryCapacity\": 100, \"state\": \"IDLE\"}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateBatteryCapacity() throws Exception {
        Long droneId = 105L;
        int newBatteryCapacity = 90;

        when(droneService.updateDroneBatteryCapacityAsync(droneId, newBatteryCapacity)).thenReturn(CompletableFuture.completedFuture(null));

        mockMvc.perform(MockMvcRequestBuilders.put("/drones/{id}/updateBatteryCapacity?newBatteryCapacity={newBatteryCapacity}", droneId, newBatteryCapacity)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateState() throws Exception {
        Long droneId = 105L;
        Drone.DroneState newState = Drone.DroneState.LOADED;

        when(droneService.updateDroneStateAsync(droneId, newState)).thenReturn(CompletableFuture.completedFuture(null));

        mockMvc.perform(MockMvcRequestBuilders.put("/drones/{id}/updateState?newState={newState}", droneId, newState)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

