package com.nn.kovaleva.irina.schooltutor.provider.tables;

import android.net.Uri;
import android.provider.BaseColumns;

public class ChatTable implements BaseColumns {
    public static final String AUTHORITY = "com.nn.kovaleva.irina.schooltutor.database";
    public static final String TABLE_NAME = "chat";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public static final String  AUTHOR_ID = "id_author";
    public static final String  CLIENT_ID = "id_client";
    public static final String  DATE = "date";
    public static final String  TEXT = "text";
}
