package com.nn.kovaleva.irina.schooltutor.provider.tables;

import android.net.Uri;
import android.provider.BaseColumns;

public class AddressTable implements BaseColumns{
    public static final String AUTHORITY = "com.nn.kovaleva.irina.schooltutor.database";
    public static final String TABLE_NAME = "tutors";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public static final String  USER_ID = "id_user";
    public static final String  ADDRESS = "address";
    public static final String  IF_AT_HOME = "if_at_home";
}
