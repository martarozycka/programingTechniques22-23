package be.kuleuven.gt.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Location implements Parcelable{

    private String locationName;
    private String longitude;
    private String latitude;

    public Location(String locationName, String longitude, String latitude) {
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Location(Parcel in) {
        locationName = in.readString();
        longitude = in.readString();
        latitude = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(locationName);
        dest.writeString(longitude);
        dest.writeString(latitude);
    }

    public Location(JSONObject o) {
        try {
            locationName = o.getString("name");
            longitude = o.getString("longitude");
            latitude = o.getString("latitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getLocationName() {
        return locationName;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
