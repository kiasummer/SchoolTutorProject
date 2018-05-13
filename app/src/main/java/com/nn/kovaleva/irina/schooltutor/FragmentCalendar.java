package com.nn.kovaleva.irina.schooltutor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentCalendar extends Fragment {

//    private DateFormat md = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
//    private TextView currentDate;

    public static FragmentCalendar newInstance() {
        FragmentCalendar fragment = new FragmentCalendar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}
