package io.patriotframework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.ThermometerDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.VirtualSmartHomePlusApplication;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.Thermometer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    @ContextConfiguration(classes = VirtualSmartHomePlusApplication.class)
    @SpringBootTest
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class ThermometerApiTest {

        private static final Logger LOGGER = LoggerFactory.getLogger(House.class);


        private final DTOMapper dtoMapper = new DTOMapper(new ModelMapper());
        @Autowired
        public House house = new House();
        private MockMvc mockMvc;
        @Autowired
        private WebApplicationContext wac;

        @BeforeAll
        public void setUp() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
            Thermometer thermometer = new Thermometer("thermometer1");
            thermometer.setEnabled(true);
            house.addDevice(thermometer);
        }

        @Test
        public void shouldFetchDevice() throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();
            ThermometerDTO ThermometerDTO = (ThermometerDTO) dtoMapper.map(house.getDevice("thermometer1"));
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/thermometer/thermometer1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(200))
                    .andReturn();

            String responseBody = result.getResponse().getContentAsString();
            ThermometerDTO responseDTO = objectMapper.readValue(responseBody, ThermometerDTO.class);

            assertEquals(ThermometerDTO, responseDTO);


        }

        @Test
        public void shouldPostThermometer() throws Exception {
            ThermometerDTO thermometer = new ThermometerDTO();
            thermometer.setLabel("thermometer2");

            thermometer.setEnabled(false);
            thermometer.setUnit("C");

            ObjectMapper objectMapper = new ObjectMapper();
            this.mockMvc.perform(post("/api/v0.1/house/device/thermometer/")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer)))
                    .andExpect(status().isOk());
            DeviceDTO deviceDTO = dtoMapper.map(house.getDevice("thermometer2"));
            assertEquals(thermometer, deviceDTO);

            ThermometerDTO thermometer1 = new ThermometerDTO();
            thermometer1.setLabel("thermometer3");
            thermometer1.setEnabled(false);
            thermometer1.setUnit("C");


            this.mockMvc.perform(post("/api/v0.1/house/device/thermometer/")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer1)))
                    .andExpect(status().isOk());
            deviceDTO = dtoMapper.map(house.getDevice("thermometer3"));
            assertEquals(thermometer1, deviceDTO);
        }

        @Test
        public void shouldUpdateThermometer() throws Exception {
            ThermometerDTO thermometer = new ThermometerDTO();
            thermometer.setLabel("thermometer1");
            thermometer.setUnit("F");

            ObjectMapper objectMapper = new ObjectMapper();
            this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/thermometer/thermometer1")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer))
                    )
                    .andExpect(status().isOk());
            ThermometerDTO ThermometerDTO2 = (ThermometerDTO) dtoMapper.map(house.getDevice("thermometer1"));
            assertEquals(ThermometerDTO2.getUnit(), "F");


            thermometer.setLabel("newThermometer1");
            thermometer.setUnit("F");

            this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/thermometer/newThermometer1")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer))
                    )
                    .andExpect(status().isOk());
            ThermometerDTO2 = (ThermometerDTO) dtoMapper.map(house.getDevice("newThermometer1"));
            assertEquals(ThermometerDTO2.getUnit(), "F");

            thermometer = new ThermometerDTO();
            thermometer.setLabel("thermometer1");
            thermometer.setEnabled(true);


            this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/thermometer/thermometer1")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer))
                    )
                    .andExpect(status().isOk());
            ThermometerDTO2 = (ThermometerDTO) dtoMapper.map(house.getDevice("thermometer1"));
            assertEquals(ThermometerDTO2.getEnabled(), true);

            thermometer = new ThermometerDTO();
            thermometer.setLabel("t2");
            thermometer.setUnit("F");

            this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/thermometer/thermometer1")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer))
                    )
                    .andExpect(status().is(400));
        }

        @Test
        public void shouldDeleteMapping() throws Exception {
            Thermometer thermometer = new Thermometer("1");
            house.addDevice(thermometer);
            this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v0.1/house/device/thermometer/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(204))
                    .andReturn();
        }

        @Test
        public void deviceAlreadyExists() throws Exception {
            ThermometerDTO thermometer = new ThermometerDTO();
            thermometer.setLabel("thermometer1");
            thermometer.setEnabled(false);

            ObjectMapper objectMapper = new ObjectMapper();
            this.mockMvc.perform(post("/api/v0.1/house/device/thermometer/")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer)))
                    .andExpect(status().is(409));
        }
        @Test
        public void wrongApiVersion() throws Exception {
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/house/device/thermometer/thermometer1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(404))
                    .andReturn();
        }

        @Test
        public void noSuchElementException() throws Exception {
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/thermometer/thermometer8")
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is(404))
                    .andReturn();
        }

        @Test
        public void wrongLabel() throws Exception {
            ObjectMapper objectMapper = new ObjectMapper();

            ThermometerDTO thermometer = new ThermometerDTO();
            thermometer.setLabel(null);
            thermometer.setEnabled(false);

            this.mockMvc.perform(post("/api/v0.1/house/device/thermometer/")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(thermometer)))
                    .andExpect(status().is(400));
        }
    }
