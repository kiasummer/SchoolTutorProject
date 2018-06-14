package com.nn.kovaleva.irina.schooltutor.provider.tables;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserTable implements BaseColumns{
    public static final String AUTHORITY = "com.nn.kovaleva.irina.schooltutor.database";
    public static final String TABLE_NAME = "users";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public static final String  LOGIN = "login";
    public static final String  PASSWORD = "password";
    public static final String  IFTUTOR = "if_tutor";
//    public static final String  TUTOR_ID = "tutor_id";
//    public static final String  STUDENT_ID = "student_id";
    public static final String  PHONE = "phone";
}
