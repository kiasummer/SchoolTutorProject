package com.nn.kovaleva.irina.schooltutor.server;

import android.content.Context;
import android.util.Log;

import com.nn.kovaleva.irina.schooltutor.Model.LogIn;
import com.nn.kovaleva.irina.schooltutor.Model.Student;
import com.nn.kovaleva.irina.schooltutor.Model.Tutor;
import com.nn.kovaleva.irina.schooltutor.Model.User;
import com.nn.kovaleva.irina.schooltutor.core.Controller;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestMethods {
    private static final String TAG = "RequestMethods";

    public static String logIn(Context context, String request){
        String login = "aaa";
        String pass = "aaa";
        String response = null;
        LogIn logIn = new LogIn();
        try{
            JSONObject obj = new JSONObject(request);
            logIn.fromJson(obj);
        } catch (JSONException e){
            Log.e(TAG, "addUser: JSONException: " + e.getMessage());
            logIn = null;
        }

        if (logIn != null){
            if (!logIn.userName.equals(login) || !logIn.password.equals(pass)){
                response = "{\"message\":\"Incorrect username or password\",\"errorCode\":400}";
            } else {
                response = "{\"message\":\"Ok\",\"errorCode\":0}";
            }
        } else {
            response = "{\"message\":\"Response Error\",\"errorCode\":500}";
        }
        return response;
    }

    public static String addUser(Context context, String request){
        String response = null;
        User user;
        try{
            JSONObject obj = new JSONObject(request);
            boolean ifTutor = obj.getBoolean("ifTutor");
            if (ifTutor){
                user = new Tutor();
            } else {
                user = new Student();
            }
            user.fromJson(obj);
        } catch (JSONException e){
            Log.e(TAG, "addUser: JSONException: " + e.getMessage());
            user = null;
        }

        if (user != null){
            response = "{\"message\":\"Ok\",\"errorCode\":0}";
        } else {
            response = "{\"message\":\"Response Error\",\"errorCode\":500}";
        }

        return response;
    }
}
