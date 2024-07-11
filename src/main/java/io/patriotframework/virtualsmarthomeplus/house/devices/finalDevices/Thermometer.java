package io.patriotframework.virtualsmarthomeplus.house.devices.finalDevices;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.patriot_framework.generator.dataFeed.NormalDistVariateDataFeed;
import io.patriotframework.virtualsmarthomeplus.DTOs.DeviceDTO;
import io.patriotframework.virtualsmarthomeplus.DTOs.ThermometerDTO;
import io.patriotframework.virtualsmarthomeplus.house.House;
import io.patriotframework.virtualsmarthomeplus.house.devices.Device;
import io.patriotframework.virtualsmarthomeplus.house.devices.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Thermometer extends Sensor {

    public static final String CELSIUS = "C";
    public static final String FAHRENHEIT = "F";
    //public static final String KELVIN = "K";     private  Float temperature;
    public static final String DEFAULT_UNIT = CELSIUS;
    private static final Logger LOGGER = LoggerFactory.getLogger(House.class);
    private final io.patriot_framework.generator.device.Device device;
    private String unit;

    /**
     * Creates new Thermometer with given label.
     *
     * @param label label creates identity of the Thermometer and is compared in the equals method
     * @throws IllegalArgumentException if given label is null or blank
     */
    public Thermometer(String label) {
        this(label, DEFAULT_UNIT);
    }

    /**
     * Creates new thermometer with given label and given unit.
     *
     * @param unit measuring unit
     * @param label label of the new thermometer
     * @throws IllegalArgumentException if given label is null or blank
     */
    @JsonCreator
    public Thermometer(String label, String unit) {
        super(label);
        device = new io.patriot_framework.generator.device.impl.basicSensors.Thermometer(
                getLabel(),
                new NormalDistVariateDataFeed(25, 1)
        );
        this.unit = unit;
    }

    /**
     * Creates new thermometer with the same values of the attributes as given thermometer except label.
     * Label of the new thermometer is given by parameter.
     *
     * @param origThermometer new thermometer copies values of given thermometer
     * @param newLabel        label of new device
     * @throws IllegalArgumentException if given label is null or blank
     */

    public Thermometer(Thermometer origThermometer, String newLabel) {
        this(origThermometer, newLabel, DEFAULT_UNIT);
        unit = origThermometer.unit;
    }

    /**
     * Creates new thermometer with the same values of the attributes as given thermometer except label.
     * Label of the new thermometer is given by parameter.
     *
     * @param origThermometer new thermometer copies values of given thermometer
     * @param newLabel        label of new device
     * @param unit            measuring unit
     * @throws IllegalArgumentException if given label is null or blank
     */
    public Thermometer(Thermometer origThermometer, String newLabel, String unit) {
        this(newLabel, unit);
        Thermometer.this.unit = origThermometer.unit;
    }

    /**
     * Method to get temperature value
     *
     * @return temperature value
     */
    public Float getTemperature() {
        return device.requestData().get(0).get(Double.class).floatValue();
    }

    /**
     * Method sets new measuring unit
     *
     * @param unit change measuring unit to unit
     */
    public void setUnit(String unit) {
        Thermometer.this.unit = unit;
        LOGGER.debug(String.format("Measuring unit changed to %s", unit));
    }

    /**
     * Method gets type of measuring unit
     *
     * @return measuring unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Device createWithSameAttributes(String newLabel) {
        return new Thermometer(this, newLabel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSameAttributes(Device thermometer) throws IllegalArgumentException {
        if (thermometer == null) {
            throw new IllegalArgumentException("Fireplace cannot be null");
        }
        if (getClass() != thermometer.getClass()) {
            throw new IllegalArgumentException("device must be of class Fireplace");
        }

        final Thermometer typedThermometer = (Thermometer) thermometer;

        if (isEnabled() != typedThermometer.isEnabled()) {
            return false;
        }
        return typedThermometer.unit.equals(unit);
    }
    /**
     * Updates the thermometer object with the values from provided DTO.
     *
     * @param deviceDTO thermometer DTO containing the updated values or null if value was not updated
     */
    public void update(DeviceDTO deviceDTO) {
        final ThermometerDTO thermometerDTO = (ThermometerDTO) deviceDTO;
        if (thermometerDTO.getUnit() != null) {
            this.setUnit(thermometerDTO.getUnit());
        }
        super.update(thermometerDTO);
    }
}
