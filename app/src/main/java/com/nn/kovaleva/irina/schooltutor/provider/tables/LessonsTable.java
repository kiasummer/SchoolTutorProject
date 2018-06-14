package com.nn.kovaleva.irina.schooltutor.provider.tables;

import android.net.Uri;
import android.provider.BaseColumns;

public class LessonsTable implements BaseColumns {
    public static final String AUTHORITY = "com.nn.kovaleva.irina.schooltutor.database";
    public static final String TABLE_NAME = "lesson";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    public static final String  TUTOR_ID = "id_tutor";
    public static final String  STUDENT_ID = "id_student";
    public static final String  ADDRESS = "address";
    public static final String  SUBJECT_ID = "id_subject";
    public static final String  START_TIME = "start_time";
    public static final String  DURATION = "duration";
    public static final String  COST = "cost";
}
