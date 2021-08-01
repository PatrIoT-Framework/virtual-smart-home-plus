package io.patriot_framework.virtual_smart_home.house.device;

import io.patriot_framework.virtual_smart_home.house.Fireplace;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FirePlaceTests {

    Fireplace fireplace = new Fireplace("fireplace");

    @Test
    public void fireUp() {
        fireplace.fireUp();
        assertThat(fireplace.isEnabled(), equalTo(true));
    }

    @Test
    public void extinguish() {
        fireplace.extinguish();
        assertThat(fireplace.isEnabled(), equalTo(false));
    }
}
