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
package ch.ethz.coss.nervousnet.lib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author prasad
 */
public class ErrorReading extends SensorReading {

	
	private String[] errorValues = new String[3];

	public ErrorReading(String[] values) {
		this.type = LibConstants.ERROR;
		this.errorValues = values;
	}

	/**
	 * @param in
	 */
	public ErrorReading(Parcel in) {
		readFromParcel(in);
	}

	public int getErrorCode() {
		return Integer.parseInt(errorValues[0]);
	}
	
	public String getErrorString() {
		return errorValues[1];
	}

	

	public void readFromParcel(Parcel in) {

		in.readStringArray(errorValues);
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
		out.writeStringArray(errorValues);
	}

	public static final Parcelable.Creator<ErrorReading> CREATOR = new Parcelable.Creator<ErrorReading>() {
		@Override
		public ErrorReading createFromParcel(Parcel in) {
			return new ErrorReading(in);
		}

		@Override
		public ErrorReading[] newArray(int size) {
			return new ErrorReading[size];
		}
	};

}
