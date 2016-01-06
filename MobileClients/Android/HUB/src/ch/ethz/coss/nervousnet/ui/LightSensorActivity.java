package ch.ethz.coss.nervousnet.ui;

import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import ch.ethz.coss.nervousnet.R;
import ch.ethz.coss.nervousnet.sensors.AccelerometerSensor;
import ch.ethz.coss.nervousnet.sensors.LightSensor;
import ch.ethz.coss.nervousnet.sensors.LightSensor.LightSensorListener;
import ch.ethz.coss.nervousnet.vm.AccelerometerReading;
import ch.ethz.coss.nervousnet.vm.LightReading;

public class LightSensorActivity extends BaseSensorActivity implements LightSensorListener{

	
	TextView lux_val;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_light_sensor);
		setSensorStatus(Sensor.TYPE_LIGHT);
		lux_val = (TextView) findViewById(R.id.statusTF);
	}

	
	
	
	
	@Override
	public void lightSensorDataReady(LightReading reading) {
		Log.d("LightSensorActivity", "lightSensorDataReady called");

		update(reading);
	}

	@Override
	protected void onResume() {
		super.onResume();

		addListener();
		Log.d("LightSensorActivity", "onResume() - addListener");
		// The activity has become visible (it is now "resumed").
	}

	@Override
	protected void onPause() {
		super.onPause();
		removeListener();

		Log.d("LightSensorActivity", "onPause() - removeListener");
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onStop() {
		super.onStop();
		removeListener();
		Log.d("LightSensorActivity", "onStop() - removeListener");
		// The activity is no longer visible (it is now "stopped")
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		removeListener();
		Log.d("LightSensorActivity", "onDestroy() - removeListener");

		// The activity is about to be destroyed.
	}

	/**
	* 
	*/
	private void addListener() {
		// TODO Auto-generated method stub
		Log.d("LightSensorActivity", "Inside LightSensorActivity addListener ");

		LightSensor.getInstance().addListener(LightSensorActivity.this);
	}

	/**
	 * 
	 */
	private void removeListener() {
		// TODO Auto-generated method stub
		Log.d("LightSensorActivity", "Inside LightSensorActivity removeListener ");
		LightSensor.getInstance().removeListener(LightSensorActivity.this);

	}
	// private int m_interval = 1000;
	// private Handler m_handler = new Handler();
	// Runnable updater = new Runnable() {
	// @Override
	// public void run() {
	// runOnUiThread(new Runnable() {
	// public void run() {
	// update();
	// }
	// });
	// m_handler.postDelayed(updater, m_interval);
	// }
	// };

	private void update(final LightReading reading) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				lux_val.setText(reading.getLuxValue()+" lux");
			}
		});
	}

}
