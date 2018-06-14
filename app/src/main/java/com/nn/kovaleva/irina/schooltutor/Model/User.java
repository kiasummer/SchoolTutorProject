package com.nn.kovaleva.irina.schooltutor.Model;

import android.location.Address;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User extends JsonBaseResponse {
    public static final String TAG = "User";

    public int userId;
    public String login = "";
    public String password = "";
    public String firstName = "";
    public String secondName = "";
    public boolean ifTutor;
    public String telNumber = "";
    public ArrayList<String> themes = new ArrayList<>();
    public String address = "";
    //public ArrayList<DurationsOfTime> times = new ArrayList<>();
    //public String patronymic = "";
    public boolean ifAtHome;
    public int cost;
    public ArrayList<Education> educations = new ArrayList<>();
    public int yearOfEducation;

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        try {
            obj.put("userId", userId);
            obj.put("login", login);
            obj.put("password", password);
            obj.put("firstName", firstName);
            obj.put("secondName", secondName);
            //obj.put("patronymic", patronymic);
            obj.put("ifTutor", ifTutor);
            obj.put("telNumber", telNumber);
            obj.put("address", address);
            obj.put("cost", cost);
            obj.put("yearOfEducation", yearOfEducation);
            obj.put("ifAtHome", ifAtHome);

//            JSONArray arr1 = new JSONArray();
//            for (DurationsOfTime durations : times) {
//                arr1.put(durations.toJson());
//            }
//            obj.put("times", arr1);

            JSONArray arr2 = new JSONArray();
            for (String theme : themes) {
                arr2.put(theme);
            }
            obj.put("themes", arr2);

            JSONArray arr3 = new JSONArray();
            for (Education education: educations) {
                arr3.put(education.toJson());
            }
            obj.put("educations", arr3);

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
            //patronymic = obj.getString("patronymic");
            ifTutor = obj.getBoolean("ifTutor");
            telNumber = obj.getString("telNumber");
            address = obj.getString("address");
            cost = obj.getInt("cost");
            yearOfEducation = obj.getInt("yearOfEducation");
            ifAtHome = obj.getBoolean("ifAtHome");

//            JSONArray arr1 = obj.getJSONArray("times");
//            for (int i = 0; i < arr1.length(); i ++){
//                DurationsOfTime durations = new DurationsOfTime();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    durations.fromJson(arr1.getJSONObject(i));
//                }
//                times.add(durations);
//            }

            JSONArray arr2 = obj.getJSONArray("themes");
            for (int i = 0; i < arr2.length(); i ++){
                String theme = arr2.getString(i);
                themes.add(theme);
            }

            JSONArray arr3 = obj.getJSONArray("educations");
            for (int i = 0; i < arr3.length(); i ++){
                Education education = new Education();
                education.fromJson(arr3.getJSONObject(i));
                educations.add(education);
            }

        } catch (JSONException e) {
            Log.e(TAG, "fromJson: JSONException: " + e.getMessage());
        }
    }

}
