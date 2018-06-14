package com.nn.kovaleva.irina.schooltutor.Model;

import java.util.ArrayList;

public class Actor {
    private static Actor sInst = null;
    public int id;
    public String login = "";
    public String password = "";
    public String firstName = "";
    public String secondName = "";
    public boolean ifTutor;
    public boolean ifAtHome;
    public String telNumber = "";
    public ArrayList<String> themes = new ArrayList<>();
    public String address = null;
    //public ArrayList<DurationsOfTime> times = new ArrayList<>();
    //public String patronymic = "";
    public int cost;
    public ArrayList<Education> educations = new ArrayList<>();
    public int yearOfEducation;

    public ArrayList<Lesson> lessons = new ArrayList<>();


    public void clear(){
        sInst = null;
        //        id = 0;
//        login = "";
//        password = "";
//        firstName = "";
//        secondName = "";
//        ifTutor;
//        ifAtHome;
//        telNumber = "";
//        themes = new ArrayList<>();
//        address = null;
//        times = new ArrayList<>();
//        patronymic = "";
//        cost;
//        educations;
//        yearOfEducation;
    }

    public static Actor getsInstance(){
        if (sInst == null){
            sInst = new Actor();
        }
        return sInst;
    }
}
