package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;

public class logInPage extends AppCompatActivity {

    private Button submitLogIn;
    private TextInputEditText username;
    private EditText password;


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
            submitLogIn.setEnabled(enableButton);
        }
    };


}