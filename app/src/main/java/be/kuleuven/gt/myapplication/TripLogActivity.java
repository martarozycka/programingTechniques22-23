package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import be.kuleuven.gt.model.Comment;
import be.kuleuven.gt.model.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.gt.model.Trip;

public class TripLogActivity extends AppCompatActivity {

    private ArrayList<Location> locationList = new ArrayList<>();
    private TextView txtTripName;
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtLocationName;

    private static final String LOCATION_URL = "https://studev.groept.be/api/a22pt303/selectLocationsPerTrip/";

    private RecyclerView commentView;
    private List<Comment> comments = new ArrayList<>();
    private static final String COMMENT_URL = "https://studev.groept.be/api/a22pt303/selectCommentsPerLocation/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_log);
        commentView = findViewById( R.id.commentView );

        // Initialize TextViews
        txtTripName = findViewById(R.id.txtTripName);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtLocationName = findViewById(R.id.txtLocationName);

        Trip trip = (Trip) getIntent().getParcelableExtra("Trip");
        txtTripName.setText(trip.getName());
        txtStartDate.setText(trip.getStartDate());
        txtEndDate.setText(trip.getEndDate());

        requestLocations();

//        CommentAdapter adapter = new CommentAdapter(comments);
//        commentView.setAdapter( adapter );
//        commentView.setLayoutManager( new LinearLayoutManager( this ));


    }

    private void requestLocations() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Trip trip = (Trip) getIntent().getParcelableExtra("Trip");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, LOCATION_URL + trip.getName(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // JSON array is obtained successfully
                        // Proceed with parsing and using the data
                        processJSONArray(response);


                        // initialize 1st location of the trip
                        if (!locationList.isEmpty()) {
                            txtLocationName.setText(locationList.get(0).getLocationName());
                        }

                        requestComments();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                TripLogActivity.this,
                                "Unable to communicate with the server",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void processJSONArray(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String locationName = jsonObject.getString("name");
                String longitude =jsonObject.getString("longitude");
                String latitude =jsonObject.getString("latitude");
                Location location = new Location(locationName, longitude, latitude);
                locationList.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //for the comments!
    private void requestComments() {
        String locationName = null;
        if (!locationList.isEmpty()) {
            locationName = locationList.get(0).getLocationName();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, COMMENT_URL + locationName, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // JSON array is obtained successfully
                        // Proceed with parsing and using the data
                        processJSONArray2(response);

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                TripLogActivity.this,
                                "Unable to communicate with the server",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void processJSONArray2(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String comment = jsonObject.getString("comment");
                String username =jsonObject.getString("nameUser");
                Comment newComment = new Comment(username, comment);
                comments.add(newComment);
            }
            // Notify the adapter that the data set has changed
            CommentAdapter adapter = new CommentAdapter(comments);
            commentView.setAdapter( adapter );
            commentView.setLayoutManager( new LinearLayoutManager( this ));
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}