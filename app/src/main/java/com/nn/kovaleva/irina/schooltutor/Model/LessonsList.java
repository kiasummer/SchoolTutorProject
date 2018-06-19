package com.nn.kovaleva.irina.schooltutor.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LessonsList extends JsonBaseResponse {
    public static final String TAG = "LessonList";
    public ArrayList<Lesson> lessonList = new ArrayList<>();

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        JSONArray arr1 = new JSONArray();
        for (Lesson lesson: lessonList) {
            arr1.put(lesson.toJson());
        }
        try {
            obj.put("lessons", arr1);
        } catch (JSONException e) {
            Log.e(TAG, "toJSONObject: JSONException: " + e.getMessage());
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void fromJson(JSONObject obj) throws JSONException {
        super.fromJson(obj);
        JSONArray arr1 = obj.getJSONArray("lessons");
        for (int i = 0; i < arr1.length(); i ++){
            Lesson lesson = new Lesson();
            lesson.fromJson(arr1.getJSONObject(i));
            lessonList.add(lesson);
        }
    }
}
