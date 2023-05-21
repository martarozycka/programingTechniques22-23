package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import be.kuleuven.gt.model.Trip;

public class TripAddActivity extends AppCompatActivity {

    private TextInputEditText name;
    private TextInputEditText tripLocation;
    private EditText startdate;
    private EditText enddate;
    private EditText comments;
    private Button submitTrip;
    private static final String url = "https://studev.groept.be/api/a22pt303/insertTrip/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_add);
        submitTrip = (Button)findViewById(R.id.submitTrip);
        name = (TextInputEditText) findViewById(R.id.tripTitle);
        tripLocation= findViewById(R.id.tripLocation);
        startdate= findViewById(R.id.startDate);
        enddate= findViewById(R.id.endDate);
        comments= findViewById(R.id.putComment);

        //checking that user has finished putting data

        name.addTextChangedListener(textWatcher);
        tripLocation.addTextChangedListener(textWatcher);
        startdate.addTextChangedListener(textWatcher);
        enddate.addTextChangedListener(textWatcher);

        //disable button by default
        submitTrip.setEnabled(false);

    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        public void afterTextChanged(Editable s) {
            // Check if all text fields have data
            boolean enableButton = name.getText().length() > 0 && tripLocation.getText().length() > 0 && startdate.getText().length() > 0 && enddate.getText().length() > 0;
            // Enable/disable the button based on the text fields' content
            submitTrip.setEnabled(enableButton);
        }
    };

    private void updateDatabase() {

            // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(TripAddActivity.this);
        progressDialog.setMessage("Uploading, please wait...");


        // Create the POST request with the data to be sent.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the response from the server, if needed.
                            progressDialog.dismiss();
                            Toast.makeText(
                                    TripAddActivity.this,
                                    "Post request executed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error response, if needed.
                            progressDialog.dismiss();
                            Toast.makeText(
                                    TripAddActivity.this,
                                    "Unable to communicate with the server",
                                    Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    // Add the data to the request as parameters.
                    Map<String, String> params = new HashMap<>();
                    params.put("titleTrip", name.getText().toString());
                    // params.put("titleTrip", "fun");
                    params.put("startDate", startdate.getText().toString());
                    //params.put("startDate", "2023-02-05");
                    params.put("endDate", enddate.getText().toString());
                   // params.put("endDate", "2023-04-07");
                    return params;
                }
            };

            // Add the request to the RequestQueue.
             progressDialog.show();
            queue.add(stringRequest);
        }


    public void onBtnSubmitTrip_Clicked(View Caller) {
        if (submitTrip.isEnabled()){
                    updateDatabase();
                    Intent intent = new Intent(this, TripConfirmationActivity.class);
                    startActivity(intent);

        }}}



