package com.nn.kovaleva.irina.schooltutor;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
//import com.inducesmile.androidcalendardailyview.databases.DatabaseQuery;
//import com.inducesmile.androidcalendardailyview.databases.EventObjects;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {

//    private WeekView.EventClickListener mEventClickListener = new WeekView.EventClickListener() {
//        @Override
//        public void onEventClick(WeekViewEvent event, RectF eventRect) {
//
//        }
//    };
//
//    private WeekView.EventLongPressListener mEventLongPressListener = new WeekView.EventLongPressListener() {
//        @Override
//        public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
//
//        }
//    };


    private static final String TAG = CalendarActivity.class.getSimpleName();
    private ImageView previousDay;
    private ImageView nextDay;
    private Calendar cal = Calendar.getInstance();
    private SQLiteDatabase mQuery;
    private FragmentCalendar fragmentCalendar;
    private RelativeLayout mLayout;
    private int eventIndex;
    private DateFormat md = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
    private TextView currentDate;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    selectedFragment = FragmentCalendar.newInstance();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = ItemOneFragment.newInstance();
                    break;
                case R.id.navigation_students:
                    break;
                case R.id.navigation_search:
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.constraint_layout, selectedFragment);
            transaction.commit();
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        //WeekView mWeekView = findViewById(R.id.weekView);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraint_layout, FragmentCalendar.newInstance());
        transaction.commit();

        //mWeekView.setOnEventClickListener(mEventClickListener);

 //       mQuery = new SQLiteDatabase(this);
//        mLayout = (RelativeLayout)findViewById(R.id.left_event_column);
//        eventIndex = mLayout.getChildCount();
        //fragmentCalendar = new FragmentCalendar();

//        currentDate = (TextView)findViewById(R.id.display_current_date);
//        currentDate.setText(md.format(Calendar.getInstance().getTime()));




//        currentDate.setText(displayDateInString(cal.getTime()));
//        displayDailyEvents();
//        previousDay = (ImageView)findViewById(R.id.previous_day);
//        nextDay = (ImageView)findViewById(R.id.next_day);
//        previousDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                previousCalendarDate();
//            }
//        });
//        nextDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nextCalendarDate();
//            }
//        });

    }

}
