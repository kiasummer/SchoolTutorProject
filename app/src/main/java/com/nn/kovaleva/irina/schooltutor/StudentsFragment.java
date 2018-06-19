package com.nn.kovaleva.irina.schooltutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.Model.MessageList;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.Model.UserList;
import com.nn.kovaleva.irina.schooltutor.UI.ChatActivity;
import com.nn.kovaleva.irina.schooltutor.UI.LoginActivity;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.ChatAdapter;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.RecyclerOnClickListener;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;

import java.util.ArrayList;

public class StudentsFragment extends Fragment {

    public static final String TAG = "StudentsFragment";
    
    private View view;

    private RecyclerView recyclerView;
    private TextView notFound;
    private MessageList messageList = null;
    private UserList userList = null;

    ChatAdapter chatAdapter;

    public static StudentsFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        StudentsFragment fragment = new StudentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_students, container, false);
        notFound = view.findViewById(R.id.fragment_chats_not_found);
        recyclerView = view.findViewById(R.id.recycler_view_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatAdapter = new ChatAdapter();
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int id = Actor.getsInstance().id;
        userList = new UserList();
        getMessages(id);

        recyclerView.addOnItemTouchListener(new RecyclerOnClickListener(getContext(), recyclerView,
                new RecyclerOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.d(TAG, "onItemClick: ");
                        User user = chatAdapter.getUsers().get(position);
                        String userName = user.firstName;
                        String userSurname = user.secondName;
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("firstName", userName);
                        intent.putExtra("surname", userSurname);
                        intent.putExtra("partnerId", user.userId);
                        intent.putExtra("secondId", user.userId);
                        getActivity().startActivityForResult(intent, 4);

                        //int id = chatAdapter.getUsers().get(position).userId;
                        //openProfile(id);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

        return view;

    }

    private void getMessages(int id){
        Log.d(TAG, "getMessages: ");
        Controller.getsInstance().getUsersMessages(Actor.getsInstance().id, new OnRequestResult() {
                    @Override
                    public void onResponse(final JsonBaseResponse response) {
                        if (response != null && response.errorCode == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notFound.setVisibility(View.GONE);
                                    messageList = (MessageList) response;
                                    ArrayList<Integer> usersID = new ArrayList<>();
                                    for (int i = 0; i < messageList.messageList.size(); i ++){
                                        int author = messageList.messageList.get(i).author_id;
                                        int client = messageList.messageList.get(i).client_id;
                                        if (author == Actor.getsInstance().id){
                                            usersID.add(client);
                                        } else {
                                            usersID.add(author);
                                        }
                                    }
                                    //userList.userList = getUserNameList(usersID);
                                    for (int i = 0; i < usersID.size(); i++) {
                                        Controller.getsInstance().getUserById(usersID.get(i), new OnRequestResult() {
                                            @Override
                                            public void onResponse(final JsonBaseResponse response) {
                                                if (response != null && response.errorCode == 0) {
                                                    userList.userList.add((User)response);
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            chatAdapter.setItems(userList.userList,
                                                                    messageList.messageList);
                                                        }
                                                    });

                                                } else if (response == null) {
                                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (response.errorCode != 400) {
                                                        Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        notFound.setVisibility(View.VISIBLE);
                                                    }
                                                }
                                            }
                                        });
                                    }

                                }
                            });
                        } else if (response == null) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.errorCode != 400){
                                Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
                            } else {
                                notFound.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });


    }

//    private ArrayList<User> getUserNameList(ArrayList<Integer> indexes) {
//        Log.d(TAG, "getUserNameList: ");
//        final ArrayList<User> userListArr = new ArrayList<>();
//        for (int i = 0; i < indexes.size(); i++) {
//            Controller.getsInstance().getUserById(indexes.get(i), new OnRequestResult() {
//                @Override
//                public void onResponse(final JsonBaseResponse response) {
//                    if (response != null && response.errorCode == 0) {
//                        userListArr.add((User)response);
//                    } else if (response == null) {
//                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if (response.errorCode != 400) {
//                            Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
//                        } else {
//                            notFound.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//            });
//        }
//        return userListArr;
//    }
}
