/**
 * 
 */
package ch.ethz.coss.nervousnet.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ch.ethz.coss.nervousnet.sample.R;
import ch.ethz.coss.nervousnet.lib.SensorReading;

/**
 * @author prasad
 *
 */
public class BeaconsFragment extends BaseFragment {

	/**
	 * 
	 */
	public BeaconsFragment(int type) {
		super(type);
	}

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_beacons, container, false);
		
		return rootView;
	}


	/* (non-Javadoc)
	 * @see ch.ethz.coss.nervousnet.sample.BaseFragment#updateReadings(ch.ethz.coss.nervousnet.vm.SensorReading)
	 */
	@Override
	public void updateReadings(SensorReading reading) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleError(String message) {
		TextView status = (TextView) getActivity().findViewById(R.id.sensor_status);
		 status.setText(message);
		
	}
}
