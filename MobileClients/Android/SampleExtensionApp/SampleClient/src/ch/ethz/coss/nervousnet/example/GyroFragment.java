/**
 * 
 */
package ch.ethz.coss.nervousnet.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.ethz.coss.nervousnet.vm.SensorReading;

/**
 * @author prasad
 *
 */
public class GyroFragment extends BaseFragment {

	/**
	 * 
	 */
	public GyroFragment(int type) {
		super(type);
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_gyro, container, false);
		
		return rootView;
	}

	/* (non-Javadoc)
	 * @see ch.ethz.coss.nervousnet.example.BaseFragment#updateReadings(ch.ethz.coss.nervousnet.vm.SensorReading)
	 */
	@Override
	public void updateReadings(SensorReading reading) {
		// TODO Auto-generated method stub
		
	}

}
