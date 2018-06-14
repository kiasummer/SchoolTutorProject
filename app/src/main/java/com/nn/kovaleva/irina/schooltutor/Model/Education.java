package com.nn.kovaleva.irina.schooltutor.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.time.DayOfWeek;

public class Education extends JsonBaseResponse{
    private static final String TAG = "Education";

    public String nameOfUniversity;
    public String faculty;
    public int yearOfEnd;

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("university", nameOfUniversity);
            obj.put("faculty", faculty);
            obj.put("yearOfEnd", yearOfEnd);
        } catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void fromJson(JSONObject obj) throws JSONException {
        //super.fromJson(obj);
        try {
            nameOfUniversity = obj.getString("university");
            faculty = obj.getString("faculty");
            yearOfEnd = obj.getInt("yearOfEnd");
        } catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
    }
}
