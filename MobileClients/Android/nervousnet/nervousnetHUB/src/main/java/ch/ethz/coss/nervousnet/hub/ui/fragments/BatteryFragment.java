/**
 * *     Nervousnet - a distributed middleware software for social sensing.
 * *      It is responsible for collecting and managing data in a fully de-centralised fashion
 * *
 * *     Copyright (C) 2016 ETH Zürich, COSS
 * *
 * *     This file is part of Nervousnet Framework
 * *
 * *     Nervousnet is free software: you can redistribute it and/or modify
 * *     it under the terms of the GNU General Public License as published by
 * *     the Free Software Foundation, either version 3 of the License, or
 * *     (at your option) any later version.
 * *
 * *     Nervousnet is distributed in the hope that it will be useful,
 * *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 * *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * *     GNU General Public License for more details.
 * *
 * *     You should have received a copy of the GNU General Public License
 * *     along with NervousNet. If not, see <http://www.gnu.org/licenses/>.
 * *
 * *
 * * 	Contributors:
 * * 	Prasad Pulikal - prasad.pulikal@gess.ethz.ch  -  Initial API and implementation
 */
/**
 *
 */
package ch.ethz.coss.nervousnet.hub.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ch.ethz.coss.nervousnet.hub.Application;
import ch.ethz.coss.nervousnet.hub.R;
import ch.ethz.coss.nervousnet.lib.ErrorReading;
import ch.ethz.coss.nervousnet.lib.LibConstants;
import ch.ethz.coss.nervousnet.lib.SensorReading;
import ch.ethz.coss.nervousnet.vm.NNLog;
import ch.ethz.coss.nervousnet.vm.NervousnetVMConstants;

public class BatteryFragment extends BaseFragment {

    private static final String LOG_TAG = BatteryFragment.class.getSimpleName();

    public BatteryFragment() {
        super(LibConstants.SENSOR_BATTERY);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battery, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sensorStatusTV = (TextView) getView().findViewById(R.id.sensorStatus);
        radioGroup = (RadioGroup) getView().findViewById(R.id.radioRateSensor);
        lastCollectionRate = ((((Application) ((Activity) getContext()).getApplication()).nn_VM.getSensorState(LibConstants.SENSOR_BATTERY)));

        ((RadioButton) radioGroup.getChildAt(lastCollectionRate)).setChecked(true);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                NNLog.d(LOG_TAG, "Inside radioGroup onCheckedChanged ");

                switch (checkedId) {
                    case R.id.radioOff:
                        if (lastCollectionRate > NervousnetVMConstants.SENSOR_STATE_AVAILABLE_BUT_OFF) {
                            ((Application) ((Activity) getContext()).getApplication()).nn_VM.updateSensorState(LibConstants.SENSOR_BATTERY, NervousnetVMConstants.SENSOR_STATE_AVAILABLE_BUT_OFF);
                        }
                        break;
                    case R.id.radioLow:
                        if (lastCollectionRate >= NervousnetVMConstants.SENSOR_STATE_AVAILABLE_BUT_OFF) {
                            ((Application) ((Activity) getContext()).getApplication()).nn_VM.updateSensorState(LibConstants.SENSOR_BATTERY, NervousnetVMConstants.SENSOR_STATE_AVAILABLE_DELAY_LOW);
                        }
                        break;
                    case R.id.radioMed:
                        if (lastCollectionRate >= NervousnetVMConstants.SENSOR_STATE_AVAILABLE_BUT_OFF) {
                            ((Application) ((Activity) getContext()).getApplication()).nn_VM.updateSensorState(LibConstants.SENSOR_BATTERY, NervousnetVMConstants.SENSOR_STATE_AVAILABLE_DELAY_MED);
                        }
                        break;
                    case R.id.radioHigh:
                        if (lastCollectionRate >= NervousnetVMConstants.SENSOR_STATE_AVAILABLE_BUT_OFF) {
                            ((Application) ((Activity) getContext()).getApplication()).nn_VM.updateSensorState(LibConstants.SENSOR_BATTERY, NervousnetVMConstants.SENSOR_STATE_AVAILABLE_DELAY_HIGH);
                        }
                        break;
                }
            }
        });

        if ((((Application) ((Activity) getContext()).getApplication()).nn_VM.getNervousnetState() == NervousnetVMConstants.STATE_PAUSED)) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                ((RadioButton) radioGroup.getChildAt(i)).setEnabled(false);
            }
            sensorStatusTV.setText(R.string.local_service_paused);
        }
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
        NNLog.d(LOG_TAG, "Inside updateReadings");
        if (reading instanceof ErrorReading) {

            NNLog.d(LOG_TAG, "Inside updateReadings - ErrorReading");
            handleError((ErrorReading) reading);
        } else {
            sensorStatusTV.setText(R.string.sensor_status_connected);
            ArrayList values = reading.getValues();

            TextView percent = (TextView) getActivity().findViewById(R.id.battery_percent);
            percent.setText("" + (float)values.get(11)*100 + " %");


            TextView isCharging = (TextView) getActivity().findViewById(R.id.battery_isCharging);
            isCharging.setText(((int)reading.getValues().get(7) != 0) ? "YES" : "NO");

            TextView USB_Charging = (TextView) getActivity().findViewById(R.id.battery_isUSB);
            USB_Charging.setText((int)reading.getValues().get(8) > 0 ? getContext().getString(R.string.yes) : getContext().getString(R.string.no));

            TextView AC_charging = (TextView) getActivity().findViewById(R.id.battery_isAC);
            AC_charging.setText((int)reading.getValues().get(9) > 0 ? getContext().getString(R.string.yes) : getContext().getString(R.string.no));


        }
    }

    @Override
    public void handleError(ErrorReading reading) {
        NNLog.d(LOG_TAG, "handleError called");
        sensorStatusTV.setText(reading.getErrorString());
    }


}
