package com.nn.kovaleva.irina.schooltutor.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIn{ // extends JsonBaseRequest
    private static final String TAG = "LogIn";
    public String userName;
    public String password;

    public JSONObject toJson() {
        Log.d(TAG, "toJson: try to convert to Json in log in");
        JSONObject obj = new JSONObject();
        try {
            obj.put("userName", userName);
            obj.put("password", password);
        }catch (JSONException e){
            Log.e(TAG, "toJson: JSONException" + e.getMessage());
        }
        return obj;
    }

    public void fromJson(JSONObject obj) {
        Log.d(TAG, "fromJson: try to convert from Json in log in");
        try {
            userName = obj.getString("userName");
            password = obj.getString("password");
        }catch (JSONException e){
            Log.e(TAG, "toJson: JSONException" + e.getMessage());
        }
    }
}
