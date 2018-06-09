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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentCalendar extends Fragment implements View.OnClickListener {

    private DateFormat md = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
    private TextView currentDate;
    private LinearLayout dayTable;
    private ScrollView scrollDayTable;
    private ImageView leftArrow, rightArrow;
    private Date curDate;

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
        curDate = Calendar.getInstance().getTime();
        currentDate.setText(md.format(curDate));
        dayTable = view.findViewById(R.id.hours_list);
        setDayTable(view);
        scrollDayTable = view.findViewById(R.id.day_table);
        scrollDayTable.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ready, move up
                scrollDayTable.scrollTo(0, 8*100 + 8*2);
            }
        });
        leftArrow = view.findViewById(R.id.previous_day);
        rightArrow = view.findViewById(R.id.next_day);
        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);

        return view;

    }

    @SuppressLint("ResourceAsColor")
    private void setDayTable(View view){
        LinearLayout.LayoutParams oneHourLayoutParams = new LinearLayout.LayoutParams(
                -1, 100);
        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(
                120, -1);
        LinearLayout.LayoutParams verLineLayoutParams = new LinearLayout.LayoutParams(
                2, -1);
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(
                -1, 2);

        for (int i = 0; i < 24; i ++) {
            LinearLayout oneHour = new LinearLayout(view.getContext());
            oneHour.setOrientation(LinearLayout.HORIZONTAL);
            oneHour.setLayoutParams(oneHourLayoutParams);

            TextView hrs = new TextView(view.getContext());
            String str = ((i == 0) ? 12 : i) + ((i < 12) ? " a.m." : " p.m.");
            hrs.setText(str);
            hrs.setId(1000 + i);
            hrs.setTextColor(Color.parseColor("#455ede")); //primary dark color
            hrs.setTextSize(16);
            hrs.setLayoutParams(layoutParamsText);
            hrs.setGravity(Gravity.CENTER);

            View verLine = new View(view.getContext());
            verLine.setBackgroundColor(Color.parseColor("#03a9f4"));//primary color
            verLine.setLayoutParams(verLineLayoutParams);

            View horLine = new View(view.getContext());
            horLine.setLayoutParams(layoutParamsLine);
            horLine.setBackgroundColor(Color.parseColor("#03a9f4"));//primary color

            oneHour.addView(hrs);
            oneHour.addView(verLine);

            dayTable.addView(oneHour);
            dayTable.addView(horLine);
        }

    }

    @Override
    public void onClick(View v) {
        int dayInMs = 1000 * 60 * 60 * 24;
        switch (v.getId()){
            case R.id.previous_day: {
                Date prevDate = new Date(curDate.getTime() - dayInMs);
                curDate = prevDate;
                currentDate.setText(md.format(curDate));
                break;
            }
            case R.id.next_day: {
                Date nextDate = new Date(curDate.getTime() + dayInMs);
                curDate = nextDate;
                currentDate.setText(md.format(curDate));
                break;
            }
        }
    }
}
