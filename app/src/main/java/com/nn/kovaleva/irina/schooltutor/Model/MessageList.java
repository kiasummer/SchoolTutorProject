package com.nn.kovaleva.irina.schooltutor.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageList extends JsonBaseResponse {
    public static final String TAG = "MessageList";
    public ArrayList<ChatMessage> messageList = new ArrayList<>();

    @Override
    public JSONObject toJson() {
        JSONObject obj = super.toJson();
        JSONArray arr1 = new JSONArray();
        for (ChatMessage message : messageList) {
            arr1.put(message.toJson());
        }
        try {
            obj.put("messages", arr1);
        } catch (JSONException e) {
            Log.e(TAG, "toJSONObject: JSONException: " + e.getMessage());
        }
        return obj;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void fromJson(JSONObject obj) throws JSONException {
        super.fromJson(obj);
        JSONArray arr1 = obj.getJSONArray("messages");
        for (int i = 0; i < arr1.length(); i ++){
            ChatMessage message = new ChatMessage();
            message.fromJson(arr1.getJSONObject(i));
            messageList.add(message);
        }
    }
}

