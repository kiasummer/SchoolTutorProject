package com.nn.kovaleva.irina.schooltutor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nn.kovaleva.irina.schooltutor.Model.Actor;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.Model.UserList;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.RecyclerOnClickListener;
import com.nn.kovaleva.irina.schooltutor.UI.resurces.SearchAdapter;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements TextView.OnEditorActionListener,
        TextWatcher{
    public static final String TAG = "SearchFragment";

    private View view;

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private UserList userResponse = null;
    private ArrayList<User> filteredUsers = new ArrayList<>();
    private TextView notFound;
    private EditText searchField;
    private static String filter = "";


    public static SearchFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        SearchFragment fragment = new SearchFragment();
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
        view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter();
        notFound = view.findViewById(R.id.fragment_search_not_found);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        searchField = view.findViewById(R.id.input_search);
        searchField.setOnEditorActionListener(this);
        searchField.addTextChangedListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerOnClickListener(getContext(), recyclerView,
                new RecyclerOnClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        int id = searchAdapter.getUsers().get(position).userId;
                        openProfile(id);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));


        Controller.getsInstance().getAllIntUsers(Actor.getsInstance().ifTutor,
                Actor.getsInstance().id, new OnRequestResult() {
                    @Override
                    public void onResponse(final JsonBaseResponse response) {
                        if (response != null && response.errorCode == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notFound.setVisibility(View.GONE);
                                    userResponse = (UserList) response;
                                    filteredUsers = getFilteredUsers();
                                    searchAdapter.setItems(filteredUsers);
                                }
                            });
                        } else if (response == null) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            if (response.errorCode != 400){
                                Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
                            } else {
                                notFound.setText((Actor.getsInstance().ifTutor) ?
                                        "No students found" : "No tutors found");
                                notFound.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.d(TAG, "onBackStackChanged: ");
                afterTextChanged(null);
            }
        });
        return view;

    }

    private void openProfile(int id){
        Log.d(TAG, "openProfile: with id = " + id);
        Controller.getsInstance().getUserById(id, new OnRequestResult() {
            @Override
            public void onResponse(JsonBaseResponse response) {
                if (response != null && response.errorCode == 0) {
                    //где-то здесь должна быть проверка на то, является ли учеником/учителем
                    ProfileFragment.user = (User) response;
                    ProfileFragment.status = ProfileFragment.VISITER;
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.constraint_layout, ProfileFragment.newInstance());
                    fragmentTransaction.addToBackStack(null);
                    //fragmentTransaction.add(R.id.constraint_layout, new ProfileFragment());
                    fragmentTransaction.commit();
                } else if (response == null) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    if (response.errorCode != 400){
                        Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
                    } else {
                        notFound.setText((Actor.getsInstance().ifTutor) ?
                                "No students found" : "No tutors found");
                        notFound.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d(TAG, "onEditorAction: ");
        if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN){
            CalendarActivity.hideKeyBoardTouchEveryWhere(view.findViewById(R.id.search_fragment_parent),
                    getActivity());
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    private ArrayList<User> getFilteredUsers(){
        ArrayList<User> filtrUsers = new ArrayList<>();
        if (filter.equals("")){
            for (int i = 0; i < userResponse.userList.size(); i ++){
                filtrUsers.add(userResponse.userList.get(i));
            }
        } else {
            String[] filterWords = filter.split(" ", 0);
            for (int i = 0; i < userResponse.userList.size(); i++) {
                for (int j = 0; j < filterWords.length; j++) {
                    if (!filterWords[j].equals("")) {
                        if (userResponse.userList.get(i).firstName.contains(filterWords[j])
                                || userResponse.userList.get(i).secondName.contains(filterWords[j])) {
                            filtrUsers.add(userResponse.userList.get(i));
                            break;
                        }
                    }
                }
            }
        }
        return filtrUsers;
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged: ");
        searchAdapter.clearItems();
        filter = searchField.getText().toString();
        filteredUsers = getFilteredUsers();
        if (filteredUsers.size() == 0){
            notFound.setText((Actor.getsInstance().ifTutor) ?
                    "No students found" : "No tutors found");
            notFound.setVisibility(View.VISIBLE);
        } else {
            notFound.setVisibility(View.GONE);
            searchAdapter.setItems(filteredUsers);
        }
    }
}
