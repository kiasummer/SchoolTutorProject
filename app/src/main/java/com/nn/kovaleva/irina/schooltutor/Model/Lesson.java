package com.nn.kovaleva.irina.schooltutor.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class Lesson extends JsonBaseResponse{
    private static final String TAG = "Lesson";

    public User tutor = new User();
    public User student = new User();
    public String theme;
    public String address;
    public String startTime;
    public int duration; //in minutes
    public int cost;

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tutor", tutor.toJson());
            obj.put("student", student.toJson());
            obj.put("theme", theme);
            obj.put("address", address);
            obj.put("startTime", startTime);
            obj.put("duration", duration);
            obj.put("cost", cost);
        } catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
        return obj;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void fromJson(JSONObject obj) {
        try {
            //super.fromJson(obj);
            tutor.fromJson(obj.getJSONObject("tutor"));
            student.fromJson(obj.getJSONObject("student"));
            theme = obj.getString("theme");
            address = obj.getString("address");
            startTime = obj.getString("startTime");
            duration = obj.getInt("duration");
            cost = obj.getInt("cost");
        } catch (JSONException e) {
            Log.e(TAG, "fromJson: JSONException: " + e.getMessage());
        }
    }

}
