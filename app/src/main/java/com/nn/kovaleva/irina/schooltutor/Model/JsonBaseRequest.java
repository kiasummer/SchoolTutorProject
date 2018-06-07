package com.nn.kovaleva.irina.schooltutor.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonBaseRequest {
    public String toJsonString(){
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        return gson.toJson(this);
    }
}
