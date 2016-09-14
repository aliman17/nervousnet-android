package data;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.ethz.coss.nervousnet.lib.AccelerometerReading;
import ch.ethz.coss.nervousnet.lib.BatteryReading;
import ch.ethz.coss.nervousnet.lib.ErrorReading;
import ch.ethz.coss.nervousnet.lib.LibConstants;
import ch.ethz.coss.nervousnet.lib.LightReading;
import ch.ethz.coss.nervousnet.lib.NervousnetSensorDataListener;
import ch.ethz.coss.nervousnet.lib.NervousnetServiceConnectionListener;
import ch.ethz.coss.nervousnet.lib.NervousnetServiceController;
import ch.ethz.coss.nervousnet.lib.NoiseReading;
import ch.ethz.coss.nervousnet.lib.RemoteCallback;
import ch.ethz.coss.nervousnet.lib.SensorReading;

/**
 * Created by ales on 28/06/16.
 *
 * Nervousnet queries the data in Nervousnet application. This class is source of the data.
 */
public class Nervousnet implements NervousnetServiceConnectionListener, NervousnetSensorDataListener {

    private enum SensorType{
        ACC,
        BATTERY,
        GYRO,
        LIGHT,
        LOC,
        NOISE
    }

    // We need context to get connections and sensor data
    private Context context;

    // Connection to the service
    NervousnetServiceController nervousnetServiceController;

    // Constructor
    public Nervousnet(Context context){
        this.context = context;
    }

    // Connect
    public void connect(){
        Log.d("NERVOUSNET", "Connecting ...");
        nervousnetServiceController = new NervousnetServiceController(this.context, this);
        Log.d("NERVOUSNET", "Connecting2 ..." + nervousnetServiceController);
        try {
            nervousnetServiceController.connect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void testAverage() throws RemoteException {
        // TEST
        List list = nervousnetServiceController.getAverage(0);
        for (Object o : list)
            Log.d("NERVOSNET-TEST", "Average acc " + o);

        list = nervousnetServiceController.getAverage(1);
        for (Object o : list)
            Log.d("NERVOSNET-TEST", "Average bat " + o);

        list = nervousnetServiceController.getAverage(2);
        for (Object o : list)
            Log.d("NERVOSNET-TEST", "Average gyro " + o);

        list = nervousnetServiceController.getAverage(5);
        for (Object o : list)
            Log.d("NERVOSNET-TEST", "Average noise " + o);

    }


    /////////////////////////////////////////////////////////////////////////
    // CALLBACK
    /////////////////////////////////////////////////////////////////////////

    class Callback extends RemoteCallback.Stub {
        private SensorType sType;
        private List list;

        public Callback(SensorType sType){
            this.sType = sType;
        }

        @Override
        public void success(final List<SensorReading> list) throws RemoteException {
            Log.d("NERVOUSNET CALLBACK", sType + " callback success " + list.size());
            this.list = list;
        }

        @Override
        public void failure(final ErrorReading reading) throws RemoteException {
            Log.d("NERVOUSNET CALLBACK", sType + "callback failure "+reading.getErrorString());
        }

        public List getList() { return this.list; }
    }


    /////////////////////////////////////////////////////////////////////////
    // OVERWRITE
    /////////////////////////////////////////////////////////////////////////

    @Override
    public void onSensorDataReady(SensorReading reading) {

    }

    @Override
    public void onServiceConnected() {

    }

    @Override
    public void onServiceDisconnected() {

    }

    @Override
    public void onServiceConnectionFailed(ErrorReading errorReading) {

    }

}
