package com.nn.kovaleva.irina.schooltutor.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.R;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;

public class SignUpActivity extends Activity implements View.OnClickListener{

    private ImageView btnBack;
    private EditText userNameField, phoneNumberField, firstPasswordField, secondPasswordField;
    private RadioButton tutorButton, studentButton;
    private Button btnSignUp;

//    private boolean ifTutor = false;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        CalendarActivity.hideKeyBoardTouchEveryWhere(findViewById(R.id.sing_up_parent),
                SignUpActivity.this);

        btnBack = findViewById(R.id.back_from_sign_up);
        btnBack.setOnClickListener(this);

        btnSignUp =  findViewById(R.id.btn_sing_up);
        btnSignUp.setOnClickListener(this);


        userNameField = findViewById(R.id.username_sign_up);
        phoneNumberField = findViewById(R.id.phone_num_sign_up);
        firstPasswordField = findViewById(R.id.first_password_sign_up);
        secondPasswordField= findViewById(R.id.second_password_sign_up);

        tutorButton = findViewById(R.id.radioButtonTutor);
        tutorButton.setOnClickListener(this);

        studentButton = findViewById(R.id.radioButtonStudent);
        studentButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_from_sign_up:{
                finish();
                break;
            }
            case R.id.btn_sing_up:{
                if (checkForCorrect()) {
                    boolean ifTutor = tutorButton.isChecked();
                    Controller.getsInstance().register(userNameField.getText().toString(),
                            firstPasswordField.getText().toString(), ifTutor, phoneNumberField.getText().toString(),
                            new OnRequestResult() {
                                @Override
                                public void onResponse(JsonBaseResponse response) {
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    String toast;
                                    if (response != null && response.errorCode == 0) {
                                        toast = "You've signed up";
                                        intent.putExtra("status", toast);
                                        setResult(RESULT_OK, intent);
                                    } else if (response == null) {
                                        toast = "Error";
                                        intent.putExtra("status", toast);
                                        setResult(RESULT_CANCELED, intent);
                                    } else {
                                        toast = response.message;
                                        intent.putExtra("status", toast);
                                        setResult(RESULT_CANCELED, intent);
                                    }
                                    SignUpActivity.this.finish();
                                }
                            });
//                    Intent intent = new Intent(this, EditProfileActivity.class);
//                    intent.putExtra("status", EditProfileActivity.ADD);
//                    startActivityForResult(intent, 1);
                }
                break;
            }
//            case R.id.radioButtonTutor:{
//                ifTutor = true;
//                break;
//            }
//            case R.id.radioButtonStudent:{
//                ifTutor = false;
//                break;
//            }
//            case R.id.username_sign_up:{
//
//                break;
//            }
//            case R.id.phone_num_sign_up:{
//                break;
//            }
//            case R.id.first_password_sign_up:{
//                break;
//            }
//            case R.id.second_password_sign_up:{
//                break;
//            }

        }
    }


    private boolean checkForCorrect(){ //нужно добавить проверку на уникальность
        if (ifSmthNotFilled() == false){
            Toast.makeText(this, "Please, fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        String firstPass = firstPasswordField.getText().toString();
        String secondPass = secondPasswordField.getText().toString();
        if (!firstPass.equals(secondPass)){
            Toast.makeText(this, "Passwords are different", Toast.LENGTH_SHORT).show();
            firstPasswordField.setText("");
            secondPasswordField.setText("");
            firstPasswordField.requestFocus();
            return false;
        }
        return true;
    }


    private boolean ifSmthNotFilled(){
        if ((userNameField.getText().length() == 0)
                || (phoneNumberField.getText().length() == 0)
                || (firstPasswordField.getText().length() == 0)
                || (secondPasswordField.getText().length() == 0)
                || (!tutorButton.isChecked() && !studentButton.isChecked())){
            return false;
        }
        return true;
    }


}
