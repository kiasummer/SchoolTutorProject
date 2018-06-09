package com.nn.kovaleva.irina.schooltutor.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.Themes;
import com.nn.kovaleva.irina.schooltutor.R;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.CustomSimpleSpinnerAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.CustomSpinnerAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.PlaceAutocompleteAdapter;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnFocusChangeListener{
    private static final String TAG = "EditProfileActivity";

    private LinearLayout listOfEdits;
    private TextView editProfileTitle;
    private ImageView backBtn;
    private EditText firstName;
    private View lineFirstName, linePatr, lineSecName, lineSubject, linePlace, lineEduc;
    private AutoCompleteTextView mSearchText;

    private int status;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;

    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    final public static int ADD = 0;
    final public static int EDIT = 1;
    final public static int RADIO_BTN_GROUP_ID = 3999;
    final public static int RADIO_GO_OUT_ID = 4000;
    final public static int RADIO_AT_HOME_ID = 4001;
    final public static int LINE_PATR_ID = 100;
    final public static int LINE_SECNAME_ID = 101;
    final public static int LINE_SUBJ_ID = 102;
    final public static int LINE_PLACE_ID = 103;
    final public static int LINE_EDUC_ID = 104;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        CalendarActivity.hideKeyBoardTouchEveryWhere(findViewById(R.id.edit_profile_parent),
                EditProfileActivity.this);

        listOfEdits = findViewById(R.id.edit_profile_list);
        editProfileTitle = findViewById(R.id.edit_profile_title);
        backBtn = findViewById(R.id.back_from_edit_profile);
        firstName = findViewById(R.id.first_name_edit);
        lineFirstName = findViewById(R.id.first_name_line_edit);

        firstName.setOnFocusChangeListener(this);

        Intent intent = getIntent();

        status = intent.getIntExtra("status", -1);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (Actor.getsInstance().ifTutor){
            setForTutor();
        } else {
            setForStudent();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }

    @SuppressLint("ResourceAsColor")
    private void setForTutor(){
        LinearLayout.LayoutParams layoutParamsTitleField = new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsEditField= new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(
                -1, 2);

        layoutParamsTitleField.setMargins(80,30,80,0);
        layoutParamsEditField.setMargins(80, 0, 80,0);
        layoutParamsLine.setMargins(80, 0, 80, 0);

        //-------------  patronymic --------------------

        TextView patronymicTitle = new TextView(this);
        patronymicTitle.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        patronymicTitle.setText("Patronymic");
        patronymicTitle.setTextSize(12);
        patronymicTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(patronymicTitle, layoutParamsTitleField);

        EditText patronymic = new EditText(this);
        patronymic.setTextColor(getResources().getColor(R.color.textColorDark));
        patronymic.setHint("Enter your patronymic");
        patronymic.setTextSize(20);
        patronymic.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(patronymic, layoutParamsEditField);

        patronymic.setId(LINE_PATR_ID);
        patronymic.setOnFocusChangeListener(this);

        linePatr = new View(this);
        linePatr.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        listOfEdits.addView(linePatr, layoutParamsLine);

        //----------------- second name ------------------------

        TextView secondNameTitle = new TextView(this);
        secondNameTitle.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        secondNameTitle.setText("Second Name");
        secondNameTitle.setTextSize(12);
        secondNameTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(secondNameTitle, layoutParamsTitleField);

        EditText secondName = new EditText(this);
        secondName.setTextColor(getResources().getColor(R.color.textColorDark));
        secondName.setHint("Enter your second name");
        secondName.setTextSize(20);
        secondName.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(secondName, layoutParamsEditField);

        secondName.setId(LINE_SECNAME_ID);
        secondName.setOnFocusChangeListener(this);

        lineSecName = new View(this);
        lineSecName.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        listOfEdits.addView(lineSecName, layoutParamsLine);

        commonPart(layoutParamsTitleField, layoutParamsLine);//things and place

        //------------------ Education ------------------
        TextView educationTitle = new TextView(this);
        educationTitle.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        educationTitle.setText("Your education");
        educationTitle.setTextSize(12);
        educationTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(educationTitle, layoutParamsTitleField);

        RelativeLayout educationLayout = new RelativeLayout(this);
        listOfEdits.addView(educationLayout, new LinearLayout.LayoutParams(-1, -2));

        educationLayout.setId(LINE_EDUC_ID);

        addEducationItem(0, educationLayout);

        lineEduc = new View(this);
        lineEduc.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        listOfEdits.addView(lineEduc, layoutParamsLine);

    }

    private void setForStudent(){
        LinearLayout.LayoutParams layoutParamsTitleField = new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsEditField= new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(
                -1, 2);

        layoutParamsTitleField.setMargins(80,30,80,0);
        layoutParamsEditField.setMargins(80, 0, 80,0);
        layoutParamsLine.setMargins(80, 0, 80, 0);

        //----------------- second name ------------------------

        TextView secondNameTitle = new TextView(this);
        secondNameTitle.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        secondNameTitle.setText("Second Name");
        secondNameTitle.setTextSize(12);
        secondNameTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(secondNameTitle, layoutParamsTitleField);

        EditText secondName = new EditText(this);
        secondName.setTextColor(getResources().getColor(R.color.textColorDark));
        secondName.setHint("Enter your second name");
        secondName.setTextSize(20);
        secondName.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(secondName, layoutParamsEditField);

        secondName.setId(LINE_SECNAME_ID);
        secondName.setOnFocusChangeListener(this);

        lineSecName = new View(this);
        lineSecName.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        listOfEdits.addView(lineSecName, layoutParamsLine);

        //-------------  Year of education --------------------

        TextView patronymicTitle = new TextView(this);
        patronymicTitle.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        patronymicTitle.setText("Year of study");
        patronymicTitle.setTextSize(12);
        patronymicTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(patronymicTitle, layoutParamsTitleField);

        RelativeLayout yearOfEducLayout = new RelativeLayout(this);
        listOfEdits.addView(yearOfEducLayout, new LinearLayout.LayoutParams(-1, -2));

        yearOfEducLayout.setId(LINE_PATR_ID);
//        thingsLayout.setOnFocusChangeListener(this);

        LinearLayout.LayoutParams layoutParamsSpinner = new LinearLayout.LayoutParams(-1, -2);

        Spinner spinner = new Spinner(this);
        setYearOfEducSpinner(spinner, yearOfEducLayout);

        layoutParamsSpinner.setMargins(80, 0, 80, 0);
        layoutParamsSpinner.weight = 3;

        yearOfEducLayout.addView(spinner, layoutParamsSpinner);

        linePatr = new View(this);
        linePatr.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        listOfEdits.addView(linePatr, layoutParamsLine);

        commonPart(layoutParamsTitleField, layoutParamsLine);
    }


    private int addThingItem(int prevId, final RelativeLayout thingsLayout){
        final LinearLayout thingItem = new LinearLayout(this);
        thingItem.setOrientation(LinearLayout.HORIZONTAL);

        RelativeLayout.LayoutParams layoutParamsThingItem = new RelativeLayout.LayoutParams(-1, -2);
        LinearLayout.LayoutParams layoutParamsPlusBtn = new LinearLayout.LayoutParams(50, 50);
        LinearLayout.LayoutParams layoutParamsSpinner = new LinearLayout.LayoutParams(0, -2);

        final int newId;
        if (prevId == 0){
            newId = 2000;
        } else {
            newId = prevId + 1;
            layoutParamsThingItem.addRule(RelativeLayout.BELOW, prevId);
        }
        thingItem.setId(newId);


        final ImageView plusThingBtn = new ImageView(this);
        plusThingBtn.setId(newId + 1000);
        plusThingBtn.setImageResource(R.drawable.ic_plus);
        plusThingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFocusChange(thingsLayout, true);
                plusThingBtn.setEnabled(false);
                plusThingBtn.setColorFilter(Color.WHITE);
                addThingItem(newId, thingsLayout);
            }
        });
        layoutParamsPlusBtn.gravity = Gravity.CENTER_VERTICAL;
        layoutParamsPlusBtn.setMargins(10, 0, 10, 0);

        Spinner spinner = new Spinner(this);
        setThingsSpinner(spinner, thingsLayout);
        //listOfEdits.addView(spinner, layoutParamsEditField);
        layoutParamsSpinner.setMargins(10, 0, 80, 0);
        layoutParamsSpinner.weight = 2;

        thingItem.addView(plusThingBtn, layoutParamsPlusBtn);
        thingItem.addView(spinner, layoutParamsSpinner);

        ImageView crossBtn = new ImageView(this);
        crossBtn.setImageResource(R.drawable.ic_close);
        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFocusChange(thingsLayout, true);
                if (plusThingBtn.isEnabled()){
                    int i = 1;
                    while (true){
                        LinearLayout ll = findViewById(newId - i);
                        if (ll.getVisibility() != View.GONE){
                            break;
                        }
                        i ++;
                    }
                    ImageView iv = findViewById(newId - i + 1000);
                    iv.setEnabled(true);
                    iv.setColorFilter(android.R.color.darker_gray);
                }
                thingItem.setVisibility(View.GONE);
            }
        });
        thingItem.addView(crossBtn, layoutParamsPlusBtn);

        if (prevId == 0){
            crossBtn.setEnabled(false);
            crossBtn.setColorFilter(Color.WHITE);
        }

        thingsLayout.addView(thingItem, layoutParamsThingItem);

        return newId;
    }

    private void commonPart(LinearLayout.LayoutParams layoutParamsTitleField, LinearLayout.LayoutParams
                            layoutParamsLine){
        //---------------- Relative Things -------------
        TextView thingsTitle = new TextView(this);
        thingsTitle.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        thingsTitle.setText("Subjects you teach");
        thingsTitle.setTextSize(12);
        thingsTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(thingsTitle, layoutParamsTitleField);

        RelativeLayout thingsLayout = new RelativeLayout(this);
        listOfEdits.addView(thingsLayout, new LinearLayout.LayoutParams(-1, -2));

        thingsLayout.setId(LINE_SUBJ_ID);
        thingsLayout.setOnFocusChangeListener(this);

        addThingItem(0, thingsLayout);

        lineSubject = new View(this);
        lineSubject.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        listOfEdits.addView(lineSubject, layoutParamsLine);

        //------------------- Radio Group ---------------
        TextView ifGoOutTitle = new TextView(this);
        ifGoOutTitle.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        ifGoOutTitle.setText("The place of lessons");
        ifGoOutTitle.setTextSize(12);
        ifGoOutTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(ifGoOutTitle, layoutParamsTitleField);

        RelativeLayout placeLayout = new RelativeLayout(this);
        listOfEdits.addView(placeLayout, new LinearLayout.LayoutParams(-1, -2));
        placeLayout.setId(LINE_PLACE_ID);

        setRadioBtnGroup(placeLayout);

        linePlace = new View(this);
        linePlace.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        listOfEdits.addView(linePlace, layoutParamsLine);

    }


    private void addEducationItem(int prevId, final RelativeLayout educationLayout){
        final LinearLayout educationBlock = new LinearLayout(this);
        educationBlock.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout educationItem = new LinearLayout(this);
        educationItem.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout facultyAndYear = new LinearLayout(this);
        educationItem.setOrientation(LinearLayout.HORIZONTAL);

        RelativeLayout.LayoutParams layoutParamsEducationBlock= new RelativeLayout.LayoutParams(-1, -2);
        LinearLayout.LayoutParams layoutParamsPlusBtn = new LinearLayout.LayoutParams(50, 50);
        LinearLayout.LayoutParams layoutParamsEditText = new LinearLayout.LayoutParams(0, -2);

        educationBlock.addView(educationItem, new LinearLayout.LayoutParams(-1, -2));
        educationBlock.addView(facultyAndYear, new LinearLayout.LayoutParams(-1, -2));

        final int newId;
        if (prevId == 0){
            newId = 5000;
        } else {
            newId = prevId + 1;
            layoutParamsEducationBlock.addRule(RelativeLayout.BELOW, prevId);
        }
        educationBlock.setId(newId);

        //*********education item

        final ImageView plusThingBtn = new ImageView(this);
        plusThingBtn.setId(newId + 1000);
        plusThingBtn.setImageResource(R.drawable.ic_plus);
        plusThingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFocusChange(educationLayout, true);
                plusThingBtn.setEnabled(false);
                plusThingBtn.setColorFilter(Color.WHITE);
                addEducationItem(newId, educationLayout);
            }
        });
        layoutParamsPlusBtn.gravity = Gravity.CENTER_VERTICAL;
        layoutParamsPlusBtn.setMargins(10, 0, 10, 0);

        EditText editText = new EditText(this);
        editText.setTextColor(Color.BLACK);
        editText.setTextSize(20);
        editText.setHint("Enter your university");
        editText.setBackground(getDrawable(R.drawable.border_white));
        editText.setId(LINE_EDUC_ID);
        editText.setOnFocusChangeListener(this);

        layoutParamsEditText.setMargins(10, 0, 80, 0);
        layoutParamsEditText.weight = 2;

        educationItem.addView(plusThingBtn, layoutParamsPlusBtn);
        educationItem.addView(editText, layoutParamsEditText);

        ImageView crossBtn = new ImageView(this);
        crossBtn.setImageResource(R.drawable.ic_close);
        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFocusChange(educationLayout, true);
                if (plusThingBtn.isEnabled()){
                    int i = 1;
                    while (true){
                        LinearLayout ll = findViewById(newId - i);
                        if (ll.getVisibility() != View.GONE){
                            break;
                        }
                        i ++;
                    }
                    ImageView iv = findViewById(newId - i + 1000);
                    iv.setEnabled(true);
                    iv.setColorFilter(android.R.color.darker_gray);
                }
                educationBlock.setVisibility(View.GONE);
            }
        });
        educationItem.addView(crossBtn, layoutParamsPlusBtn);

        if (prevId == 0){
            crossBtn.setEnabled(false);
            crossBtn.setColorFilter(Color.WHITE);
        }

        //************ faculty and year row

        LinearLayout.LayoutParams layoutParamsFaculty = new LinearLayout.LayoutParams(0, -2);
        LinearLayout.LayoutParams layoutParamsYear = new LinearLayout.LayoutParams(0, -2);

        layoutParamsFaculty.setMargins(80, 10, 10, 0);
        layoutParamsYear.setMargins(10, 10, 80, 0);

        layoutParamsFaculty.weight = 2;
        layoutParamsYear.weight = 1;

        EditText faculty = new EditText(this);
        faculty.setTextColor(Color.BLACK);
        faculty.setTextSize(20);
        faculty.setHint("Faculty");
        faculty.setBackground(getDrawable(R.drawable.border_white));

        faculty.setId(LINE_EDUC_ID);
        faculty.setOnFocusChangeListener(this);

        EditText year = new EditText(this);
        year.setTextColor(Color.BLACK);
        year.setTextSize(20);
        year.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        year.setHint("Ending year");
        year.setBackground(getDrawable(R.drawable.border_white));

        year.setId(LINE_EDUC_ID);
        year.setOnFocusChangeListener(this);

        facultyAndYear.addView(faculty, layoutParamsFaculty);
        facultyAndYear.addView(year, layoutParamsYear);

        //*************


        educationLayout.addView(educationBlock, layoutParamsEducationBlock);
    }

    private void setYearOfEducSpinner(Spinner spinner, final RelativeLayout thingLayout){
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < 11; i ++){
            data.add((i + 1) + " year");
        }

        final CustomSimpleSpinnerAdapter customSpinnerAdapter = new CustomSimpleSpinnerAdapter(this, data);
        spinner.setAdapter(customSpinnerAdapter);

        spinner.setSelected(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (customSpinnerAdapter.ifUsed){
                    onFocusChange(thingLayout, true);
                }
                customSpinnerAdapter.ifUsed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setThingsSpinner(Spinner spinner, final RelativeLayout thingLayout){
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < Themes.Theme.values().length; i ++){
            data.add(Themes.Theme.values()[i].getDescription());
        }

        final CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(this, data);
        spinner.setAdapter(customSpinnerAdapter);

        spinner.setSelected(false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (customSpinnerAdapter.ifUsed){
                    onFocusChange(thingLayout, true);
                }
                customSpinnerAdapter.ifUsed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setRadioBtnGroup(final RelativeLayout placeLayout){
        final RadioGroup ifGoOut = new RadioGroup(this);
        RelativeLayout.LayoutParams layoutParamsRadio = new RelativeLayout.LayoutParams(-1, -2);
        layoutParamsRadio.setMargins(80, 10, 80, 0);
        ifGoOut.setOrientation(RadioGroup.HORIZONTAL);

        final RadioButton goOut = new RadioButton(this);
        RadioButton atHome = new RadioButton(this);
        ifGoOut.setId(RADIO_BTN_GROUP_ID);
        goOut.setId(RADIO_GO_OUT_ID);
        atHome.setId(RADIO_AT_HOME_ID);
        goOut.setText("I go out");
        atHome.setText("At my place");
        goOut.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        atHome.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
        goOut.setTextSize(20);
        atHome.setTextSize(20);

        ifGoOut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                RadioButton uncheckedRb;
                if (checkedId == RADIO_AT_HOME_ID){
                    //сделать автокомплит для адреса
                    if (mSearchText == null) {
                        setAutocompleteField(placeLayout, ifGoOut.getId());
                    } else {
                        mSearchText.setVisibility(View.VISIBLE);
                    }
                    mSearchText.requestFocus();
                    rb.setTextColor(Color.BLACK);
                    uncheckedRb = group.findViewById(RADIO_GO_OUT_ID);
                    uncheckedRb.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
                } else {
                    rb.setTextColor(Color.BLACK);
                    uncheckedRb = group.findViewById(RADIO_AT_HOME_ID);
                    uncheckedRb.setTextColor(getResources().getColor(R.color.notActiveTextColorDark));
                    if (mSearchText != null){
                        mSearchText.setVisibility(View.GONE);
                    }
                }
                onFocusChange(placeLayout, true);
            }
        });

        RadioGroup.LayoutParams layoutParamsRadioBtn1 = new RadioGroup.LayoutParams(-2, -2);
        layoutParamsRadioBtn1.setMargins(0, 10, 0, 10);
        ifGoOut.addView(goOut, layoutParamsRadioBtn1);
        RadioGroup.LayoutParams layoutParamsRadioBtn = new RadioGroup.LayoutParams(-2, -2);
        layoutParamsRadioBtn.setMargins(80, 10, 0, 10);
        ifGoOut.addView(atHome, layoutParamsRadioBtn);

        placeLayout.addView(ifGoOut, layoutParamsRadio);
    }

    private void setAutocompleteField(RelativeLayout placeLayout, int prevId){
        RelativeLayout.LayoutParams layoutParamsAutocomplete = new RelativeLayout.LayoutParams(
                -1, -2);
        layoutParamsAutocomplete.setMargins(80, 10, 80, 0);
        layoutParamsAutocomplete.addRule(RelativeLayout.BELOW, prevId);
        mSearchText = new AutoCompleteTextView(this);
        mSearchText.setTextSize(20);
        mSearchText.setTextColor(Color.BLACK);
        mSearchText.setBackground(getDrawable(R.drawable.border_white));
        mSearchText.setHint("Enter address");

        mSearchText.setId(LINE_PLACE_ID);
        mSearchText.setOnFocusChangeListener(this);
//        android:imeOptions="actionSearch"/>


        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        //mGoogleApiClient.connect();

       // mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        placeLayout.addView(mSearchText, layoutParamsAutocomplete);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CalendarActivity.hideKeyBoardTouchEveryWhere(findViewById(R.id.edit_profile_parent),
                    EditProfileActivity.this);

//            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(position);
//            final String placeId = item.getPlaceId();

//            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
//                    .getPlaceById(mGoogleApiClient, placeId);
//            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(TAG, "onFocusChange: focus has changed" + v.getId() + " " + hasFocus);
        allLinesSetDark();
        //if (hasFocus) {
            switch (v.getId()) {
                case R.id.first_name_edit: {
                    if (hasFocus) {
                        lineFirstName.setBackgroundColor(getResources().getColor(R.color.accent));
                    }
                    break;
                }
                case LINE_PATR_ID: {
                    if (hasFocus) {
                        linePatr.setBackgroundColor(getResources().getColor(R.color.accent));
                    }
                    break;
                }
                case LINE_SECNAME_ID: {
                    if (hasFocus) {
                        lineSecName.setBackgroundColor(getResources().getColor(R.color.accent));
                    }
                    break;
                }
                case LINE_SUBJ_ID: {
                    if (hasFocus) {
                        lineSubject.setBackgroundColor(getResources().getColor(R.color.accent));
                    }
                    break;
                }
                case LINE_PLACE_ID: {
                    if (hasFocus) {
                        linePlace.setBackgroundColor(getResources().getColor(R.color.accent));
                    }
                    break;
                }
                case LINE_EDUC_ID: {
                    if (hasFocus) {
                        lineEduc.setBackgroundColor(getResources().getColor(R.color.accent));
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        //}
    }

    private void allLinesSetDark(){
        lineFirstName.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        linePatr.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        lineSecName.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        lineSubject.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        linePlace.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        if (lineEduc != null) {
            lineEduc.setBackgroundColor(getResources().getColor(R.color.primary_dark));
        }
    }
}
