package com.example.apptemplate.ui.dashboard;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.apptemplate.MeetingNotification;
import com.example.apptemplate.R;
import com.example.apptemplate.databinding.FragmentMeetingBinding;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class MeetingFragment extends Fragment {

    SharedPreferences sharedPreferences;

    private FragmentMeetingBinding binding;
    int hour, minute;

    public MeetingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMeetingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = getActivity().getSharedPreferences("Meeting Information", Context.MODE_PRIVATE);
        Button okButton = root.findViewById(R.id.addButton);
        Button cancelButton = root.findViewById(R.id.cancelButton);
        Button selectTime = root.findViewById(R.id.timeSelect);
        Spinner tutorNames = root.findViewById(R.id.tutorNames);
        Spinner subjects = root.findViewById(R.id.subjects);
        FrameLayout meetingFrame = root.findViewById(R.id.constraint);

        createNotificationChannel();

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker(v);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tutor = tutorNames.getSelectedItem().toString();
                String subject = subjects.getSelectedItem().toString();


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("NAME", tutor);
                editor.putString("SUBJECT", subject);
                editor.apply();

                Intent intent = new Intent(getContext(), MeetingNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, sharedPreferences.getInt("DAY",0));
                calendar.set(Calendar.MONTH, sharedPreferences.getInt("MONTH",0));
                calendar.set(Calendar.YEAR, sharedPreferences.getInt("YEAR",0));
                calendar.set(Calendar.HOUR_OF_DAY, sharedPreferences.getInt("HOUR",0));
                calendar.set(Calendar.MINUTE, sharedPreferences.getInt("MINUTE",0));
                calendar.set(Calendar.SECOND, 1);

                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() - (sharedPreferences.getInt("NOTIF MARGIN",10) *60000), pendingIntent);

                Toast.makeText(getContext(),"A new meeting has been added.",Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment calendarFragment = new DashboardFragment();
                fragmentTransaction.replace(R.id.constraint, calendarFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment calendarFragment = new DashboardFragment();
                fragmentTransaction.replace(R.id.constraint, calendarFragment);
                fragmentManager.popBackStack();
                fragmentTransaction.commit();

            }
        });
        return root;
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                hour = hourOfDay;
                minute = minuteOfHour;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("HOUR", hourOfDay);
                editor.putInt("MINUTE", minute);
                editor.apply();
            }
        };
        TimePickerDialog timePickerDialog  = new TimePickerDialog(getContext(),onTimeSetListener,hour, minute, false);
        timePickerDialog.show();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("meetingNotify","MeetingRemindersChannel", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    Log.d("attach","Meeting Fragment got Attached.");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("attach","Meeting Fragment got Detached.");

    }

}