package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import be.kuleuven.gt.model.Location;
import be.kuleuven.gt.model.Trip;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Location newLocation = (Location) getIntent().getParcelableExtra("Location");
        double lat = Double.parseDouble(newLocation.getLatitude());
        double lon = Double.parseDouble(newLocation.getLongitude());
        myMap = googleMap;
        LatLng location = new LatLng(lon,lat);
        myMap.addMarker(new MarkerOptions().position(location).title(newLocation.getLocationName()));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}