package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import be.kuleuven.gt.model.User;

public class logInPage extends AppCompatActivity {

    private ArrayList<String> userList = new ArrayList<>();
    private String userInput;
    private String passwordInput;
    HashMap<String, String> infoUser = new HashMap<String, String>();
    private Button submitLogIn;
    private TextInputEditText username;
    private EditText password;
    private static final String USERNAME_URL = "https://studev.groept.be/api/a22pt303/allPasswordsandUsers";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        submitLogIn=(Button)findViewById(R.id.submitLogIn);
        username = findViewById(R.id.userLogIn);
        password = findViewById(R.id.passLogIn);

        //checking that user has finished putting data
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        //disable button by default
        submitLogIn.setEnabled(false);
        requestUserNames();

    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            // check if all text fields have data
            boolean enableButton = username.getText().length() > 0 && password.getText().length() > 0 ;
            // enable/disable the button based on the text fields' content
            submitLogIn.setEnabled(true);
            userInput = username.getText().toString();
            passwordInput=password.getText().toString();
        }
    };


    private void requestUserNames() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, USERNAME_URL, null,
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
                                logInPage.this,
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
                String user= jsonObject.getString("nameUser");
                String password=jsonObject.getString("password");
                userList.add(user);
                infoUser.put(user,password);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onBtnNext_Clicked(View Caller) {
        int i=0;
        for (i=0;i<userList.size();i++){
            if (userInput. equals(userList.get(i))){
                 if (passwordInput.equals(infoUser.get(userInput))){
                     Intent intent = new Intent(this, HomePageActivity.class);
                     startActivity(intent);
                 }
                 else {
                         Toast.makeText(
                                 logInPage.this,
                                 "Incorrect password",
                                 Toast.LENGTH_LONG).show();
                 }
                 break;
            }
            if (i==userList.size()-1){
                Toast.makeText(
                        logInPage.this,
                        "Incorrect username",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
