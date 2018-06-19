
package com.nn.kovaleva.irina.schooltutor.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.ChatMessage;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.Model.Lesson;
import com.nn.kovaleva.irina.schooltutor.Model.MessageList;
import com.nn.kovaleva.irina.schooltutor.Model.Themes;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.R;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.ChatAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.CustomSimpleSpinnerAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.CustomSpinnerAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.MessageAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.PlaceAutocompleteAdapter;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener{
    public static final String TAG = "ChatActivity";

    private String fistName, surname, partnerAddress;
    private int partnetId;
    private MessageList messageList;
    private List<User> users = new ArrayList<>();
    private TextView partnerName, date;
    private ImageView backBtn, sendBtn, addLessonBtn;
    private EditText textField, priceForLesson;
    private User user, actor;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    RelativeLayout thingsLayout, educationLayout;
    View dialogView;
    Spinner spinner, spinnerDays, spinnerThing;
    CustomSimpleSpinnerAdapter customSpinnerAdapter, customSpinnerAdapterDate;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private AutoCompleteTextView mSearchText = null;
    private View lineSubject = null, linePlace = null, linePatr = null, lineEduc = null, lineCost = null;

    private String subject = "", time = "", address = "";
    private int price, duration, dayOfWeek;
    final int hour = 12;
    final int minutes = 0;

    TimePickerDialog.OnTimeSetListener timeSetListener;

    final public static int LINE_SUBJ_ID = 102;
    final public static int LINE_COST_ID = 105;
    final public static int FIRST_SPINNER_ID = 500;
    final public static int FIRST_EDUCATION_ID = 600;
    public static int last_education_id = 600;
    final public static int RADIO_GROUP = 3999;
    final public static int RADIO_AT_MY = 4000;
    final public static int RADIO_AT_HIS = 4001;
    final public static int RADIO_AT_OTHER = 4002;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: creating login activity");
        setContentView(R.layout.chat_activity);
        CalendarActivity.hideKeyBoardTouchEveryWhere(findViewById(R.id.chat_activity_parent),
                ChatActivity.this);

        partnerName = findViewById(R.id.chat_partner_name);
        backBtn = findViewById(R.id.back_from_chat);
        backBtn.setOnClickListener(this);
        sendBtn = findViewById(R.id.ic_magnify_chat_frag);
        textField = findViewById(R.id.input_search);
        addLessonBtn = findViewById(R.id.add_lesson);
        Intent intent = getIntent();
        //int secondId = intent.getIntExtra("secondName", -1);
        fistName = intent.getStringExtra("firstName");
        surname = intent.getStringExtra("surname");
        partnetId = intent.getIntExtra("partnerId", -1);
        partnerAddress = intent.getStringExtra("partnerAddress");

        recyclerView = findViewById(R.id.recycler_view_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter();
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sendBtn.setOnClickListener(this);
        addLessonBtn.setOnClickListener(this);

        user = new User();
        actor = new User();
        user.userId = partnetId;
        user.firstName = fistName;
        user.secondName = surname;
        actor.firstName = Actor.getsInstance().firstName;
        actor.secondName = Actor.getsInstance().secondName;

        partnerName.setText(user.firstName + " " + user.secondName);

        Controller.getsInstance().getChatHistory(Actor.getsInstance().id,
                partnetId, new OnRequestResult() {
                    @Override
                    public void onResponse(JsonBaseResponse response) {
                        if (response != null && response.errorCode == 0) {
                            messageList = (MessageList) response;
                            for (int i = 0; i < messageList.messageList.size(); i ++){
                                ChatMessage message = messageList.messageList.get(i);
                                if (message.author_id == Actor.getsInstance().id){
                                    users.add(actor);
                                } else {
                                    users.add(user);
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    messageAdapter.setItems(users, messageList.messageList);
                                    recyclerView.scrollToPosition(users.size() - 1);
                                }
                            });
                        } else {
                            Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }

                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_magnify_chat_frag:{
                Controller.getsInstance().sendMessage(Actor.getsInstance().id, partnetId,
                        textField.getText().toString(), new OnRequestResult() {
                            @Override
                            public void onResponse(JsonBaseResponse response) {
                                if (response != null && response.errorCode == 0) {
                                    Log.d(TAG, "onResponse: message send");
                                    ChatMessage chatMessage = (ChatMessage) response;
                                    textField.setText("");
                                    messageList.messageList.add(chatMessage);
                                    if (chatMessage.author_id == Actor.getsInstance().id){
                                        users.add(actor);
                                    } else {
                                        users.add(user);
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            messageAdapter.setItems(users, messageList.messageList);
                                            recyclerView.scrollToPosition(users.size() - 1);
                                        }
                                    });
                                } else if (response == null) {
                                    Toast.makeText(ChatActivity.this, "Error",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    if (response.errorCode != 400) {
                                        Toast.makeText(ChatActivity.this,
                                                response.message, Toast.LENGTH_SHORT).show();
                                    } else {
                                    }
                                }
                            }
                        });
                break;
            }
            case R.id.back_from_chat:{
                finish();
                break;
            }
            case R.id.add_lesson:{
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                dialogView = layoutInflater.inflate(R.layout.add_lesson_dialog, null);
                createDialogView(dialogView);
                mDialogBuilder.setView(dialogView);

                mDialogBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (ifAllFilled()){
                                    Lesson lesson = new Lesson();
                                    User actor = User.getUserByActor();
                                    if (Actor.getsInstance().ifTutor){
                                        lesson.tutor = actor;
                                        lesson.student = user;
                                    } else {
                                        lesson.tutor = user;
                                        lesson.student = actor;
                                    }
                                    lesson.duration = duration;
                                    lesson.startTime = time;
                                    lesson.theme = subject;
                                    lesson.address = address;
                                    lesson.cost = price;
                                    lesson.startTime += " " + dayOfWeek;
                                    Controller.getsInstance().addLesson(lesson, new OnRequestResult() {
                                        @Override
                                        public void onResponse(final JsonBaseResponse response) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (response != null && response.errorCode == 0) {
                                                        Toast.makeText(ChatActivity.this, "Lesson added",
                                                                Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    });
                                } else {
                                    Toast.makeText(ChatActivity.this,
                                            "Please, fill all fields", Toast.LENGTH_SHORT).show();
                                }
//                                Controller.getsInstance().sendMessage(Actor.getsInstance().id,
//                                        user.userId, messageField.getText().toString(),
//                                        new OnRequestResult() {
//                                            @Override
//                                            public void onResponse(final JsonBaseResponse response) {
//                                                getActivity().runOnUiThread(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        if (response != null && response.errorCode == 0) {
//                                                            Toast.makeText(getActivity(),
//                                                                    "Request send", Toast.LENGTH_SHORT).show();
//                                                        } else if (response == null) {
//                                                            Toast.makeText(getActivity(), "Error",
//                                                                    Toast.LENGTH_SHORT).show();
//                                                        } else {
//                                                            if (response.errorCode != 400) {
//                                                                Toast.makeText(getActivity(),
//                                                                        response.message, Toast.LENGTH_SHORT).show();
//                                                            } else {
//                                                            }
//                                                        }
//                                                    }
//                                                });
//                                            }
//                                        });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();
                break;
            }
            default:{
                break;
            }
        }
    }

    private boolean ifAllFilled(){
        subject = ((CustomSpinnerAdapter) spinnerThing.getAdapter()).
                getSelectedString(spinnerThing);
        if (subject.equals("")){
            return false;
        }
        if (priceForLesson.getText().equals("")){
            return false;
        } else {
            price = Integer.parseInt(priceForLesson.getText().toString());
        }
        int duration = ((CustomSimpleSpinnerAdapter) spinner.getAdapter()).
                getSelectedPosition(spinner);
        if (duration == 0){
            return false;
        } else {
            this.duration = (duration + 1) * 15;
        }
        int dayOfWeek = ((CustomSimpleSpinnerAdapter) spinnerDays.getAdapter()).
                getSelectedPosition(spinnerDays);
        if (dayOfWeek == 0){
            return false;
        } else {
            this.dayOfWeek = dayOfWeek;
        }
        String time = date.getText().toString();
        if (time.equals("")){
            return false;
        } else {
            this.time = time;
        }

        if (mSearchText == null || mSearchText.getVisibility() == View.GONE ||
                mSearchText.getText().equals("")){
            return false;
        } else {
            this.address = mSearchText.getText().toString();
        }
        return true;
    }

    private void createDialogView(View view){
        LinearLayout listOfEdits = view.findViewById(R.id.add_lesson_list);
        LinearLayout.LayoutParams layoutParamsTitleField = new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsEditField= new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(
                -1, 2);

        layoutParamsTitleField.setMargins(80,30,80,0);
        layoutParamsEditField.setMargins(80, 0, 80,0);
        layoutParamsLine.setMargins(80, 0, 80, 0);

        //---------------- Relative Things -------------
        TextView thingsTitle = new TextView(this);
        thingsTitle.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        thingsTitle.setText("Subject");
        thingsTitle.setTextSize(12);
        thingsTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(thingsTitle, layoutParamsTitleField);

        thingsLayout = new RelativeLayout(this);
        listOfEdits.addView(thingsLayout, new LinearLayout.LayoutParams(-1, -2));

//        thingsLayout.setId(LINE_SUBJ_ID);
//        thingsLayout.setOnFocusChangeListener(this);

        addThingItem(0, thingsLayout);

        lineSubject = new View(this);
        lineSubject.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
        listOfEdits.addView(lineSubject, layoutParamsLine);

        //----------------- price ------------------------

        TextView costTitle = new TextView(this);
        costTitle.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        costTitle.setText("Price");
        costTitle.setTextSize(12);
        costTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(costTitle, layoutParamsTitleField);

        priceForLesson = new EditText(this);
        priceForLesson.setTextColor(ContextCompat.getColor(this, R.color.textColorDark));
        priceForLesson.setHint("Price of the lesson");
        priceForLesson.setTextSize(20);
        priceForLesson.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(priceForLesson, layoutParamsEditField);

//        priceForLesson.setId(LINE_COST_ID);
//        priceForLesson.setOnFocusChangeListener(this);

        lineCost = new View(this);
        lineCost.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
        listOfEdits.addView(lineCost, layoutParamsLine);

        //-------------  Duration --------------------

        TextView patronymicTitle = new TextView(this);
        patronymicTitle.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        patronymicTitle.setText("Duration");
        patronymicTitle.setTextSize(12);
        patronymicTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(patronymicTitle, layoutParamsTitleField);

        RelativeLayout yearOfEducLayout = new RelativeLayout(this);
        listOfEdits.addView(yearOfEducLayout, new LinearLayout.LayoutParams(-1, -2));

        RelativeLayout.LayoutParams layoutParamsSpinner = new RelativeLayout.LayoutParams(-1, -2);

        spinner = new Spinner(this);
        setYearOfEducSpinner(spinner, yearOfEducLayout);

        layoutParamsSpinner.setMargins(10, 0, 80, 0);

        yearOfEducLayout.addView(spinner, layoutParamsSpinner);

        linePatr = new View(this);
        linePatr.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
        listOfEdits.addView(linePatr, layoutParamsLine);

        //------------------ Times ------------------
        TextView educationTitle = new TextView(this);
        educationTitle.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        educationTitle.setText("Time of lesson");
        educationTitle.setTextSize(12);
        educationTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(educationTitle, layoutParamsTitleField);

        educationLayout = new RelativeLayout(this);
        //educationLayout = new LinearLayout(this);
        //educationLayout.setOrientation(LinearLayout.VERTICAL);
        listOfEdits.addView(educationLayout, new LinearLayout.LayoutParams(-1, -2));

        spinnerDays = new Spinner(this);
        setDaysSpinner(spinnerDays, educationLayout);

        layoutParamsSpinner.setMargins(10, 0, 80, 0);
        spinnerDays.setId(last_education_id);
        educationLayout.addView(spinnerDays, layoutParamsSpinner);

        LinearLayout.LayoutParams layoutParamsDate = new LinearLayout.LayoutParams(-1, -2);
        layoutParamsDate.setMargins(80, 10, 80, 0);

        date = new TextView(this);
        date.setTextColor(ContextCompat.getColor(this, R.color.textColorDark));
        date.setHint("__ : __");
        date.setTextSize(20);
        date.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(date, layoutParamsDate);

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                date.setText((hourOfDay < 10 ? "0" : "") + hourOfDay + " : "
                + (minute < 10 ? "0" : "") + minute);
            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
        lineEduc = new View(this);
        lineEduc.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
        listOfEdits.addView(lineEduc, layoutParamsLine);

        //------------------- Radio Group ---------------
        TextView ifGoOutTitle = new TextView(this);
        ifGoOutTitle.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        ifGoOutTitle.setText("The place of lesson");
        ifGoOutTitle.setTextSize(12);
        ifGoOutTitle.setBackground(getDrawable(R.drawable.border_white));
        listOfEdits.addView(ifGoOutTitle, layoutParamsTitleField);

        RelativeLayout placeLayout = new RelativeLayout(this);
        listOfEdits.addView(placeLayout, new LinearLayout.LayoutParams(-1, -2));
 //       placeLayout.setId(LINE_PLACE_ID);

        setRadioBtnGroup(placeLayout);

        linePlace = new View(this);
        linePlace.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
        listOfEdits.addView(linePlace, layoutParamsLine);


    }

    private void setRadioBtnGroup(final RelativeLayout placeLayout){
        final RadioGroup ifGoOut = new RadioGroup(this);
        RelativeLayout.LayoutParams layoutParamsRadio = new RelativeLayout.LayoutParams(-1, -2);
        layoutParamsRadio.setMargins(80, 10, 80, 0);
        ifGoOut.setOrientation(RadioGroup.VERTICAL);

        final RadioButton atMy = new RadioButton(this);
        RadioButton atHis = new RadioButton(this);
        RadioButton atOther = new RadioButton(this);
        ifGoOut.setId(RADIO_GROUP);
        atMy.setId(RADIO_AT_MY);
        atHis.setId(RADIO_AT_HIS);
        atOther.setId(RADIO_AT_OTHER);
        atMy.setText("At my place");
        atHis.setText("At companion place");
        atOther.setText("At other place");
        atMy.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        atHis.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        atOther.setTextColor(ContextCompat.getColor(this, R.color.notActiveTextColorDark));
        atMy.setTextSize(20);
        atHis.setTextSize(20);
        atOther.setTextSize(20);

        if (Actor.getsInstance().address.equals("")){
            atMy.setEnabled(false);
        } else {
            atMy.setEnabled(true);
        }

        if (partnerAddress.equals("")){
            atHis.setEnabled(false);
        } else {
            atHis.setEnabled(true);
        }

        ifGoOut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                RadioButton uncheckedRb;
                if (mSearchText != null) {
                    mSearchText.setVisibility(View.GONE);
                }
                setAutocompleteField(placeLayout, ifGoOut.getId());
                mSearchText.setVisibility(View.VISIBLE);
                switch (checkedId){
                    case RADIO_AT_OTHER:{
                        //сделать автокомплит для адреса
//                        mSearchText = null;
//                        setAutocompleteField(placeLayout, ifGoOut.getId());
//                        mSearchText.setVisibility(View.VISIBLE);
                        mSearchText.setText("");
                        //mSearchText.requestFocus();
                        rb.setTextColor(Color.BLACK);
                        uncheckedRb = group.findViewById(RADIO_AT_MY);
                        uncheckedRb.setTextColor(ContextCompat.getColor(ChatActivity.this,
                                R.color.notActiveTextColorDark));
                        uncheckedRb = group.findViewById(RADIO_AT_HIS);
                        uncheckedRb.setTextColor(ContextCompat.getColor(ChatActivity.this,
                                R.color.notActiveTextColorDark));
                        break;
                    }
                    case RADIO_AT_MY:{
                        rb.setTextColor(Color.BLACK);
                        uncheckedRb = group.findViewById(RADIO_AT_OTHER);
                        uncheckedRb.setTextColor(ContextCompat.getColor(ChatActivity.this,
                                R.color.notActiveTextColorDark));
                        uncheckedRb = group.findViewById(RADIO_AT_HIS);
                        uncheckedRb.setTextColor(ContextCompat.getColor(ChatActivity.this,
                                R.color.notActiveTextColorDark));
//                        mSearchText = null;
//                        setAutocompleteField(placeLayout, ifGoOut.getId());
//                        mSearchText.setVisibility(View.VISIBLE);
                        mSearchText.setText(Actor.getsInstance().address);
                        break;
                    }
                    case RADIO_AT_HIS:{
                        rb.setTextColor(Color.BLACK);
                        uncheckedRb = group.findViewById(RADIO_AT_OTHER);
                        uncheckedRb.setTextColor(ContextCompat.getColor(ChatActivity.this,
                                R.color.notActiveTextColorDark));
                        uncheckedRb = group.findViewById(RADIO_AT_MY);
                        uncheckedRb.setTextColor(ContextCompat.getColor(ChatActivity.this,
                                R.color.notActiveTextColorDark));
//                        mSearchText = null;
//                        setAutocompleteField(placeLayout, ifGoOut.getId());
//                        mSearchText.setVisibility(View.VISIBLE);
                        mSearchText.setText(partnerAddress);
                        break;
                    }
                }
                //onFocusChange(placeLayout, true);
            }
        });

        atOther.setSelected(true);
        RadioGroup.LayoutParams layoutParamsRadioBtn1 = new RadioGroup.LayoutParams(-2, -2);
        layoutParamsRadioBtn1.setMargins(0, 10, 0, 10);
        ifGoOut.addView(atMy, layoutParamsRadioBtn1);
        RadioGroup.LayoutParams layoutParamsRadioBtn = new RadioGroup.LayoutParams(-2, -2);
        layoutParamsRadioBtn.setMargins(0, 10, 0, 10);
        ifGoOut.addView(atHis, layoutParamsRadioBtn);
        RadioGroup.LayoutParams layoutParamsRadioBtn2 = new RadioGroup.LayoutParams(-2, -2);
        layoutParamsRadioBtn.setMargins(0, 10, 0, 10);
        ifGoOut.addView(atOther, layoutParamsRadioBtn2);

        placeLayout.addView(ifGoOut, layoutParamsRadio);
    }

    private int addThingItem(int prevId, final RelativeLayout thingsLayout){
        final LinearLayout thingItem = new LinearLayout(this);
        thingItem.setOrientation(LinearLayout.HORIZONTAL);

        RelativeLayout.LayoutParams layoutParamsThingItem = new RelativeLayout.LayoutParams(-1, -2);
//        LinearLayout.LayoutParams layoutParamsPlusBtn = new LinearLayout.LayoutParams(50, 50);
        LinearLayout.LayoutParams layoutParamsSpinner = new LinearLayout.LayoutParams(0, -2);

        spinnerThing = new Spinner(this);
        spinnerThing.setId(FIRST_SPINNER_ID);
        setThingsSpinner(spinnerThing, thingsLayout);
        //listOfEdits.addView(spinner, layoutParamsEditField);
        layoutParamsSpinner.setMargins(10, 0, 80, 0);
        layoutParamsSpinner.weight = 2;

        thingItem.addView(spinnerThing, layoutParamsSpinner);
        thingsLayout.addView(thingItem, layoutParamsThingItem);

        return 0;
    }

    protected Dialog onCreateDialog(int id){
        TimePickerDialog tpd = new TimePickerDialog(this, timeSetListener, hour, minutes, true);
        return tpd;
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
        mSearchText.setHint("Address");

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(this, this)
                    .build();
        }
        //mGoogleApiClient.connect();


        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);

        placeLayout.addView(mSearchText, layoutParamsAutocomplete);
    }

    private void setThingsSpinner(Spinner spinner, final RelativeLayout thingLayout){
        ArrayList<String> data = new ArrayList<>();
        data.add("Choose a subject...");
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
                    //onFocusChange(thingLayout, true);
                }
                customSpinnerAdapter.ifUsed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setYearOfEducSpinner(Spinner spinner, final RelativeLayout thingLayout){
        ArrayList<String> data = new ArrayList<>();
        data.add("Choose...");
        for (int i = 2; i < 17; i ++){
            if (i < 4) {
                data.add((i * 15) + " min");
            } else {
                int hours =(i/4);
                int minutes = ((i * 15) -  60 * hours);
                data.add(hours + (hours == 1 ? " hour " : " hours ") +
                        ((minutes == 0) ?  "" : (minutes+ " min")));
            }
        }

        customSpinnerAdapter = new CustomSimpleSpinnerAdapter(this, data);
        spinner.setAdapter(customSpinnerAdapter);

        spinner.setSelected(false);
//        spinner.setId(YEAR_OF_EDUC_SPINNER_ID);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (customSpinnerAdapter.ifUsed){
                    //onFocusChange(thingLayout, true);
                }
                customSpinnerAdapter.ifUsed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setDaysSpinner(Spinner spinner, final RelativeLayout thingLayout){
        ArrayList<String> data = new ArrayList<>();
        data.add("Day of week");
        data.add("Monday");
        data.add("Tuesday");
        data.add("Wednesday");
        data.add("Thursday");
        data.add("Saturday");
        data.add("Sunday");

        customSpinnerAdapterDate = new CustomSimpleSpinnerAdapter(this, data);
        spinner.setAdapter(customSpinnerAdapterDate);

        spinner.setSelected(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (customSpinnerAdapterDate.ifUsed){
                }
                customSpinnerAdapterDate.ifUsed = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        Log.d(TAG, "onFocusChange: focus has changed" + v.getId() + " " + hasFocus);
//        int id = v.getId();
////        if (id >= FIRST_EDUCATION_ID && id <= last_education_id){
////            id = LINE_EDUC_ID;
////        }
//        allLinesSetDark();
//        //if (hasFocus) {
//        switch (id) {
//            case R.id.first_name_edit: {
//                if (hasFocus) {
//                    lineFirstName.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
//                }
//                break;
//            }
////            case LINE_PATR_ID: {
////                if (hasFocus) {
////                    linePatr.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
////                }
////                break;
////            }
////            case LINE_SECNAME_ID: {
////                if (hasFocus) {
////                    lineSecName.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
////                }
////                break;
////            }
//            case LINE_SUBJ_ID: {
//                if (hasFocus) {
//                    lineSubject.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
//                }
//                break;
//            }
////            case LINE_PLACE_ID: {
////                if (hasFocus) {
////                    linePlace.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
////                }
////                break;
////            }
////            case LINE_EDUC_ID: {
////                if (hasFocus) {
////                    lineEduc.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
////                }
////                break;
////            }
//            case LINE_COST_ID: {
//                if (hasFocus) {
//                    lineCost.setBackgroundColor(ContextCompat.getColor(this, R.color.accent));
//                }
//                break;
//            }
//            default: {
//                break;
//            }
//        }
//        //}
//    }
//
//    private void allLinesSetDark(){
////        lineFirstName.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
////        if (linePatr != null) {
////            linePatr.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
////        }
////        lineSecName.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
//        lineSubject.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
//        lineCost.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
////        linePlace.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
////        if (lineEduc != null) {
////            lineEduc.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_dark));
////        }
//    }
}
