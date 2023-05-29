package be.kuleuven.gt.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.kuleuven.gt.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLogIn = (Button) findViewById(R.id.idLogIn);
        Button btnSignUp = (Button) findViewById(R.id.idSignUp);
    }
    public void onBtnLogin_Clicked(View Caller) {
        Intent intent = new Intent(this, logInPage.class);
        startActivity(intent);
    }
    public void onBtnSignUp_Clicked(View Caller) {
        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
    }
}