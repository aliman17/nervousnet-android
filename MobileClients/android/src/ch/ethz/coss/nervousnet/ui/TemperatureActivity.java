package ch.ethz.coss.nervousnet.ui;

import android.hardware.Sensor;
import android.os.Bundle;
import ch.ethz.coss.nervousnet.R;

public class TemperatureActivity extends BaseSensorActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_sensor);
		
		
		setSensorStatus(Sensor.TYPE_AMBIENT_TEMPERATURE);
	}

}
