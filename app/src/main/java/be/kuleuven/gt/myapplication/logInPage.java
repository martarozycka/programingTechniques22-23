package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class logInPage extends AppCompatActivity {

    private Button submitLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        submitLogIn=(Button)findViewById(R.id.submitLogIn);
    }
}