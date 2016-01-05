package ch.ethz.coss.nervousnet.example;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ch.ethz.coss.nervousnet.vm.AccelerometerReading;
import ch.ethz.coss.nervousnet.vm.BatteryReading;
import ch.ethz.coss.nervousnet.vm.LocationReading;
import ch.ethz.coss.nervousnet.vm.NervousnetRemote;

public class MainActivity extends Activity {
	protected NervousnetRemote mService;
	ServiceConnection mServiceConnection;
	TextView count, battery_percent, battery_isCharging, battery_isUSB, battery_isAC, location_values, temp, volt, health,
	accelX, accelY, accelZ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		count = (TextView) findViewById(R.id.count);
		battery_percent = (TextView) findViewById(R.id.battery_percent);
		battery_isCharging = (TextView) findViewById(R.id.battery_isCharging);
		battery_isUSB = (TextView) findViewById(R.id.battery_isUSB);
		battery_isAC = (TextView) findViewById(R.id.battery_isAC);
		temp = (TextView) findViewById(R.id.battery_temp);
		volt = (TextView) findViewById(R.id.battery_volt);
		health = (TextView) findViewById(R.id.battery_health);
		
		location_values = (TextView) findViewById(R.id.location_values);
		
		accelX = (TextView) findViewById(R.id.accel_x);
		accelY = (TextView) findViewById(R.id.accel_y);
		accelZ = (TextView) findViewById(R.id.accel_z);
		
		
		initConnection();
	}

	void initConnection() {
		mServiceConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				System.out.println("onServiceDisconnected");
				// TODO Auto-generated method stub
				mService = null;
				Toast.makeText(getApplicationContext(), "NervousnetRemote Service not connected", Toast.LENGTH_SHORT)
						.show();
				Log.d("NervousnetRemote", "Binding - Service disconnected");
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				System.out.println("onServiceConnected");

				// TODO Auto-generated method stub
				mService = NervousnetRemote.Stub.asInterface(service);
				System.out.println("onServiceConnected 1");

				try {
					count.setText(mService.getCounter() + "");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// try {
				// BatteryReading reading = mService.getBatteryReading();
				// System.out.println("onServiceConnected 2");
				// if(reading != null)
				// counter.setText(reading.getBatteryPercent()+"");
				// else
				// counter.setText("Null object returned");
				// } catch (RemoteException e) {
				// // TODO Auto-generated catch block
				// System.out.println("Exception thrown here");
				// e.printStackTrace();
				// }
				// m_handler.post(m_statusChecker);

				startRepeatingTask();
				Toast.makeText(getApplicationContext(), "Nervousnet Remote Service Connected", Toast.LENGTH_SHORT)
						.show();
				Log.d("IRemote", "Binding is done - Service connected");
			}
		};
		if (mService == null) {
			Intent it = new Intent();
			// it.setPackage("ch.ethz.coss.nervousnet.service");
			it.setClassName("ch.ethz.coss.nervousnet", "ch.ethz.coss.nervousnet.vm.NervousnetVMService");
			// it.setAction("ch.ethz.nervousnet.VM");

			try {

				Boolean flag = bindService(it, mServiceConnection, 0);
				Log.d("DEBUG", flag.toString()); // will return "true"
				if (!flag)
					Toast.makeText(MainActivity.this,
							"Please check if the Nervousnet Remote Service is installed and running.",
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(MainActivity.this, "Nervousnet Remote is running fine", Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				e.printStackTrace();
				Log.e("DEBUG", "not able to bind ! ");
			}

			// //binding to remote service
			// boolean flag = bindService(it, mServiceConnection,
			// Service.BIND_AUTO_CREATE);
			//
			//
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void toastToScreen(String msg, boolean lengthLong) {
		int toastLength = lengthLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
		Toast.makeText(getApplicationContext(), msg, toastLength).show();
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	public void updateStatus() {
		try {
			System.out.println("Get Battery Reading");
			
			count.setText(mService.getCounter()+"");
			
			BatteryReading breading = mService.getBatteryReading();
			LocationReading lreading = mService.getLocationReading();
			AccelerometerReading aReading = mService.getAccelerometerReading();
			
			if (breading != null){
				System.out.println("Set Battery Reading");
				battery_percent.setText("Charge Remaining = "+breading.getPercent() * 100 + " %");
				battery_isCharging.setText("Charging: "+(breading.isCharging()? "YES": "NO"));
				battery_isUSB.setText("USB Charging: "+(breading.getCharging_type() == 1? "YES": "NO"));
				battery_isAC.setText("AC Charging: "+(breading.getCharging_type() == 2? "YES": "NO"));
				temp.setText("Temperature: "+breading.getTemp()+" C");
				volt.setText("Voltage: "+breading.getVolt()+" mV");
				health.setText("Health Status: "+breading.getHealthString());
				
			}
			else{
				System.out.println("Set Location Reading");
				battery_percent.setText("Battery sensor not responding.");
			}
			
			if(lreading != null) {
				
				location_values.setText(lreading.toString());
			}
			
			
			if(aReading != null) {
				accelX.setText("X: "+aReading.getX());
				accelY.setText("Y: "+aReading.getY());
				accelZ.setText("Z: "+aReading.getZ());
				
			}
			
			
			

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int m_interval = 500; // 1 seconds by default, can be changed later
	private Handler m_handler = new Handler();

	Runnable m_statusChecker = new Runnable() {
		@Override
		public void run() {

			updateStatus(); // this function can change value of m_interval.
		
			m_handler.postDelayed(m_statusChecker, m_interval);
		}
	};

	void startRepeatingTask() {
		m_statusChecker.run();
	}

	void stopRepeatingTask() {
		m_handler.removeCallbacks(m_statusChecker);
	}
}