package com.example.apptemplate;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.Fragment;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.apptemplate.databinding.ActivityLoginBinding;


public class LoginActivity extends FragmentActivity {

    public ActivityLoginBinding binding;
    private boolean condition;
    private boolean firstLogin;

    private boolean loginMatch(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);

        if (username.equals(sharedPreferences.getString("EMAIL", null)) && password.hashCode() == sharedPreferences.getInt("PASSWORD",0)) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);
        EditText emailInput = findViewById(R.id.emailText);
        EditText passwordInput = findViewById(R.id.passwordText);
        ConstraintLayout constraint = findViewById(R.id.loginConstraintLayout);
        SharedPreferences sharedPreferences = getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);

        firstLogin = sharedPreferences.getBoolean("FIRST LOGIN",true);

        if (firstLogin == false) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Fragment meetingFragment = new SignupFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_browse_fragment, meetingFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (loginMatch(emailInput.getText().toString(),passwordInput.getText().toString())) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("FIRST LOGIN",false);
                editor.putString("EMAIL", emailInput.getText().toString());
                editor.apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Your login credentials don't match an \n account in our system.",Toast.LENGTH_SHORT).show();

            }
            }
        });


    }
}