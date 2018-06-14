package com.nn.kovaleva.irina.schooltutor.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonBaseResponse {
    public static final String TAG = "JsonBaseResponse";

    public int errorCode = 0;
    public String message = "";

    public void fromJson(JSONObject obj) throws JSONException {
        errorCode = obj.getInt("errorCode");
        message = obj.getString("message");
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("errorCode", errorCode);
            obj.put("message", message);
        }catch (JSONException e){
            Log.e(TAG, "toJson: JSONException: " + e.getMessage() );
        }
        return obj;
    }
}
