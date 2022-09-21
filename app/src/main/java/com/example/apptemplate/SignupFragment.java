package com.example.apptemplate;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import android.widget.Toast;

import com.example.apptemplate.databinding.FragmentSignupBinding;


public class SignupFragment extends Fragment {

    public FragmentSignupBinding binding;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageButton backButton = root.findViewById(R.id.backButton);
        FrameLayout frameLayout = root.findViewById(R.id.signupFrame);
        Button signupButton = root.findViewById(R.id.loginButton);
        EditText email = root.findViewById(R.id.emailText);
        EditText password = root.findViewById(R.id.passwordText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(),"Please input valid information.",Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("EMAIL", email.getText().toString());
                    editor.putInt("PASSWORD", password.getText().toString().hashCode());
                    editor.apply();
                    getActivity().onBackPressed();
                }
            }
        });
        
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.INVISIBLE);
                getActivity().onBackPressed();
            }
        });

        return root;
    }
}