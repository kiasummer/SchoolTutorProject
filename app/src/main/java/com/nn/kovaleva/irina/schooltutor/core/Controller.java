package com.nn.kovaleva.irina.schooltutor.core;

import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.nn.kovaleva.irina.schooltutor.CalendarActivity;
import com.nn.kovaleva.irina.schooltutor.Model.Education;
import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.Model.LogIn;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.Model.UserList;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.ICommunicationManager;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.IOnRequestListener;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;
import com.nn.kovaleva.irina.schooltutor.core.transport.CommunicationManager;
import com.nn.kovaleva.irina.schooltutor.core.transport.data.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Controller {

    private static final String TAG = "Controller";

    private static Controller sInstance = null;
    public boolean ifLogIn = false;
//    public Context context = null;
    private ICommunicationManager communicationManager = null;

    public boolean ifTutor;

    public Controller() {
        communicationManager = new CommunicationManager();
    }

    private Request makeRequest(String method, String data){
        Request request = new Request();
        request.method = method;
        request.data = data;
        return request;
    }

    public void logIn(String userName, String password, final OnRequestResult onRequestResult) {
        Log.d(TAG, "logIn: start to login");
        LogIn logIn = new LogIn();
        logIn.userName = userName;
        logIn.password = password;

        final Request request = makeRequest("login", logIn.toJson().toString());
        communicationManager.sendJsonRequest(request, new IOnRequestListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(ResponseCode responseCode, String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (responseCode == ResponseCode.Ok){
                        User loginResponse = new User();
                        loginResponse.fromJson(obj);
                        onRequestResult.onResponse(loginResponse);
                    } else {
                        JsonBaseResponse baseResponse = new JsonBaseResponse();
                        baseResponse.fromJson(obj);
                        onRequestResult.onResponse(baseResponse);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: JSONException" + e.getMessage());
                }
            }
        });
    }

    public void register (String userName, String password,  boolean ifTutor, String phoneNumber,
                              final OnRequestResult onRequestResult){
        final User user = new User();
        user.login = userName;
        user.password = password;
        user.ifTutor = ifTutor;
        user.telNumber = phoneNumber;

        Request request = makeRequest("addUser", user.toJson().toString());
        communicationManager.sendJsonRequest(request, new IOnRequestListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(ResponseCode responseCode, String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (responseCode == ResponseCode.Ok){
                        User userResponse = new User();
                        userResponse.fromJson(obj);
                        onRequestResult.onResponse(userResponse);
                    } else {
                        JsonBaseResponse baseResponse = new JsonBaseResponse();
                        baseResponse.fromJson(obj);
                        onRequestResult.onResponse(baseResponse);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: JSONException" + e.getMessage());
                }
            }
        });
    }

//    String firstName, String patronymic, String secondName, int yearOfEducation,
//    ArrayList<String> subjects, ArrayList<Education> educations,
//    String address, boolean ifAtHome, int cost,

    public void saveEdits(User requestUser, final OnRequestResult onRequestResult){
        Request request = makeRequest("saveChangesProfile", requestUser.toJson().toString());
        communicationManager.sendJsonRequest(request, new IOnRequestListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(ResponseCode responseCode, String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (responseCode == ResponseCode.Ok){
                        User userResponse = new User();
                        userResponse.fromJson(obj);
                        onRequestResult.onResponse(userResponse);
                    } else {
                        JsonBaseResponse baseResponse = new JsonBaseResponse();
                        baseResponse.fromJson(obj);
                        onRequestResult.onResponse(baseResponse);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: JSONException" + e.getMessage());
                }
            }
        });

    }


    public void getAllIntUsers(boolean ifTutor, int userId, final OnRequestResult onRequestResult){
        final User user = new User();
        user.ifTutor = ifTutor;
        user.userId = userId;
        Request request = makeRequest("getAllUsers", user.toJson().toString());
        communicationManager.sendJsonRequest(request, new IOnRequestListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(ResponseCode responseCode, String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (responseCode == ResponseCode.Ok){
                        UserList userListResponse = new UserList();
                        userListResponse.fromJson(obj);
                        onRequestResult.onResponse(userListResponse);
                    } else {
                        JsonBaseResponse baseResponse = new JsonBaseResponse();
                        baseResponse.fromJson(obj);
                        onRequestResult.onResponse(baseResponse);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: JSONException" + e.getMessage());
                }
            }
        });

    }

    public void getUserById(int id, final OnRequestResult onRequestResult){
        Request request = makeRequest("getUserById", String.valueOf(id));
        communicationManager.sendJsonRequest(request, new IOnRequestListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(ResponseCode responseCode, String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (responseCode == ResponseCode.Ok){
                        User user = new User();
                        user.fromJson(obj);
                        onRequestResult.onResponse(user);
                    } else {
                        JsonBaseResponse baseResponse = new JsonBaseResponse();
                        baseResponse.fromJson(obj);
                        onRequestResult.onResponse(baseResponse);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: JSONException" + e.getMessage());
                }
            }
        });

    }

    public boolean isIfLogIn() {
        return ifLogIn;
    }

    public static Controller getsInstance (){
        if (sInstance == null){
            sInstance = new Controller();
        }
        return sInstance;
    }
}
