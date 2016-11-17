package ch.ethz.coss.nervousnet.vm.configuration;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import ch.ethz.coss.nervousnet.vm.nervousnet.database.NoSuchElementInDBException;

/**
 * Created by ales on 17/11/16.
 */
public class ConfigurationManager implements iConfigurationManager {

    HashMap<Long, GeneralSensorConfiguration> configMap;
    StateDBManager stateDBManager;

    public ConfigurationManager(Context context) {
        this.configMap = new HashMap<>();
        this.stateDBManager = new StateDBManager(context);
        JsonConfigurationLoader loader = new JsonConfigurationLoader(context);
        ArrayList<BasicSensorConfiguration> confList = loader.load();
        for (BasicSensorConfiguration conf : confList){
            configMap.put(conf.getSensorID(), conf);
            try {
                int state = stateDBManager.getSensorState(conf.getSensorID());
                conf.setState(state);
            } catch (NoSuchElementInDBException e) {
                stateDBManager.storeSensorState(conf.getSensorID(), conf.getState());
            }
        }
    }

    @Override
    public ArrayList<GeneralSensorConfiguration> getAllConfigurations() {
        ArrayList<GeneralSensorConfiguration> returnList = new ArrayList<>();
        returnList.addAll(configMap.values());
        return returnList;
    }

    @Override
    public GeneralSensorConfiguration getConfiguration(long sensorID) throws NoSuchElementException {
        if (configMap.containsKey(sensorID))
            return configMap.get(sensorID);
        else
            throw new NoSuchElementException("Sensor " + sensorID + " has not been configured.");
    }

    @Override
    public int getSensorState(long sensorID) throws NoSuchElementException{
        if (configMap.containsKey(sensorID))
            return ((BasicSensorConfiguration)configMap.get(sensorID)).getState();
        else
            throw new NoSuchElementException("Sensor " + sensorID + " has not been configured.");
    }

    @Override
    public void setSensorState(long sensorID, int state) {
        if (configMap.containsKey(sensorID)) {
            stateDBManager.storeSensorState(sensorID, state);
            ((BasicSensorConfiguration) configMap.get(sensorID)).setState(state);
        }
        else
            throw new NoSuchElementException("Sensor " + sensorID + " has not been configured.");
    }

    @Override
    public int getNervousnetState() throws NoSuchElementInDBException {
        return stateDBManager.getNervousnetState();
    }

    @Override
    public void setNervousnetState(int state) throws NoSuchElementException {
        stateDBManager.storeNervousnetState(state);
    }

}
