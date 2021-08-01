package io.patriot_framework.virtual_smart_home.house;

import io.patriot_framework.virtual_smart_home.InitialApplication;
import io.patriot_framework.virtual_smart_home.house.device.Device;
import io.patriot_framework.virtual_smart_home.house.device.Fireplace;
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
public class HouseTest {

    House house = new House("house");
    Map<String, Device> devices = new ConcurrentHashMap<>();
    Fireplace fireplace = new Fireplace("fireplace");
    Fireplace fireplace2 = new Fireplace("fireplace2");

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
    public void getDevicesOfType() {
        devices.put("fireplace", fireplace);
        devices.put("fireplace2", fireplace2);
        house.setDevices(devices);
        ArrayList<Device> expectedList = new ArrayList<>(devices.values());
        assertThat(house.getDevicesOfType(Fireplace.class), equalTo(expectedList));
    }
}
