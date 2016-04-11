/*******************************************************************************
 *
 *  *     Nervousnet - a distributed middleware software for social sensing. 
 *  *     It is responsible for collecting and managing data in a fully de-centralised fashion
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
package ch.ethz.coss.nervousnet.sample.fragments;

import android.os.Bundle;
import ch.ethz.coss.nervousnet.lib.AccelerometerReading;
import ch.ethz.coss.nervousnet.lib.SensorReading;
import ch.ethz.coss.nervousnet.sample.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AccelFragment extends BaseFragment {
	View rootView;

	TextView x_value;
	TextView y_value;
	TextView z_value;
	public AccelFragment() {
	}

	public AccelFragment(int type) {
		super(type);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 rootView = inflater.inflate(R.layout.fragment_accel, container, false);
			 x_value = (TextView) rootView.findViewById(R.id.accel_x);
			 y_value = (TextView) rootView.findViewById(R.id.accel_y);
			 z_value = (TextView) rootView.findViewById(R.id.accel_z);
		return rootView;
	}

	@Override
	public void updateReadings(SensorReading reading) {
		Log.d("AccelFragment", "Inside updateReadings, X = " + ((AccelerometerReading) reading).getX());

		x_value.setText("" + ((AccelerometerReading) reading).getX());
		y_value.setText("" + ((AccelerometerReading) reading).getY());
		z_value.setText("" + ((AccelerometerReading) reading).getZ());

		rootView.invalidate();
	}

}
