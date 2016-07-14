/*******************************************************************************
 *
 *  *     Nervousnet - a distributed middleware software for social sensing. 
 *  *      It is responsible for collecting and managing data in a fully de-centralised fashion
 *  *
 *  *     Copyright (C) 2016 ETH Zürich, COSS
 *  *
 *  *     This file is part of Nervousnet Framework
 *  *
 *  *     Nervousnet is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     Nervousnet is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with NervousNet. If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 *  * 	Contributors:
 *  * 	Prasad Pulikal - prasad.pulikal@gess.ethz.ch  -  Initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package ch.ethz.coss.nervousnet.hub.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ch.ethz.coss.nervousnet.hub.R;
import ch.ethz.coss.nervousnet.hub.ui.views.DecibelMeterView;
import ch.ethz.coss.nervousnet.lib.ErrorReading;
import ch.ethz.coss.nervousnet.lib.LibConstants;
import ch.ethz.coss.nervousnet.lib.NoiseReading;
import ch.ethz.coss.nervousnet.lib.SensorReading;
import ch.ethz.coss.nervousnet.vm.NNLog;
import ch.ethz.coss.nervousnet.vm.NervousnetVMConstants;

public class NoiseFragment extends BaseFragment {
	private DecibelMeterView noiseView;
	private float db;
	private float newDb;
	public NoiseFragment() {
		super(LibConstants.SENSOR_NOISE);
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_noise, container, false);
		noiseView = (DecibelMeterView)rootView.findViewById(R.id.noiseVizView);
		return rootView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.ethz.coss.nervousnet.sample.BaseFragment#updateReadings(ch.ethz.coss.
	 * nervousnet.vm.SensorReading)
	 */
	@Override
	public void updateReadings(SensorReading reading) {

		NNLog.d("NoiseFragment", "Inside updateReadings");
		db = ((NoiseReading) reading).getdbValue();
		TextView dbTV = (TextView) getActivity().findViewById(R.id.dbValue);
		
		if(newDb < Math.round(db) )
			newDb++;
		else if(newDb > Math.round(db))
			newDb--;
		else 
			newDb = db;
			
		dbTV.setText("" + Math.round(db));
		noiseView.setDecibleValue(newDb);
	}

	final private int REQUEST_CODE_ASK_PERMISSIONS_NOISE = 2;

	@Override
	public void handleError(ErrorReading reading) {
		NNLog.d("NoiseFragment", "handleError called");
		TextView status = (TextView) getActivity().findViewById(R.id.sensor_status_noise);
		status.setText("Error: code = " + reading.getErrorCode() + ", message = " + reading.getErrorString());

//		Toast.makeText(getActivity(),"Error Code = "+Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();

//		if (Build.VERSION.SDK_INT >= 23) {
			if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
				if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECORD_AUDIO)) {
					ActivityCompat.requestPermissions(
							getActivity(),
							new String[]{Manifest.permission.RECORD_AUDIO},
							REQUEST_CODE_ASK_PERMISSIONS_NOISE
					);
				}
			}
			return;
//		}


	}
}
