package com.nn.kovaleva.irina.schooltutor;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentCalendar extends Fragment {

    private DateFormat md = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
    private TextView currentDate;
    private LinearLayout dayTable;
    private ScrollView scrollDayTable;

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
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        currentDate = view.findViewById(R.id.display_current_date);
        currentDate.setText(md.format(Calendar.getInstance().getTime()));
        dayTable = view.findViewById(R.id.hours_list);
        setDayTable(view);
        scrollDayTable = view.findViewById(R.id.day_table);
        scrollDayTable.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ready, move up
                scrollDayTable.scrollTo(0, 8*100 + 8);
            }
        });


        return view;

    }

    @SuppressLint("ResourceAsColor")
    private void setDayTable(View view){
        LinearLayout.LayoutParams oneHourLayoutParams = new LinearLayout.LayoutParams(
                -1, 100);
        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(
                120, -1);
        LinearLayout.LayoutParams verLineLayoutParams = new LinearLayout.LayoutParams(
                1, -1);
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(
                -1, 1);

        for (int i = 0; i < 24; i ++) {
            LinearLayout oneHour = new LinearLayout(view.getContext());
            oneHour.setOrientation(LinearLayout.HORIZONTAL);
            oneHour.setLayoutParams(oneHourLayoutParams);

            TextView hrs = new TextView(view.getContext());
            String str = ((i == 0) ? 12 : i) + ((i < 12) ? " a.m." : " p.m.");
            hrs.setText(str);
            hrs.setTextColor(Color.BLACK);
            hrs.setLayoutParams(layoutParamsText);
            hrs.setGravity(Gravity.CENTER);

            View verLine = new View(view.getContext());
            verLine.setBackgroundColor(R.color.colorDivider);
            verLine.setLayoutParams(verLineLayoutParams);

            View horLine = new View(view.getContext());
            horLine.setLayoutParams(layoutParamsLine);
            horLine.setBackgroundColor(R.color.colorDivider);

            oneHour.addView(hrs);
            oneHour.addView(verLine);

            dayTable.addView(oneHour);
            dayTable.addView(horLine);
        }

    }
}
