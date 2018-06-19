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
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.ChatMessage;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.Model.Lesson;
import com.nn.kovaleva.irina.schooltutor.Model.LessonsList;
import com.nn.kovaleva.irina.schooltutor.Model.MessageList;
import com.nn.kovaleva.irina.schooltutor.UI.ChatActivity;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;

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

    private LessonsList lessonsList;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
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


        Controller.getsInstance().getUsersLessons(Actor.getsInstance().id, Actor.getsInstance().ifTutor
                , new OnRequestResult() {
            @Override
            public void onResponse(JsonBaseResponse response) {
                if (response != null && response.errorCode == 0) {
                    lessonsList = (LessonsList) response;
                    setLessons();
//                    for (int i = 0; i < lessonsList.lessonList.size(); i ++){
//                        Lesson lesson = lessonsList.lessonList.get(i);
//                        if (message.author_id == Actor.getsInstance().id){
//                            users.add(actor);
//                        } else {
//                            users.add(user);
//                        }
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            messageAdapter.setItems(users, messageList.messageList);
//                            recyclerView.scrollToPosition(users.size() - 1);
//                        }
//                    });
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;

    }

    @SuppressLint("ResourceAsColor")
    private void setLessons(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < lessonsList.lessonList.size(); i ++){
                    Lesson lesson = lessonsList.lessonList.get(i);
                    String date = lesson.startTime;
                    String[] values = date.split(" ");
                    int hours = Integer.parseInt(values[0]);
                    int minutes = Integer.parseInt(values[2]);
                    int dayOfWeek = Integer.parseInt(values[3]) + 1;
                    int duration = lesson.duration;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(curDate);
                    if (calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek){
                        float flHours = ((float)minutes + (float)duration)/60;
                        int numOfHours = (flHours%1 == 0) ? (int)flHours : (int)flHours + 1;
                        for (int j = 0; j < numOfHours; j ++) {
                            final TextView textView = view.findViewById(1300 + hours + j);
                            if (textView != null) {
                                textView.setBackgroundColor(Color.parseColor("#ffd740"));
                                String str = "";
                                if (j == 0) {
                                    String partnerName;
                                    if (Actor.getsInstance().ifTutor) {
                                        partnerName = lesson.student.firstName + " " + lesson.student.secondName;
                                    } else {
                                        partnerName = lesson.tutor.firstName + " " + lesson.tutor.secondName;
                                    }
                                    str += partnerName + " / " + lesson.theme;
                                    if (numOfHours > 1) {
                                        str += "\n";
                                    } else {
                                        str += "/";
                                    }
                                    String startTime = values[0] + " : " + values[2];
                                    str += startTime + " / ";
                                    str += lesson.duration + " min /";
                                    str += lesson.cost + " rub";
                                    if (numOfHours == 1) {
                                        str += "/" + lesson.address;
                                    }
                                    textView.setText(str);
                                } else {
                                    textView.setText(lesson.address);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void clearItems(){
        for (int i = 0; i < 24; i ++){
            TextView textView = view.findViewById(1300 + i);
            if (textView != null){
                textView.setText("");
                textView.setBackgroundColor(Color.WHITE);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void setDayTable(View view){
        LinearLayout.LayoutParams oneHourLayoutParams = new LinearLayout.LayoutParams(
                -1, 100);
        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(
                120, -1);
        LinearLayout.LayoutParams layoutParamsLessons = new LinearLayout.LayoutParams(
                -1, -1);
        LinearLayout.LayoutParams verLineLayoutParams = new LinearLayout.LayoutParams(
                2, -1);
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(
                -1, 2);

        for (int i = 0; i < 24; i ++) {
            LinearLayout oneHour = new LinearLayout(view.getContext());
            oneHour.setBackgroundColor(Color.WHITE);
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

            TextView lessons = new TextView(view.getContext());
            //String str = ((i == 0) ? 12 : i) + ((i < 12) ? " a.m." : " p.m.");
            //hrs.setText(str);
            lessons.setId(1300 + i);
            lessons.setTextColor(Color.parseColor("#455ede")); //primary dark color
            lessons.setTextSize(16);
            lessons.setLayoutParams(layoutParamsLessons);
            lessons.setGravity(Gravity.CENTER);

            View horLine = new View(view.getContext());
            horLine.setId(1500 + i);
            horLine.setLayoutParams(layoutParamsLine);
            horLine.setBackgroundColor(Color.parseColor("#03a9f4"));//primary color

            oneHour.addView(hrs);
            oneHour.addView(verLine);
            oneHour.addView(lessons);

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
                clearItems();
                setLessons();
                break;
            }
            case R.id.next_day: {
                Date nextDate = new Date(curDate.getTime() + dayInMs);
                curDate = nextDate;
                currentDate.setText(md.format(curDate));
                clearItems();
                setLessons();
                break;
            }
        }
    }
}
