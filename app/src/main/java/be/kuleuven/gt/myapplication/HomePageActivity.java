package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.kuleuven.gt.model.Trip;
import be.kuleuven.gt.model.User;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView tripView;
    private String newTrip;
    private static final String TRIP_URL = "https://studev.groept.be/api/a22pt303/selectAllTripsOfAUser/";
    private List<Trip> trips = new ArrayList<>();
    private static final String image_URL="https://studev.groept.be/api/a22pt303/retrivingImage/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tripView = findViewById( R.id.tripView );
        // use this use to display only this user's trips



        TripAdapter adapter = new TripAdapter( trips );
        tripView.setAdapter( adapter );
        tripView.setLayoutManager( new LinearLayoutManager( this ));
        requestTrips();

    }

    private void requestTrips() {
        User user = (User) getIntent().getParcelableExtra("User");
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest tripRequest = new JsonArrayRequest(
                Request.Method.GET,
                TRIP_URL + user.getUsername(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        processJSONResponse(response);
                        tripView.getAdapter().notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                HomePageActivity.this,
                                "Unable to communicate with the server",
                                Toast.LENGTH_LONG).show();
                    }
                } );
        requestQueue.add(tripRequest);

                }


    private void processJSONResponse(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                Trip trip = new Trip(response.getJSONObject(i));
                trips.add(trip);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void onBtnAddTrip_Clicked(View Caller) {
// Set user details
        User user = getIntent().getParcelableExtra("User");
        Intent intent = new Intent(HomePageActivity.this, TripAddActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);

}













}