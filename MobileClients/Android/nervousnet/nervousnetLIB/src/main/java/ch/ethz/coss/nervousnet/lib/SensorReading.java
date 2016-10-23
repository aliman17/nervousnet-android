package ch.ethz.coss.nervousnet.lib;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ales on 20/09/16.
 */
public class SensorReading implements Parcelable {

    // Description
    protected      int         id;                 // unique id of the sensor or error=-1
    protected     String      sensorName;         // sensor name
    protected     long        timestampEpoch;     // timestamp
    protected   List<String>  parametersNames;    // list of parameters' names

    // Data
    protected     ArrayList    values;

    // Constructor
    public SensorReading(){}

    public SensorReading(String sensorName,
                         ArrayList<String> paramNames){
        this.sensorName = sensorName;
        this.parametersNames = paramNames;
        this.values = new ArrayList<>();
        for (String name : parametersNames)
            values.add(0);
    }

    public String getSensorName() {
        return sensorName;
    }

    public long getTimestampEpoch() {
        return timestampEpoch;
    }

    public List<String> getParametersNames() {
        return parametersNames;
    }

    public ArrayList getValues() {
        return values;
    }

    public void setValue(String paramName, Object value){
        int index = this.parametersNames.indexOf(paramName);
        this.values.set(index, value);
    }

    public void setTimestampEpoch(long timestamp){
        this.timestampEpoch = timestamp;
    }

    public void setParametersNames(ArrayList<String> paramNames){
        this.parametersNames = paramNames;
        if (values == null || values.size() != paramNames.size())
            this.values = new ArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "sensorName='" + sensorName + '\'' +
                ", parametersNames size=" + parametersNames.size() +
                ", timestampEpoch=" + timestampEpoch +

                '}';
    }







    // Context for communication

    public static final Creator<SensorReading> CREATOR = new Creator<SensorReading>() {
        @Override
        public SensorReading createFromParcel(Parcel in) {
            return new SensorReading(in);
        }

        @Override
        public SensorReading[] newArray(int size) {
            return new SensorReading[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(sensorName);
        parcel.writeLong(timestampEpoch);
        parcel.writeStringList(parametersNames);
        // Pass values seperately, not as a list, as the types can be different
        parcel.writeList(values);
    }

    protected SensorReading(Parcel in) {
        readFromParcel(in);
    }

    // if the class id extended, one can override this function to satisfy reading
    public void readFromParcel(Parcel in){
        id = in.readInt();
        sensorName = in.readString();
        timestampEpoch = in.readLong();
        //TODO
        //parametersNames = in.createStringArrayList();
        //values = in.readList();
    }
}
