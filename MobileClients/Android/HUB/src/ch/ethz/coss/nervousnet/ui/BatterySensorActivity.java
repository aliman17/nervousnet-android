package ch.ethz.coss.nervousnet.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ch.ethz.coss.nervousnet.R;
import ch.ethz.coss.nervousnet.sensors.BatterySensor;
import ch.ethz.coss.nervousnet.sensors.BatterySensor.BatterySensorListener;
import ch.ethz.coss.nervousnet.vm.BatteryReading;
import ch.ethz.coss.nervousnet.vm.NervousnetVMService;

public class BatterySensorActivity extends BaseSensorActivity implements BatterySensorListener {
	ViewGroup vg;
	TextView battery_percent, battery_isCharging;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_batt_sensor);
		vg = (RelativeLayout) findViewById(R.id.layout_battery_parent);
		battery_percent = (TextView) findViewById(R.id.battValueTF);
		battery_isCharging = (TextView) findViewById(R.id.statusTF);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.ethz.coss.nervousnet.sensors.BatterySensor.BatteryListener#
	 * batterySensorDataReady(ch.ethz.coss.nervousnet.vm.BatteryReading)
	 */
	@Override
	public void batterySensorDataReady(BatteryReading reading) {
		Log.d("BatterySensorActivity",
				"Inside BatterySensorActivity  Data Ready called - charging status = " + reading.isCharging());
				// TODO Auto-generated method stub

		// battery_percent.setText("Battery level = "+reading.getPercent()
		// +"%");
		// battery_isCharging.setText("Charging status =
		// "+reading.isCharging());

		update();
	}

	@Override
	protected void onResume() {
		super.onResume();

		addListener();
		Log.d("BatterySensorActivity", "onResume() - addListener");
		// The activity has become visible (it is now "resumed").
	}

	@Override
	protected void onPause() {
		super.onPause();
		removeListener();

		Log.d("BatterySensorActivity", "onPause() - removeListener");
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onStop() {
		super.onStop();
		removeListener();
		Log.d("BatterySensorActivity", "onStop() - removeListener");
		// The activity is no longer visible (it is now "stopped")
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		removeListener();
		Log.d("BatterySensorActivity", "onDestroy() - removeListener");

		// The activity is about to be destroyed.
	}

	/**
	* 
	*/
	private void addListener() {
		// TODO Auto-generated method stub
		Log.d("BatterySensorActivity", "Inside BatterySensorActivity addListener- ");

		System.out.println("before adding listener");
		BatterySensor.getInstance(BatterySensorActivity.this).addListener(BatterySensorActivity.this);
	}

	/**
	 * 
	 */
	private void removeListener() {
		// TODO Auto-generated method stub
		System.out.println("before adding listener");
		BatterySensor.getInstance(BatterySensorActivity.this).removeListener(BatterySensorActivity.this);

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

	private void update() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				battery_percent.setText("Battery level = "
						+ ((BatteryReading) BatterySensor.getInstance(BatterySensorActivity.this).getReading())
								.getPercent()
						+ "%");
				battery_isCharging.setText("Charging status = "
						+ ((BatteryReading) BatterySensor.getInstance(BatterySensorActivity.this).getReading())
								.isCharging());
				battery_percent.invalidate();
				battery_isCharging.invalidate();
				vg.invalidate();
			}
		});
	}

}
