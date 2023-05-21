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

    private static final String USERNAME_FROM_ID_URL = "https://studev.groept.be/api/a22pt303/selectUsernameFromUserId/";

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

    //    public Comment(JSONObject o) {
//        try {
//            //change to username!!!
//            int userId = o.getInt("idUser");
//            //username = getUserNameFromUserId(userId);
//            commentTxt = o.getString("comment");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    private String getUserNameFromUserId(int userId) {
//
//    }

//    private void requestUserNameFromId(int userId) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, USERNAME_FROM_ID_URL + userId, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // JSON array is obtained successfully
//                        // Proceed with parsing and using the data
//                        //processJSONArray(response);
//
//
//                    }
//
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(
////                                Comment.this,
////                                "Unable to communicate with the server",
////                                Toast.LENGTH_LONG).show();
////                    }
////                }
////        );
//        requestQueue.add(jsonArrayRequest);
//    }
//
//    private void processJSONArray(JSONArray jsonArray) {
//
//        try {
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String locationName = jsonObject.getString("name");
//                String longitude =jsonObject.getString("longitude");
//                String latitude =jsonObject.getString("latitude");
//                Location location = new Location(locationName, longitude, latitude);
//                //locationList.add(location);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
