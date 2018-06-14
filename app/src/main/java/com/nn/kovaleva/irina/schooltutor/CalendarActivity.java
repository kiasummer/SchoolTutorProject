package com.nn.kovaleva.irina.schooltutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.inducesmile.androidcalendardailyview.databases.DatabaseQuery;
//import com.inducesmile.androidcalendardailyview.databases.EventObjects;
import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.UI.EditProfileActivity;
import com.nn.kovaleva.irina.schooltutor.UI.LoginActivity;
import com.nn.kovaleva.irina.schooltutor.UI.SignUpActivity;
import com.nn.kovaleva.irina.schooltutor.core.Controller;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";

    private Intent intent;
    BottomNavigationView navigation;

    final public static int STATUS_TUTOR = 0;
    final public static int STATUS_STUDENT = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    ProfileFragment.status = ProfileFragment.OWNER;
                    selectedFragment = ProfileFragment.newInstance();
                    break;
                case R.id.navigation_calendar:
                    selectedFragment = FragmentCalendar.newInstance();
                    break;
                case R.id.navigation_students:
                    selectedFragment = StudentsFragment.newInstance();
                    break;
                case R.id.navigation_search:
                    selectedFragment = SearchFragment.newInstance();
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
        Log.d(TAG, "onCreate: creating main activity");
        super.onCreate(savedInstanceState);

        Controller.getsInstance().context = this;

        if (Controller.getsInstance().isIfLogIn() == false){
            intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }

        setContentView(R.layout.activity_calendar);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_calendar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraint_layout, FragmentCalendar.newInstance());
        transaction.commit();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: come back to main activity");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_CANCELED) {
                    if (data.getStringExtra("message").equals("exit")) {
                        finish();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), data.getStringExtra("message"),
                                Toast.LENGTH_SHORT);
                        toast.show();
                        intent = new Intent(this, LoginActivity.class);
                        startActivityForResult(intent, 1);
                    }
                } else if (resultCode == RESULT_OK) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT);
                    toast.show();
                    if (Actor.getsInstance().firstName.equals("") || Actor.getsInstance().secondName.equals("")) {
                        Intent intent = new Intent(this, EditProfileActivity.class);
                        startActivityForResult(intent, 2);
                    }
                    navigation.setSelectedItemId(R.id.navigation_calendar);
                } else {
                    finish();
                }
                break;
            }
            case 2:{
                if (resultCode == RESULT_OK){
                    Toast.makeText(this, "All changes saved", Toast.LENGTH_SHORT).show();
                } else {
                    if (requestCode == RESULT_CANCELED) {
                        if (data.getStringExtra("message") != null) {
                            Toast.makeText(getApplicationContext(), data.getStringExtra("message"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
            case 3:{
                if (resultCode == RESULT_OK){
                    Toast.makeText(this, "All changes saved", Toast.LENGTH_SHORT).show();
                    ProfileFragment.status = ProfileFragment.OWNER;
                    navigation.setSelectedItemId(R.id.navigation_profile);
                } else {
                    if (requestCode == RESULT_CANCELED) {
                        if (data.getStringExtra("message") != null) {
                            Toast.makeText(this, data.getStringExtra("message"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
        }
    }

    public static void hideSoftKeyBoard(Activity activity){
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),0);
    }

    public static void hideKeyBoardTouchEveryWhere(View view, final Activity activity){
        if (!(view instanceof EditText)){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    CalendarActivity.hideSoftKeyBoard(activity);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i ++){
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyBoardTouchEveryWhere(innerView, activity);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN){
            hideKeyboard();
        }
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
