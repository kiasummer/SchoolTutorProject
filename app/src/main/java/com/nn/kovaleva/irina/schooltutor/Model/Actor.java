package com.nn.kovaleva.irina.schooltutor.Model;

import java.util.ArrayList;

public class Actor {
    private static Actor sInst = null;
    public int id;
//    public int userId;
    public String login;
    public String password;
//    public String firstName = "";
//    public String secondName = "";
    public boolean ifTutor;
    public String telNumber;
//    public ArrayList<Themes> themes = new ArrayList<>();
//    public String address = null;
//    public ArrayList<DurationsOfTime> times = new ArrayList<>();
//    public String patronymic;
//    public int cost;
//    public ArrayList<Education> educations;
//    public int yearOfEducation;


//    public void clear(){
//        id = 0;
//        surname = null;
//        name = null;
//        status = -1;
//        birthday = null;
//        sex = null;
//        phone = null;
//    }

    public static Actor getsInstance(){
        if (sInst == null){
            sInst = new Actor();
        }
        return sInst;
    }
}
