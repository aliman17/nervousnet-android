package ch.ethz.coss.nervousnet.vm.configuration;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by ales on 18/10/16.
 */
public class GeneralSensorConfiguration {

    protected long sensorID;
    protected String sensorName;
    protected ArrayList<String> parametersNames;
    protected ArrayList<String> parametersTypes;
    protected int dimensions;

    /**
     * Constructs an object that holds parameters for a general sensor.
     * @param sensorID - unique sensor identifier
     * @param sensorName - arbitrary sensor name
     * @param parametersNames - arbitrary persistent parameter names of the sensor
     * @param parametersTypes - arbitrary persistent parameter types of the sensor
     */
    public GeneralSensorConfiguration(long sensorID, String sensorName, ArrayList<String> parametersNames,
                                      ArrayList<String> parametersTypes) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.parametersNames = parametersNames;
        this.parametersTypes = parametersTypes;
        this.dimensions = parametersNames.size();
    }

    public long getSensorID() {
        return sensorID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public ArrayList<String> getParametersNames() {
        return parametersNames;
    }

    public ArrayList<String> getParametersTypes() {
        return parametersTypes;
    }

    public int getDimension() {
        return dimensions;
    }
    @Override
    public String toString() {
        return "ConfigurationClass{" +
                "sensorName='" + sensorName + '\'' +
                ", parametersNames=" + TextUtils.join(", ", parametersNames) +
                ", parametersTypes=" + TextUtils.join(", ", parametersTypes) +
                '}';
    }
}
