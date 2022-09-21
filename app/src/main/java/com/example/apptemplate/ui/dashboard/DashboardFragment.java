package com.example.apptemplate.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.apptemplate.R;
import com.example.apptemplate.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public FragmentDashboardBinding binding;
    FragmentManager childFragManager;
    public LinearLayout main;
    int meetingYear;
    int meetingMonth;
    int meetingDayOfMonth;
    SharedPreferences sharedPreferences;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        main = root.findViewById(R.id.LLMain);
        CalendarView meetingCalendar = root.findViewById(R.id.calendarView);
        TextView meeting = root.findViewById(R.id.TVMeeting);
        Button addMeeting = root.findViewById(R.id.addMeeting);
        ConstraintLayout frame = root.findViewById(R.id.constraint);
        LinearLayout meetingView = root.findViewById(R.id.meetingView);
        ScrollView mainScroll = root.findViewById(R.id.MainScroll);
        TextView subject = root.findViewById(R.id.Subject);
        TextView tutor = root.findViewById(R.id.Tutor);
        TextView meetingTime = root.findViewById(R.id.Time);

        childFragManager = getChildFragmentManager();
        sharedPreferences = getActivity().getSharedPreferences("Meeting Information", Context.MODE_PRIVATE);

        meetingView.setVisibility(View.INVISIBLE);
        meeting.setVisibility(View.INVISIBLE);

        meetingCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                meetingYear = year;
                meetingMonth = month;
                meetingDayOfMonth = dayOfMonth;
                meeting.setText(month+"/"+dayOfMonth+"/"+year);
                meeting.setVisibility(View.VISIBLE);

                if (meetingMonth == sharedPreferences.getInt("MONTH",0) &&
                    meetingYear == sharedPreferences.getInt("YEAR",0) &&
                    meetingDayOfMonth == sharedPreferences.getInt("DAY",0)) {
                    subject.setText(sharedPreferences.getString("SUBJECT",""));
                    tutor.setText(sharedPreferences.getString("NAME",""));
                    meetingTime.setText(String.valueOf(sharedPreferences.getInt("HOUR",0)) + " : " +
                                String.format("%02d", sharedPreferences.getInt("MINUTE",0)) + " pm");
                    meetingView.setVisibility(View.VISIBLE);
                    mainScroll.fullScroll(View.FOCUS_DOWN);
                }
                else {
                    meetingView.setVisibility(View.INVISIBLE);
                }
            }
        });

        addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("YEAR", meetingYear);
                editor.putInt("MONTH", meetingMonth);
                editor.putInt("DAY", meetingDayOfMonth);
                editor.apply();

                main.setVisibility(View.INVISIBLE);
                if (!isAdded()) {return;}
                Fragment meetingFragment = new MeetingFragment();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.constraint, meetingFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}