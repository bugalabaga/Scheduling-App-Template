package com.example.apptemplate.ui.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.apptemplate.LoginActivity;
import com.example.apptemplate.R;
import com.example.apptemplate.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button editButton = root.findViewById(R.id.button);
        LinearLayout main = root.findViewById(R.id.linearLayout);
        TextView about = root.findViewById(R.id.textView7);
        TextView name = root.findViewById(R.id.textView3);
        TextView subjects = root.findViewById(R.id.textView11);
        TextView email = root.findViewById(R.id.textView9);
        ImageButton signOut = root.findViewById(R.id.imageButton5);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Profile Preferences", Context.MODE_PRIVATE);
        about.setText(sharedPreferences.getString("ABOUT", ""));
        name.setText(sharedPreferences.getString("NAME", ""));
        subjects.setText(sharedPreferences.getString("SUBJECTS", ""));
        email.setText(sharedPreferences.getString("EMAIL", ""));

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Would you like to Sign Out?");
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("FIRST LOGIN", true);
                                editor.apply();
                            }
                        });

                alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.setVisibility(View.INVISIBLE);
                if (!isAdded()) {
                    return;
                }
                Fragment meetingFragment = new EditProfileFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.profileFrame, meetingFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
}