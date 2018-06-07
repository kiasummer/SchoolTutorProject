package com.nn.kovaleva.irina.schooltutor.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Themes extends JsonBaseResponse{
    private static final String TAG = "Themes";
    public String theme;
    public enum Theme {
        MATHS("Mathematics"),
        INF("Informatics"),
        PROG("Programming"),
        HISTORY("History"),
        GEOGRAPHY("Geography"),
        BIO("Biology"),
        PHYSICS("Physics"),
        ECONOMY("Economy"),
        CHTMISTRY("Chemistry"),
        SOCSTUD("Social studies"),
        NATLAN("Native language"),
        FORLANG("Foreign language");

        public String description;

        public Theme getByDescription(String description){
            Theme[] themes = Theme.values();
            for (int i = 0; i < themes.length; i ++){
                if (themes[i].description.equals(description)){
                    return themes[i];
                }
            }
            return null;
        }

        Theme(String description) {
            this.description = description;
        }

        public String getDescription() {return description;}
    }

    public Themes() {
    }

    public Themes(Theme theme) {
        this.theme = theme.description;
    }

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("theme", theme);
        }
        catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
        return obj;
    }

    @Override
    public void fromJson(JSONObject obj) throws JSONException {
        super.fromJson(obj);
        try {
            theme = obj.getString("theme");
        }
        catch (JSONException e) {
            Log.e(TAG, "toJson: JSONException: " + e.getMessage());
        }
    }
}
