package be.kuleuven.gt.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.kuleuven.gt.model.Comment;
import be.kuleuven.gt.model.Location;
import be.kuleuven.gt.model.Trip;

public class TripLogActivity extends AppCompatActivity {

    private ArrayList<Location> locationList = new ArrayList<>();
    private TextView txtLocationName;
    private static final String LOCATION_URL = "https://studev.groept.be/api/a22pt303/selectLocationsPerTrip/";
    private RecyclerView commentView;
    private List<Comment> comments = new ArrayList<>();
    private static final String COMMENT_URL = "https://studev.groept.be/api/a22pt303/selectCommentsPerLocation/";
    private Spinner spLocation;

    private ImageView imageRetrieved;
    private RequestQueue requestQueue;
    private ArrayList<String> locationNamesList = new ArrayList<>();


    private static final String image_URL="https://studev.groept.be/api/a22pt303/retrivingImage/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_log);
        commentView = findViewById( R.id.commentView );

        // Initialize TextViews
        TextView txtTripName = findViewById(R.id.txtTripName);
        TextView txtStartDate = findViewById(R.id.txtStartDate);
        TextView txtEndDate = findViewById(R.id.txtEndDate);
        txtLocationName = findViewById(R.id.txtLocationName);

            imageRetrieved = findViewById(R.id.imageRetrieved);
         requestQueue = Volley.newRequestQueue(this);

        Trip trip = (Trip) getIntent().getParcelableExtra("Trip");
        txtTripName.setText(trip.getName());
        txtStartDate.setText(trip.getStartDate());
        txtEndDate.setText(trip.getEndDate());

        spLocation = findViewById(R.id.spLocation);


        requestLocations();



// Create an ArrayAdapter using the location data and a default layout
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationNamesList);

// Specify the layout to use when the choices appear
        locationAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

// Set the adapter for the Spinner
        spLocation.setAdapter(locationAdapter);

        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spLocation.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected (if needed)
            }
        });
    }

    public void onBtnMap_Clicked(View Caller) {
        Location location = new Location(
                locationList.get(0).getLocationName(),
                locationList.get(0).getLatitude(),
                locationList.get(0).getLongitude()
        );
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("Location", location);
        startActivity(intent);
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
                            //txtLocationName.setText(spLocation.getSelectedItem().toString());
                          txtLocationName.setText(locationList.get(0).getLocationName());
                            //txtLocationName.setText(selectLocation);
                        }

                       requestComments();
                        requestImages();
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
                locationNamesList.add(locationName);
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

    /**
     * Retrieves the image from the DB
     */
    private void requestImages() {
        String locationName = null;
        if (!locationList.isEmpty()) {
            locationName = locationList.get(0).getLocationName();}
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                image_URL +locationName,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        processJSONResponse3(response);
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
                } );
        requestQueue.add(jsonArrayRequest );

    }


    private void processJSONResponse3(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                if (!jsonObject.isNull("image")) {
                    String base64Image = jsonObject.getString("image");
                    String base64ImageWithoutPrefix = base64Image.replace("data:image/jpeg;base64,", "");
                    byte[] imageBytes = Base64.decode(base64ImageWithoutPrefix, Base64.DEFAULT);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                    if (bitmap != null) {
                        ImageView imageView = findViewById(R.id.imageRetrieved);
                        imageView.setImageBitmap(bitmap);
                    } else {
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




}