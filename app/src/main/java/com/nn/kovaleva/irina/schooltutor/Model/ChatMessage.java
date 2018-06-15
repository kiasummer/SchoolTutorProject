package com.nn.kovaleva.irina.schooltutor.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessage extends JsonBaseResponse{
    private static final String TAG = "ChatMessage";

    public int author_id;
    public int client_id;
    public String text;
    public Date date;

    private DateFormat md = new SimpleDateFormat("hh:mm dd MMMM, yyyy", Locale.ENGLISH);

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("authorId", author_id);
            obj.put("clientId", client_id);
            obj.put("text", text);
            obj.put("date", md.format(date));
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
            author_id = obj.getInt("authorId");
            client_id = obj.getInt("clientId");
            text = obj.getString("text");
            String formatDate = obj.getString("date");
            try {
                date = md.parse(formatDate);
            } catch (ParseException e) {
                Log.e(TAG, "fromJson: ParceExceprion: " + e.getMessage());
            }
        } catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
    }
}
