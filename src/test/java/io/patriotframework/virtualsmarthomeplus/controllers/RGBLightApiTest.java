package io.patriotframework.virtualsmarthomeplus.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.RGBLightDTO;
import io.patriotframework.virtualsmarthomeplus.Mapper.DTOMapper;
import io.patriotframework.virtualsmarthomeplus.VirtualSmartHomePlusApplication;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices.RGBLight;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = VirtualSmartHomePlusApplication.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RGBLightApiTest {



    private final DTOMapper dtoMapper = new DTOMapper(new ModelMapper());
    @Autowired
    public House house = new House();
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @BeforeAll
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RGBLight rgb = new RGBLight("rgb1");
        rgb.setRed(5);
        house.addDevice(rgb);
    }

    @Test
    public void shouldFetchDevice() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RGBLightDTO rgbLightDTO = (RGBLightDTO) dtoMapper.map(house.getDevice("rgb1"));
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/RGBLight/rgb1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        RGBLightDTO responseDTO = objectMapper.readValue(responseBody, RGBLightDTO.class);

        assertEquals(5, rgbLightDTO.getRed());
        assertEquals(false, rgbLightDTO.getSwitchedOn());

        assertEquals(rgbLightDTO, responseDTO);
    }

    @Test
    public void shouldPostRGB() throws Exception {
        RGBLightDTO rgb = new RGBLightDTO();
        rgb.setLabel("rgb2");
        rgb.setSwitchedOn(true);
        rgb.setBlue(0);
        rgb.setGreen(0);
        rgb.setRed(0);
        rgb.setEnabled(false);

        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/api/v0.1/house/device/RGBLight/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb)))
                .andExpect(status().isOk());
        DeviceDTO deviceDTO = dtoMapper.map(house.getDevice("rgb2"));
        assertEquals(rgb, deviceDTO);

        RGBLightDTO rgb1 = new RGBLightDTO();
        rgb1.setLabel("rgb3");
        rgb1.setSwitchedOn(true);
        rgb1.setBlue(0);
        rgb1.setGreen(0);
        rgb1.setRed(0);
        rgb1.setEnabled(false);

        this.mockMvc.perform(post("/api/v0.1/house/device/RGBLight/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb1)))
                .andExpect(status().isOk());
        deviceDTO = dtoMapper.map(house.getDevice("rgb3"));
        assertEquals(rgb1, deviceDTO);
    }

    @Test
    public void shouldUpdateRGB() throws Exception {
        RGBLightDTO rgb = new RGBLightDTO();
        rgb.setLabel("rgb1");
        rgb.setRed(25);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/RGBLight/rgb1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb))
                )
                .andExpect(status().isOk());
        RGBLightDTO rgbLightDTO2 = (RGBLightDTO) dtoMapper.map(house.getDevice("rgb1"));
        assertEquals(rgbLightDTO2.getRed(), 25);
        assertEquals(rgbLightDTO2.getBlue(), 0);

        rgb.setLabel("newRgb1");
        rgb.setRed(25);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/RGBLight/newRgb1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb))
                )
                .andExpect(status().isOk());
        rgbLightDTO2 = (RGBLightDTO) dtoMapper.map(house.getDevice("newRgb1"));
        assertEquals(rgbLightDTO2.getRed(), 25);
        assertEquals(rgbLightDTO2.getBlue(), 0);

        rgb = new RGBLightDTO();
        rgb.setLabel("rgb1");
        rgb.setSwitchedOn(true);
        rgb.setEnabled(true);
        rgb.setGreen(40);
        rgb.setBlue(40);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/RGBLight/rgb1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb))
                )
                .andExpect(status().isOk());
        rgbLightDTO2 = (RGBLightDTO) dtoMapper.map(house.getDevice("rgb1"));
        assertEquals(rgbLightDTO2.getRed(), 25);
        assertEquals(rgbLightDTO2.getBlue(), 40);
        assertEquals(rgbLightDTO2.getGreen(), 40);
        assertEquals(rgbLightDTO2.getSwitchedOn(), true);
        assertEquals(rgbLightDTO2.getEnabled(), true);

        rgb = new RGBLightDTO();
        rgb.setLabel("r2");

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v0.1/house/device/RGBLight/rgb1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb))
                )
                .andExpect(status().is(400));
    }

    @Test
    public void shouldDeleteMapping() throws Exception {
        RGBLight rgb = new RGBLight("1");
        house.addDevice(rgb);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v0.1/house/device/RGBLight/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void deviceAlreadyExists() throws Exception {
        RGBLightDTO rgb = new RGBLightDTO();
        rgb.setLabel("rgb1");
        rgb.setSwitchedOn(true);
        rgb.setBlue(0);
        rgb.setGreen(0);
        rgb.setRed(0);
        rgb.setEnabled(false);

        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/api/v0.1/house/device/RGBLight/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb)))
                .andExpect(status().is(409));
    }
    @Test
    public void wrongApiVersion() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0/house/device/RGBLight/rgb1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void noSuchElementException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v0.1/house/device/RGBLight/rgb8")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    public void wrongLabel() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        RGBLightDTO rgb = new RGBLightDTO();
        rgb.setLabel(null);
        rgb.setSwitchedOn(true);
        rgb.setBlue(0);
        rgb.setGreen(0);
        rgb.setRed(0);
        rgb.setEnabled(false);

        this.mockMvc.perform(post("/api/v0.1/house/device/RGBLight/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(rgb)))
                .andExpect(status().is(400));
    }
}

