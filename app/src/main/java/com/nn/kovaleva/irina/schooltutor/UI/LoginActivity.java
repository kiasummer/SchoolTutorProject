package com.nn.kovaleva.irina.schooltutor.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.R;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;

public class LoginActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    
    private EditText userNameEdit, passwordEdit;
    private Button btnLogin;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: creating login activity");
        setContentView(R.layout.activity_login);
        CalendarActivity.hideKeyBoardTouchEveryWhere(findViewById(R.id.login_parent),
                LoginActivity.this);

        Intent intent = getIntent();
        String toast = intent.getStringExtra("status");
        if (toast != null){
            Toast toastNew = Toast.makeText(this, toast, Toast.LENGTH_SHORT);
            toastNew.show();
        }
        userNameEdit = findViewById(R.id.login_username);
        passwordEdit = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_login);
        signUp = findViewById(R.id.asking_if_signed);

        btnLogin.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this,
                CalendarActivity.class);
        intent.putExtra("message", "exit");
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:{
                if (ifSmthNotFilled() == false){
                    Toast.makeText(this, "Please, fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Controller.getsInstance().logIn(userNameEdit.getText().toString(),
                            passwordEdit.getText().toString(), new OnRequestResult() {
                                @Override
                                public void onResponse(JsonBaseResponse response) {
                                    Intent intent = new Intent(LoginActivity.this,
                                            CalendarActivity.class);
                                    String toast;
                                    if (response != null && response.errorCode == 0) {
                                        setResult(RESULT_OK, intent);
                                        Actor.getsInstance().login = "aaa";
                                        Actor.getsInstance().password = "aaa";
                                        Actor.getsInstance().ifTutor = true;
                                    } else if (response == null) {
                                        toast = "Error";
                                        intent.putExtra("message", toast);
                                        setResult(RESULT_CANCELED, intent);
                                    } else {
                                        toast = response.message;
                                        intent.putExtra("message", toast);
                                        setResult(RESULT_CANCELED, intent);
                                    }
                                    LoginActivity.this.finish();
                                }
                            });
                }
                break;
            }
            case R.id.asking_if_signed:{
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivityForResult(intent, 1);
                break;
            }
        }
    }

    private boolean ifSmthNotFilled(){
        if ((userNameEdit.getText().length() == 0)
                || (passwordEdit.getText().length() == 0)){
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: activity result is called");
        if (resultCode == RESULT_OK){
            Toast.makeText(this, data.getStringExtra("status"), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
