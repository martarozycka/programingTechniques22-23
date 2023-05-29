package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import be.kuleuven.gt.model.User;

public class TripAddActivity extends AppCompatActivity {

    private ArrayList<String> userIdList = new ArrayList<>();
    private static final String USERID_URL = "https://studev.groept.be/api/a22pt303/checkUserFromId/";
    private TextInputEditText name;
    private TextInputEditText tripLocation;
    private EditText startdate;
    private EditText enddate;
    private EditText comments;
    private Button submitTrip;
    private EditText friendName;
    private Button addFriendButton;
    private static final String trip = "https://studev.groept.be/api/a22pt303/insertTrip/name/startdate/enddate";

    private static final String location = "https://studev.groept.be/api/a22pt303/insertLocation/location/titletrip";
    private static final String comment = "https://studev.groept.be/api/a22pt303/insertComment/comment/titletrip/username";

    private static final String tripUser="https://studev.groept.be/api/a22pt303/insertTripUser/titletrip/username";
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
        friendName=findViewById(R.id.friendName);
        addFriendButton=findViewById(R.id.addFriendButton);

        //checking that user has finished putting data

        name.addTextChangedListener(textWatcher1);
        tripLocation.addTextChangedListener(textWatcher1);
        startdate.addTextChangedListener(textWatcher1);
        enddate.addTextChangedListener(textWatcher1);
        friendName.addTextChangedListener(textWatcher2);
        //disable button by default
        submitTrip.setEnabled(false);
        addFriendButton.setEnabled(false);

    }
    private TextWatcher textWatcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        public void afterTextChanged(Editable s) {
            // Check if all text fields have data
            boolean enableButton1 = name.getText().length() > 0 && tripLocation.getText().length() > 0 && startdate.getText().length() > 0 && enddate.getText().length() > 0;
            // Enable/disable the button based on the text fields' content
            submitTrip.setEnabled(enableButton1);
        }
    };

    private TextWatcher textWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        public void afterTextChanged(Editable s) {
            // Check if all text fields have data
            boolean enableButton2 = friendName.getText().length() > 0 ;
            // Enable/disable the button based on the text fields' content
            addFriendButton.setEnabled(enableButton2);
        }
    };

    private void insertTrip() {
            // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(TripAddActivity.this);
        progressDialog.setMessage("Uploading, please wait...");


        // Create the POST request with the data to be sent.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, trip,
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
                    params.put("name", name.getText().toString());
                // params.put("titleTrip", "fun");
                    params.put("startdate", startdate.getText().toString());
                    //params.put("startDate", "2023-02-05");
                    params.put("enddate", enddate.getText().toString());
                   // params.put("endDate", "2023-04-07");
                    return params;
                }
            };

            // Add the request to the RequestQueue.
             progressDialog.show();
            queue.add(stringRequest);
        }

    private void insertLocation() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(TripAddActivity.this);
        progressDialog.setMessage("Uploading, please wait...");


        // Create the POST request with the data to be sent.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, location,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server, if needed.
                        progressDialog.dismiss();

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
                params.put("location", tripLocation.getText().toString());
                params.put("titletrip", name.getText().toString());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        progressDialog.show();
        queue.add(stringRequest);
    }
    private void insertComments() {

        // Instantiate the RequestQueue.
        User user = getIntent().getParcelableExtra("User");
        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(TripAddActivity.this);
        progressDialog.setMessage("Uploading, please wait...");


        // Create the POST request with the data to be sent.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, comment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server, if needed.
                        progressDialog.dismiss();
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
                params.put("comment", comments.getText().toString());
                params.put("location", tripLocation.getText().toString());
                params.put("username", user.getUsername());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        progressDialog.show();
        queue.add(stringRequest);
    }

    private void insertTripUser(){

        User user = getIntent().getParcelableExtra("User");
        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(TripAddActivity.this);
        progressDialog.setMessage("Uploading, please wait...");


        // Create the POST request with the data to be sent.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tripUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server, if needed.
                        progressDialog.dismiss();
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
                params.put("titletrip", name.getText().toString());
                params.put("username", user.getUsername());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        progressDialog.show();
        queue.add(stringRequest);
    }

    private void requestUserIds() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, USERID_URL + friendName.getText().toString(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // JSON array is obtained successfully
                        // Proceed with parsing and using the data
                        processJSONArray(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                TripAddActivity.this,
                                "Unable to communicate with the server",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void showFriendAddedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TripAddActivity.this);
        builder.setTitle("Friend Added");
        builder.setMessage("Your friend has been added successfully.");
        builder.setPositiveButton("OK", null);
        builder.show();
    }
    private void processJSONArray(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userId = jsonObject.getString("idUser");
                userIdList.add(userId);
            }

            // Check the size of userIdList here
            if (userIdList.size() == 0) {
                Toast.makeText(
                        TripAddActivity.this,
                        "Tell your friend to download the app",
                        Toast.LENGTH_LONG).show();
                submitTrip.setEnabled(false);
            } else {
                submitTrip.setEnabled(true);
                showFriendAddedDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addFriend(){

        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(TripAddActivity.this);
        progressDialog.setMessage("Uploading, please wait...");


        // Create the POST request with the data to be sent.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tripUser,
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
                params.put("titletrip", name.getText().toString());
                params.put("username", friendName.getText().toString());

                return params;
            }
        };

        // Add the request to the RequestQueue.
        progressDialog.show();
        queue.add(stringRequest);
    }
    public void onBtnAddFriend_Clicked(View Caller) {
        if (addFriendButton.isEnabled()){
            requestUserIds();
        }}
    private boolean isValidDateFormat(String date) {
        // Define the desired date format using regular expressions
        String dateFormatPattern = "\\d{4}-\\d{2}-\\d{2}";

        // Match the input date against the pattern
        Pattern pattern = Pattern.compile(dateFormatPattern);
        return pattern.matcher(date).matches();
    }

    public void onBtnSubmitTrip_Clicked(View Caller) {
        if (submitTrip.isEnabled()){
            if (isValidDateFormat(startdate.getText().toString().trim()) && isValidDateFormat(enddate.getText().toString().trim())){
                // Both dates have the correct format
                insertTrip();
                insertLocation();
                insertTripUser();
                insertComments();
                addFriend();
                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
            } else {
                // Incorrect date format. Show an error message or take appropriate action.
                Toast.makeText(
                        TripAddActivity.this,
                        "Incorrect date format",
                        Toast.LENGTH_LONG).show();
            }

        }}}



