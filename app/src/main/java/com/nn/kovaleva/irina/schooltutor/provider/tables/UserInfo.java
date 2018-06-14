package com.nn.kovaleva.irina.schooltutor.provider.tables;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserInfo implements BaseColumns {
    public static final String AUTHORITY = "com.nn.kovaleva.irina.schooltutor.database";
    public static final String TABLE_NAME = "user_info";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public static final String  USER_ID = "id_user";
    public static final String  FIRST_NAME = "first_name";
    //public static final String  PATRONYMIC = "patronymic";
    public static final String  SECOND_NAME = "second_name";
    public static final String  COST = "cost";
    public static final String  YEAR_OF_EDUCATION = "year_of_education";
}
