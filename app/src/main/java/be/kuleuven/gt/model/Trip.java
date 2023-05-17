package be.kuleuven.gt.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Trip implements Parcelable {
    private String name;
    private String startDate;
    private String endDate;

    public Trip(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    protected Trip(Parcel in) {
        name = in.readString();
        startDate = in.readString();
        endDate = in.readString();
    }

    public Trip(JSONObject o) {
        try {
            name = o.getString("titleTrip");
            startDate = o.getString("startDate");
            endDate = o.getString("endDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(startDate);
        dest.writeString(endDate);
    }
//    JSON object??


    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }



}
