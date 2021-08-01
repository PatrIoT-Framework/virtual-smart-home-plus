package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.InitialApplication;
import io.patriot_framework.virtual_smart_home.house.device.Device;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = InitialApplication.class)
public class BasicHouseTests {

    House house = new House("test-house");
    Map<String, Device> devices = new ConcurrentHashMap<>();
    Fireplace fireplace = new Fireplace("fireplace");
    Fireplace fireplace2 = new Fireplace("fireplace2");

    @Test
    public void houseNameTests() {
        assertThat(house.getHouseName(), equalTo("test-house"));
        house.setHouseName("new-house");
        assertThat(house.getHouseName(), equalTo("new-house"));
    }

    @Test
    public void addDevice() {
        house.addDevice("fireplace", fireplace);
        assertThat(house.getDevice("fireplace"), equalTo(fireplace));
    }

    @Test
    public void removeDevice() {
        devices.put("fireplace", fireplace);
        house.setDevices(devices);
        house.removeDevice("fireplace");
        assertThat(house.getDevice("fireplace"), equalTo(null));
    }

    @Test
    public void setDevices() {
        devices.put("fireplace", fireplace);
        devices.put("fireplace2", fireplace2);
        house.setDevices(devices);
        assertThat(house.getDevice("fireplace"), equalTo(fireplace));
        assertThat(house.getDevice("fireplace2"), equalTo(fireplace2));
    }

    @Test
    public void getAllDevices() {
        devices.put("fireplace", fireplace);
        devices.put("fireplace2", fireplace2);
        house.setDevices(devices);
        ArrayList<Device> expectedList = new ArrayList<Device>(devices.values());
        assertThat(house.getAllDevices(Fireplace.class), equalTo(expectedList));
    }
}
