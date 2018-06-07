package com.nn.kovaleva.irina.schooltutor.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class Lesson extends JsonBaseResponse{
    private static final String TAG = "Lesson";

    public Tutor tutor;
    public Student student;
    public Themes theme;
    public String address;
    public String startTime;
    public int duration; //in minutes

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("tutor", tutor.toJson());
            obj.put("student", student.toJson());
            obj.put("theme", theme.toJson());
            obj.put("address", address);
            obj.put("startTime", startTime);
            obj.put("duration", duration);
        } catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
        return obj;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void fromJson(JSONObject obj) {
        try {
            super.fromJson(obj);
            tutor.fromJson(obj.getJSONObject("tutor"));
            student.fromJson(obj.getJSONObject("student"));
            theme.fromJson(obj.getJSONObject("theme"));
            address = obj.getString("address");
            startTime = obj.getString("startTime");
            duration = obj.getInt("duration");

        } catch (JSONException e) {
            Log.e(TAG, "fromJson: JSONException: " + e.getMessage());
        }
    }

}
