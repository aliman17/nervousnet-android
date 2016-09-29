/*******************************************************************************
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
 *******************************************************************************/
package ch.ethz.coss.nervousnet.lib;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author prasad
 */
public class AccelerometerReading extends SensorReading {

    public static final Parcelable.Creator<AccelerometerReading> CREATOR = new Parcelable.Creator<AccelerometerReading>() {
        @Override
        public AccelerometerReading createFromParcel(Parcel in) {
            return new AccelerometerReading(in);
        }

        @Override
        public AccelerometerReading[] newArray(int size) {
            return new AccelerometerReading[size];
        }
    };

    List<String> paramNames = Arrays.asList(new String[]{"accX", "accY", "accZ"});

    public AccelerometerReading(long timestamp, float[] values) {
        this.type = LibConstants.SENSOR_ACCELEROMETER;
        this.timestamp = timestamp;

        // Init inside
        this.values = new ArrayList();
        for (float f : values) {
            this.values.add(new Float(f));
        }
    }

    /**
     * @param in
     */
    public AccelerometerReading(Parcel in) {
        readFromParcel(in);
    }

    public float getX() {
        return (float)values.get(0);
    }

    public float getY() {
        return (float)values.get(1);
    }

    public float getZ() {
        return (float)values.get(2);
    }

    public void readFromParcel(Parcel in) {
        timestamp = in.readLong();
        values = new ArrayList();
        in.readList(values, getClass().getClassLoader());
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.Parcelable#describeContents()
     */
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(getClass().getName());
        out.writeLong(timestamp);
        out.writeList(values);
    }

    @Override
    public List<String> getValNames() {
        return this.paramNames;
    }
}
