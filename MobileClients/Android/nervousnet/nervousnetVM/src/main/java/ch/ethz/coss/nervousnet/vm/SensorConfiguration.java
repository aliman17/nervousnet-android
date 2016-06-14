package ch.ethz.coss.nervousnet.vm;

import android.content.Context;
import android.content.SharedPreferences;

public class SensorConfiguration {

	private Context context;

	private static SensorConfiguration sensorConfiguration;

	public static SensorConfiguration getInstance(Context context) {
		if (sensorConfiguration == null) {
			sensorConfiguration = new SensorConfiguration(context);
		}
		return sensorConfiguration;
	}

	private SensorConfiguration(Context context) {
		this.context = context;
	}

	public SensorCollectStatus getInitialSensorCollectStatus(int sensorType) {
		SharedPreferences settings = context.getSharedPreferences(NervousnetVMConstants.SENSOR_PREFS, 0);
		boolean doMeasure = settings.getBoolean(sensorType + "_doMeasure", true);
		boolean doShare = settings.getBoolean(sensorType + "_doShare", true);

		int measureInterval = context.getSharedPreferences(NervousnetVMConstants.SENSOR_FREQ, 0)
				.getInt(sensorType + "_freqValue", 2) * 2000;

		NNLog.d("SensorConfiguration", "MeasureInterval = " + measureInterval);
		// int measureInterval = settings.getInt(Long.toHexString(sensorID) +
		// "_measureInterval", 30 * 1000);

		long measureDuration = settings.getLong(sensorType + "_measureDuration", -1);
		int collectAmount = settings.getInt(sensorType + "_collectAmount", 1);
		SensorCollectStatus scs = new SensorCollectStatus(sensorType, doMeasure, doShare, measureInterval,
				measureDuration, collectAmount);
		return scs;
	}

}
