package com.nn.kovaleva.irina.schooltutor.Model;

import android.location.Address;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Student extends User{
    private static final String TAG = "Student";
    public int yearOfEducation;

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("userId", userId);
            obj.put("login", login);
            obj.put("password", password);
            obj.put("firstName", firstName);
            obj.put("secondName", secondName);
            obj.put("ifTutor", true);
            obj.put("telNumber", telNumber);
            obj.put("address", address);
            obj.put("yearOnEducation", yearOfEducation);

            JSONArray arr1 = new JSONArray();
            for (DurationsOfTime durations : times) {
                arr1.put(durations.toJson());
            }
            obj.put("times", arr1);

            JSONArray arr2 = new JSONArray();
            for (Themes theme : themes) {
                arr2.put(theme.toJson());
            }
            obj.put("themes", arr2);

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
            userId = obj.getInt("userId");
            login = obj.getString("login");
            password = obj.getString("password");
            firstName = obj.getString("firstName");
            secondName = obj.getString("secondName");
            ifTutor = obj.getBoolean("ifTutor");
            telNumber = obj.getString("telNumber");
            address = obj.getString("address");
            yearOfEducation = obj.getInt("yearOfEducation");

            JSONArray arr1 = obj.getJSONArray("times");
            for (int i = 0; i < arr1.length(); i ++){
                DurationsOfTime durations = new DurationsOfTime();
                durations.fromJson(arr1.getJSONObject(i));
                times.add(durations);
            }

            JSONArray arr2 = obj.getJSONArray("themes");
            for (int i = 0; i < arr2.length(); i ++){
                Themes theme = new Themes();
                theme.fromJson(arr2.getJSONObject(i));
                themes.add(theme);
            }
        } catch (JSONException e) {
            Log.e(TAG, "fromJson: JSONException: " + e.getMessage());
        }
    }
}
