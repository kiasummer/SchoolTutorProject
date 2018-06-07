package com.nn.kovaleva.irina.schooltutor.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.R;

public class EditProfileActivity extends Activity {
    private static final String TAG = "EditProfileActivity";

    private LinearLayout listOfEdits;
    private TextView editProfileTitle;
    private ImageView backBtn;

    private int status;

    final public static int ADD = 0;
    final public static int EDIT = 1;


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

        Intent intent = getIntent();

        status = intent.getIntExtra("status", -1);

        if (Actor.getsInstance().ifTutor){
            setForTutor();
        } else {
            setForStudent();
        }
    }

    private void setForTutor(){
        LinearLayout.LayoutParams layoutParamsTitleField = new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsEditField= new LinearLayout.LayoutParams(
                -1, -2);
        LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(
                -1, 2);

        layoutParamsTitleField.setMargins(40,20,40,0);
        layoutParamsEditField.setMargins(40, 5, 40,0);
        layoutParamsLine.setMargins(40, 0, 40, 0);

        TextView patronymicTitle = new TextView(this);
        patronymicTitle.setTextColor(Color.parseColor("#7c7d7f"));
        patronymicTitle.setText("Patronymic");
        patronymicTitle.setTextSize(12);
        patronymicTitle.setBackground(getResources().getDrawable(R.drawable.border_white));
        listOfEdits.addView(patronymicTitle, layoutParamsTitleField);

        EditText patronymic = new EditText(this);
        patronymic.setTextColor(Color.parseColor("#000000"));
        patronymic.setHint("Enter your patronymic");
        patronymic.setTextSize(20);
        patronymic.setBackground(getResources().getDrawable(R.drawable.border_white));
        listOfEdits.addView(patronymic, layoutParamsEditField);

        View line = new View(this);
        line.setBackgroundColor(Color.parseColor("#455ede"));
        listOfEdits.addView(line, layoutParamsLine);

    }

    private void setForStudent(){

    }
}
