package com.example.apptemplate.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.apptemplate.R;
import com.example.apptemplate.databinding.FragmentEditProfileBinding;


public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    SharedPreferences sharedPreferences;
    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageButton cancelButton = root.findViewById(R.id.imageButton3);
        ImageButton okButton = root.findViewById(R.id.imageButton4);
        EditText about = root.findViewById(R.id.editTextTextMultiLine);
        EditText name = root.findViewById(R.id.editTextTextPersonName);
        EditText subjects = root.findViewById(R.id.editTextTextPersonName2);

        sharedPreferences = getActivity().getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
        about.setText(sharedPreferences.getString("ABOUT",""));
        name.setText(sharedPreferences.getString("NAME", ""));
        subjects.setText(sharedPreferences.getString("SUBJECTS", ""));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aboutText = about.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ABOUT", aboutText);
                editor.putString("NAME", name.getText().toString());
                editor.putString("SUBJECTS", subjects.getText().toString());
                editor.apply();

                Toast.makeText(getContext(),"You profile has been updated.",Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Fragment calendarFragment = new NotificationsFragment();
                fragmentTransaction.replace(R.id.profileFrame, calendarFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment calendarFragment = new NotificationsFragment();
                fragmentTransaction.replace(R.id.profileFrame, calendarFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return root;
    }
}