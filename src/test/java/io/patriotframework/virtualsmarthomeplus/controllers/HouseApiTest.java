package io.patriotframework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriotframework.virtualsmarthomeplus.DTOs.HouseDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.VirtualSmartHomePlusApplication;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = VirtualSmartHomePlusApplication.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HouseApiTest {
    private final DTOMapper dtoMapper = new DTOMapper(new ModelMapper());
    private MockMvc mockMvc;
    @Autowired
    public House house = new House();
    @Autowired
    private WebApplicationContext wac;
    @BeforeAll
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    @Order(1)
    public void shouldFetchHouse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.house.addDevice(new RGBLight("rg1"));
        HouseDTO houseDTO = dtoMapper.map(house);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        String responseBody = result.getResponse().getContentAsString();
        HouseDTO responseDTO = objectMapper.readValue(responseBody,HouseDTO.class);

        for (int i=0;i<responseDTO.getDevices().size();i++) {
            assertEquals(responseDTO.getDevices().get(i),houseDTO.getDevices().get(i));
        }
    }
    @Test
    public void wrongApiVersion() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/house/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }
}
