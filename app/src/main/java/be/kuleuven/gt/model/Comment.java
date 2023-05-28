package be.kuleuven.gt.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.kuleuven.gt.myapplication.TripLogActivity;

public class Comment implements Parcelable {
    private String username;
    private String commentTxt;

    public Comment(String username, String commentTxt) {
        this.username = username;
        this.commentTxt = commentTxt;
    }

    protected Comment(Parcel in) {
        username = in.readString();
        commentTxt = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }
        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(commentTxt);
    }

    public String getUsername() {
        return username;
    }

    public String getCommentTxt() {
        return commentTxt;
    }

}
