package io.patriotframework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.FireplaceDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.VirtualSmartHomePlusApplication;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace.EXTINGUISHED;
import static io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Fireplace.ON_FIRE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ContextConfiguration(classes = VirtualSmartHomePlusApplication.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FireplaceApiTest {


    private final DTOMapper dtoMapper = new DTOMapper(new ModelMapper());
    @Autowired
    public House house = new House();
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeAll
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        Fireplace fireplace = new Fireplace("fireplace1");
        fireplace.setEnabled(true);
        house.addDevice(fireplace);
    }

    @Test
    public void shouldFetchDevice() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        FireplaceDTO FireplaceDTO = (FireplaceDTO) dtoMapper.map(house.getDevice("fireplace1"));
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/fireplace/fireplace1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        FireplaceDTO responseDTO = objectMapper.readValue(responseBody, FireplaceDTO.class);

        assertEquals(EXTINGUISHED, FireplaceDTO.getStatus());

        assertEquals(FireplaceDTO, responseDTO);
    }

    @Test
    public void shouldPostFireplace() throws Exception {
        FireplaceDTO fireplace = new FireplaceDTO();
        fireplace.setLabel("fireplace2");
        fireplace.setStatus(EXTINGUISHED);
        fireplace.setEnabled(false);

        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/api/v0.1/house/device/fireplace/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace)))
                .andExpect(status().isOk());
        DeviceDTO deviceDTO = dtoMapper.map(house.getDevice("fireplace2"));
        assertEquals(fireplace, deviceDTO);

        FireplaceDTO fireplace1 = new FireplaceDTO();
        fireplace1.setLabel("fireplace3");
        fireplace1.setStatus(EXTINGUISHED);
        fireplace1.setEnabled(false);

        this.mockMvc.perform(post("/api/v0.1/house/device/fireplace/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace1)))
                .andExpect(status().isOk());
        deviceDTO = dtoMapper.map(house.getDevice("fireplace3"));
        assertEquals(fireplace1, deviceDTO);
    }

    @Test
    public void shouldUpdateFireplace() throws Exception {
        FireplaceDTO fireplace = new FireplaceDTO();
        fireplace.setLabel("fireplace1");
        fireplace.setStatus(ON_FIRE);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/fireplace/fireplace1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace))
                )
                .andExpect(status().isOk());
        FireplaceDTO fireplaceDTO2 = (FireplaceDTO) dtoMapper.map(house.getDevice("fireplace1"));
        assertEquals(fireplaceDTO2.getStatus(),ON_FIRE);

        fireplace.setLabel("newFireplace1");
        fireplace.setStatus(ON_FIRE);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/fireplace/newFireplace1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace))
                )
                .andExpect(status().isOk());
        fireplaceDTO2 = (FireplaceDTO) dtoMapper.map(house.getDevice("newFireplace1"));
        assertEquals(fireplaceDTO2.getStatus(),ON_FIRE);

        fireplace = new FireplaceDTO();
        fireplace.setLabel("fireplace1");
        fireplace.setStatus(EXTINGUISHED);
        fireplace.setEnabled(true);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/fireplace/fireplace1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace))
                )
                .andExpect(status().isOk());
        fireplaceDTO2 = (FireplaceDTO) dtoMapper.map(house.getDevice("fireplace1"));
        assertEquals(fireplaceDTO2.getStatus(),EXTINGUISHED);
        assertEquals(fireplaceDTO2.getEnabled(), true);

        fireplace = new FireplaceDTO();
        fireplace.setLabel("2");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/fireplace/fireplace1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace))
                )
                .andExpect(status().is(400));
    }

    @Test
    public void shouldDeleteMapping() throws Exception {
        Fireplace fireplace = new Fireplace("1");
        house.addDevice(fireplace);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v0.1/house/device/fireplace/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void deviceAlreadyExists() throws Exception {
        FireplaceDTO fireplace = new FireplaceDTO();
        fireplace.setLabel("fireplace1");
        fireplace.setEnabled(false);

        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/api/v0.1/house/device/fireplace/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace)))
                .andExpect(status().is(409));
    }

    @Test
    public void wrongApiVersion() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/house/device/fireplace/thermometer1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void noSuchElementException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/fireplace/fireplace8")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void wrongLabel() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        FireplaceDTO fireplace = new FireplaceDTO();
        fireplace.setLabel(null);
        fireplace.setEnabled(false);

        this.mockMvc.perform(post("/api/v0.1/house/device/fireplace/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(fireplace)))
                .andExpect(status().is(400));
    }
}
