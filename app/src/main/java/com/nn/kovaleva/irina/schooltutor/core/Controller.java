package com.nn.kovaleva.irina.schooltutor.core;

import android.nfc.Tag;
import android.util.Log;

import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.Model.LogIn;
import com.nn.kovaleva.irina.schooltutor.Model.Student;
import com.nn.kovaleva.irina.schooltutor.Model.Tutor;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.ICommunicationManager;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.IOnRequestListener;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.OnRequestResult;
import com.nn.kovaleva.irina.schooltutor.core.transport.CommunicationManager;
import com.nn.kovaleva.irina.schooltutor.core.transport.data.Request;

import org.json.JSONException;
import org.json.JSONObject;

public class Controller {

    private static final String TAG = "Controller";

    private static Controller sInstance = null;
    private boolean ifLogIn = false;
    private ICommunicationManager communicationManager = null;

    public boolean ifTutor;

    public Controller() {
        communicationManager = new CommunicationManager(null);
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

        Request request = makeRequest("login", logIn.toJson().toString());
        communicationManager.sendJsonRequest(request, new IOnRequestListener(){
            @Override
            public void onResponse(ResponseCode responseCode, String response) {
                JsonBaseResponse logRequest = null;
                if (responseCode == ResponseCode.Ok){
                    ifLogIn = true;
                    try {
                        JSONObject obj = new JSONObject(response);
                        logRequest = new JsonBaseResponse();
                        logRequest.fromJson(obj);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: JSONException" + e.getMessage());
                    }
                }
                onRequestResult.onResponse(logRequest);
            }
        });
    }

    public void register (String userName, String password,  boolean ifTutor, String phoneNumber,
                              final OnRequestResult onRequestResult){
        User user;
        if (ifTutor){
            user = new Tutor();
        } else {
            user = new Student();
        }
        ifTutor = ifTutor;
        user.login = userName;
        user.password = password;
        user.ifTutor = ifTutor;
        user.telNumber = phoneNumber;

        Request request = makeRequest("addUser", user.toJson().toString());
        communicationManager.sendJsonRequest(request, new IOnRequestListener() {
            @Override
            public void onResponse(ResponseCode responseCode, String response) {
                JsonBaseResponse user = null;
                if (responseCode == ResponseCode.Ok){
                    try{
                        JSONObject obj = new JSONObject(response);
                        user = new JsonBaseResponse();
                        user.fromJson(obj);
                    }catch (JSONException e){
                        Log.e(TAG, "onResponse: JSONException: " +e.getMessage());
                    }
                }
                onRequestResult.onResponse(user);
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
