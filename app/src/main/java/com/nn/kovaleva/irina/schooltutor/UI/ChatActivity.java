package com.nn.kovaleva.irina.schooltutor.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.ChatMessage;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.Model.MessageList;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.R;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.ChatAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.MessageAdapter;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity implements View.OnClickListener{
    public static final String TAG = "ChatActivity";

    private String fistName;
    private String surname;
    private int partnetId;
    private MessageList messageList;
    private List<User> users = new ArrayList<>();
    private TextView partnerName;
    private ImageView backBtn, sendBtn;
    private EditText textField;
    private User user, actor;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;

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
        Intent intent = getIntent();
        //int secondId = intent.getIntExtra("secondName", -1);
        fistName = intent.getStringExtra("firstName");
        surname = intent.getStringExtra("surname");
        partnetId = intent.getIntExtra("partnerId", -1);

        recyclerView = findViewById(R.id.recycler_view_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter();
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sendBtn.setOnClickListener(this);

        user = new User();
        actor = new User();
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
            default:{
                break;
            }
        }
    }
}
