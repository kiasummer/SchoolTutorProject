package com.nn.kovaleva.irina.schooltutor.Model;

import android.location.Address;

import org.json.JSONObject;

import java.util.ArrayList;

public abstract class User extends JsonBaseResponse {
    public int userId;
    public String login = "";
    public String password = "";
    public String firstName = "";
    public String secondName = "";
    public boolean ifTutor;
    public String telNumber = "";
    public ArrayList<Themes> themes = new ArrayList<>();
    public String address = "";
    public ArrayList<DurationsOfTime> times = new ArrayList<>();

    public abstract JSONObject toJson();

}
