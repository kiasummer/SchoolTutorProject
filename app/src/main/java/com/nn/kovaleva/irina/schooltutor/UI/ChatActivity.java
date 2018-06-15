package com.nn.kovaleva.irina.schooltutor.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.R;

public class ChatActivity extends Activity {
    public static final String TAG = "ChatActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: creating login activity");
        setContentView(R.layout.chat_activity);
        CalendarActivity.hideKeyBoardTouchEveryWhere(findViewById(R.id.chat_activity_parent),
                ChatActivity.this);

        Intent intent = getIntent();
        String toast = intent.getStringExtra("status");
        if (toast != null){
            Toast toastNew = Toast.makeText(this, toast, Toast.LENGTH_SHORT);
            toastNew.show();
        }
    }
}
