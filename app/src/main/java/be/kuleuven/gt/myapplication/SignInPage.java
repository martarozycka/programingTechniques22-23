package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import be.kuleuven.gt.model.User;

public class SignInPage extends AppCompatActivity {

    private EditText createUsername;
    private EditText createPassword;
    private Button createAccount;
    private ArrayList<String> usernames = new ArrayList<>();
    private static final String USER_URL = "https://studev.groept.be/api/a22pt303/checkNewUsername/";
    private static final String newUser = "https://studev.groept.be/api/a22pt303/insertUser/username/password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        createUsername=findViewById(R.id.createUsername);
        createPassword=findViewById(R.id.createPassword);
        createAccount= (Button)findViewById(R.id.createAccount);

        createUsername.addTextChangedListener(textWatcher);
        createPassword.addTextChangedListener(textWatcher);
        //disable button by default
        createAccount.setEnabled(false);

    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            // check if all text fields have data
            boolean enableButton = createUsername.getText().length() > 0 && createPassword.getText().length() > 0 ;
            // enable/disable the button based on the text fields' content
            createAccount.setEnabled(enableButton);

        }
    };



    private void requestUsers() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, USER_URL + createUsername.getText().toString(), null,
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
                                SignInPage.this,
                                "Unable to communicate with the server",
                                Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void processJSONArray(JSONArray jsonArray) {
        usernames.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String user = jsonObject.getString("nameUser");
                usernames.add(user);
            }
            // Check the size of usernames here
            if (usernames.contains(createUsername.getText().toString())) {
                Toast.makeText(
                        SignInPage.this,
                        "Username already used",
                        Toast.LENGTH_LONG).show();
                createAccount.setEnabled(false);
            } else {
               createAccount.setEnabled(true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void insertUser() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(SignInPage.this);
        progressDialog.setMessage("Uploading, please wait...");


        // Create the POST request with the data to be sent.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, newUser,
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
                                SignInPage.this,
                                "Unable to communicate with the server",
                                Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Add the data to the request as parameters.
                Map<String, String> params = new HashMap<>();
                params.put("username", createUsername.getText().toString());
                params.put("password", createPassword.getText().toString());
                return params;
            }
        };

        // Add the request to the RequestQueue.
        progressDialog.show();
        queue.add(stringRequest);
    }
    public void onBtnCreateAccount_Clicked(View view) {
        if (createAccount.isEnabled()) {
            EditText createUsername= findViewById(R.id.createUsername);
            EditText createPassword = findViewById(R.id.createPassword);

            requestUsers();  // Request user data before inserting the user

            // Delay the insertion of the user to ensure the response is received
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (usernames.size() == 0) {
                        insertUser();

                        User user = new User(
                                createUsername.getText().toString(),
                                createPassword.getText().toString()
                        );

                        Intent intent = new Intent(SignInPage.this, HomePageActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(
                                SignInPage.this,
                                "Username already used",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }, 1000); // Delay of 1 second
        }}
    }
