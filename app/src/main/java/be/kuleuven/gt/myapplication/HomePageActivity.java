package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.gt.model.Trip;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView tripView;
    private static final String TRIP_URL = "https://studev.groept.be/api/a22pt303/trip";
    private List<Trip> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        tripView = findViewById( R.id.tripView );
        TripAdapter adapter = new TripAdapter( trips );
        tripView.setAdapter( adapter );
        tripView.setLayoutManager( new LinearLayoutManager( this ));
        requestTrips();

    }

    private void requestTrips() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest tripRequest = new JsonArrayRequest(
                Request.Method.GET,
                TRIP_URL,
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
        Intent intent = new Intent(this, TripLogActivity.class);
        startActivity(intent);}


}