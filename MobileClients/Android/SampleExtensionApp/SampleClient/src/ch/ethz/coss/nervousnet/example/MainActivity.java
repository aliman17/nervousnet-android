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
import android.widget.Toast;
import ch.ethz.coss.nervousnet.vm.BatteryReading;
import ch.ethz.coss.nervousnet.vm.NervousnetRemote;

public class MainActivity extends Activity {
	protected NervousnetRemote mService;
	ServiceConnection mServiceConnection;
	EditText count, battery_percent, battery_isCharging, battery_isUSB, battery_isAC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		count = (EditText) findViewById(R.id.count);
		battery_percent = (EditText) findViewById(R.id.battery_percent);
		battery_isCharging = (EditText) findViewById(R.id.battery_isCharging);
		battery_isUSB = (EditText) findViewById(R.id.battery_isUSB);
		battery_isAC = (EditText) findViewById(R.id.battery_isAC);
		
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
			
			BatteryReading reading = mService.getBatteryReading();

			if (reading != null){
				battery_percent.setText(reading.getBatteryPercent() * 100 + " %");
				battery_isCharging.setText(reading.isCharging()? "YES": "NO");
				battery_isUSB.setText(reading.isUSBCharging()? "YES": "NO");
				battery_isAC.setText(reading.isAcCharging()? "YES": "NO");
						
			}
			else{
				battery_percent.setText("Battery sensor not responding.");
			}
			
			
			
			

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int m_interval = 1000; // 1 seconds by default, can be changed later
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