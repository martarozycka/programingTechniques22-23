package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.gt.model.Trip;

public class TripLogActivity extends AppCompatActivity {
    private TextView txtTripName;
    private TextView txtStartDate;
    private TextView txtEndDate;
    private TextView txtLocationName;

    //private RecyclerView commentView;
    //private static final String COMMENT_URL = "https://studev.groept.be/api/a22pt303/selectAllTripsOfAUser/";
    //private List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_log);

        // Initialize TextViews
        txtTripName = findViewById(R.id.txtTripName);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtLocationName = findViewById(R.id.txtLocationName);

        Trip trip = (Trip) getIntent().getParcelableExtra("Trip");
        txtTripName.setText(trip.getName());
        txtStartDate.setText(trip.getStartDate());
        txtEndDate.setText(trip.getEndDate());
        // initialize 1st location of the trip

    }
}