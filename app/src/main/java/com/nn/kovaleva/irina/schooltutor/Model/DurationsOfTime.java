package com.nn.kovaleva.irina.schooltutor.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.time.DayOfWeek;

public class DurationsOfTime extends JsonBaseResponse{
    private static final String TAG = "DurationsOfTime";
    public String dayOfWeek;
    public String startTime;
    public String endTime;

//    public DurationsOfTime(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
//        this.dayOfWeek = dayOfWeek;
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }

    public DurationsOfTime() {
    }

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("dayOfWeek", dayOfWeek);
            obj.put("startTime", startTime);
            obj.put("endTime", endTime);
        } catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void fromJson(JSONObject obj) throws JSONException {
        super.fromJson(obj);
        try {
            dayOfWeek = obj.getString("dayOfWeek");
            startTime = obj.getString("startTime");
            endTime = obj.getString("endTime");
        } catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
    }
}
