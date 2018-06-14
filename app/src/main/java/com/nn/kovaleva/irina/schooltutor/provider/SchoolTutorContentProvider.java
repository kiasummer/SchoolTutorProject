package com.nn.kovaleva.irina.schooltutor.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nn.kovaleva.irina.schooltutor.provider.tables.AddressTable;
import com.nn.kovaleva.irina.schooltutor.provider.tables.EducationTable;
import com.nn.kovaleva.irina.schooltutor.provider.tables.LessonsTable;
//import com.nn.kovaleva.irina.schooltutor.provider.tables.StudentTable;
//import com.nn.kovaleva.irina.schooltutor.provider.tables.SubjectTable;
import com.nn.kovaleva.irina.schooltutor.provider.tables.TimeTable;
import com.nn.kovaleva.irina.schooltutor.provider.tables.UserInfo;
import com.nn.kovaleva.irina.schooltutor.provider.tables.UserSubjectTable;
import com.nn.kovaleva.irina.schooltutor.provider.tables.UserTable;

public class SchoolTutorContentProvider extends ContentProvider{
    public static final String TAG = "SchoolTutorProvider";
    public static final String DB_NAME = "school_tutor.db";

    public static final int USERTABLE = 0;
    public static final int USER_BY_ID = 1;
    public static final int USERINFOTABLE = 2;
    public static final int TUTOR_BY_ID = 3;
//    public static final int STUDENTTABLE = 4;
    public static final int STUDENT_BY_ID = 5;
    public static final int ADDRESSTABLE = 6;
    public static final int ADDRESS_BY_ID = 7;
    public static final int SUBJECTTABLE = 8;
    public static final int SUBJECT_BY_ID = 9;
    public static final int LESSONTABLE = 10;
    //public static final int LESSON_BY_ID = 11;
    public static final int USERSUBJECTTABLE = 12;
    //public static final int USER_BY_TEAM_ID = 13;
    public static final int TIMETABLE = 14;
    //public static final int TEAM_BY_GAME_ID = 15;
    public static final int EDUCATIONTABLE = 16;

    //vars
    private DataBaseHelper helper;
    private SQLiteDatabase db;


    private static final UriMatcher sUriMatcher;
    static{
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(UserTable.AUTHORITY, UserTable.TABLE_NAME, USERTABLE);
        sUriMatcher.addURI(AddressTable.AUTHORITY, AddressTable.TABLE_NAME, ADDRESSTABLE);
        sUriMatcher.addURI(EducationTable.AUTHORITY, EducationTable.TABLE_NAME, EDUCATIONTABLE);
        sUriMatcher.addURI(LessonsTable.AUTHORITY, LessonsTable.TABLE_NAME, LESSONTABLE);
//        sUriMatcher.addURI(StudentTable.AUTHORITY, StudentTable.TABLE_NAME, STUDENTTABLE);
//        sUriMatcher.addURI(SubjectTable.AUTHORITY, SubjectTable.TABLE_NAME, SUBJECTTABLE);
        sUriMatcher.addURI(TimeTable.AUTHORITY, TimeTable.TABLE_NAME, TIMETABLE);
        sUriMatcher.addURI(UserInfo.AUTHORITY, UserInfo.TABLE_NAME, USERINFOTABLE);
        sUriMatcher.addURI(UserSubjectTable.AUTHORITY, UserSubjectTable.TABLE_NAME, USERSUBJECTTABLE);

//        sUriMatcher.addURI(UsersTable.AUTHORITY, UsersTable.TABLE_NAME + "/#", USER_BY_ID);
//        sUriMatcher.addURI(EventTable.AUTHORITY, EventTable.TABLE_NAME, EVENTTABLE);
//        sUriMatcher.addURI(EventTable.AUTHORITY, EventTable.TABLE_NAME + "/#", EVENT_BY_ID);
//        sUriMatcher.addURI(LeaguesTable.AUTHORITY, LeaguesTable.TABLE_NAME, LEAGUETABLE);
//        sUriMatcher.addURI(LeaguesTable.AUTHORITY, LeaguesTable.TABLE_NAME + "/#", LEAGUE_BY_ID);
//        sUriMatcher.addURI(TeamsTable.AUTHORITY, TeamsTable.TABLE_NAME, TEAMTABLE);
//        sUriMatcher.addURI(TeamsTable.AUTHORITY, TeamsTable.TABLE_NAME + "/#", TEAM_BY_ID);
//        sUriMatcher.addURI(OrganizersTable.AUTHORITY, OrganizersTable.TABLE_NAME, ORGANIZERSTABLE);
//        sUriMatcher.addURI(UsersTable.AUTHORITY, UsersTable.TABLE_NAME + "/league/#", USER_BY_LEAGUE_ID);
//        sUriMatcher.addURI(JuryTable.AUTHORITY, JuryTable.TABLE_NAME, JURYTABLE);
//        sUriMatcher.addURI(UsersTable.AUTHORITY, UsersTable.TABLE_NAME + "/game/#", USER_BY_GAME_ID);
//        sUriMatcher.addURI(TeamMembersTable.AUTHORITY, TeamMembersTable.TABLE_NAME, TEAMMEMBERSTABLE);
//        sUriMatcher.addURI(UsersTable.AUTHORITY, UsersTable.TABLE_NAME + "/team/#", USER_BY_TEAM_ID);
//        sUriMatcher.addURI(TeamsInGameTable.AUTHORITY, TeamsInGameTable.TABLE_NAME, TEAMINGAMETABLE);
//        sUriMatcher.addURI(TeamsTable.AUTHORITY, TeamsTable.TABLE_NAME+ "/game/#", TEAM_BY_GAME_ID);
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: start creating provider");
        helper = new DataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Log.d(TAG, "query: reading: " + uri.toString());

        db = helper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        switch (match){
            case USERTABLE:{
                queryBuilder.setTables(UserTable.TABLE_NAME);
                break;
            }
            case USERINFOTABLE:{
                queryBuilder.setTables(UserInfo.TABLE_NAME);
                break;
            }
            case ADDRESSTABLE:{
                queryBuilder.setTables(AddressTable.TABLE_NAME);
                break;
            }
            case USERSUBJECTTABLE:{
                queryBuilder.setTables(UserSubjectTable.TABLE_NAME);
                break;
            }
            case EDUCATIONTABLE:{
                queryBuilder.setTables(EducationTable.TABLE_NAME);
                break;
            }
            default:{
                return null;
            }
        }
        cursor = queryBuilder.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: inserting");

        db = helper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        try{
            switch (match){
                case USERTABLE:{
                    long rowId = db.insert(UserTable.TABLE_NAME, null, values);
                    if (rowId >= 0){
                        Uri noteUri = ContentUris.withAppendedId(UserTable.CONTENT_URI, rowId);
                        getContext().getContentResolver().notifyChange(noteUri, null);
                        return noteUri;
                    }
                }
                case USERINFOTABLE:{
                    long rowId = db.insert(UserInfo.TABLE_NAME, null, values);
                    if (rowId >= 0){
                        Uri noteUri = ContentUris.withAppendedId(UserInfo.CONTENT_URI, rowId);
                        getContext().getContentResolver().notifyChange(noteUri, null);
                        return noteUri;
                    }
                }
                case ADDRESSTABLE:{
                    long rowId = db.insert(AddressTable.TABLE_NAME, null, values);
                    if (rowId >= 0){
                        Uri noteUri = ContentUris.withAppendedId(AddressTable.CONTENT_URI, rowId);
                        getContext().getContentResolver().notifyChange(noteUri, null);
                        return noteUri;
                    }
                }
                case USERSUBJECTTABLE:{
                    long rowId = db.insert(UserSubjectTable.TABLE_NAME, null, values);
                    if (rowId >= 0){
                        Uri noteUri = ContentUris.withAppendedId(UserSubjectTable.CONTENT_URI, rowId);
                        getContext().getContentResolver().notifyChange(noteUri, null);
                        return noteUri;
                    }
                }
                case EDUCATIONTABLE:{
                    long rowId = db.insert(EducationTable.TABLE_NAME, null, values);
                    if (rowId >= 0){
                        Uri noteUri = ContentUris.withAppendedId(EducationTable.CONTENT_URI, rowId);
                        getContext().getContentResolver().notifyChange(noteUri, null);
                        return noteUri;
                    }
                }
                default:{
                    break;
                }
            }
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete: removing");
        db = helper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        try{
            switch (match){
                case USERSUBJECTTABLE:{
                    return db.delete(UserSubjectTable.TABLE_NAME, selection, selectionArgs);
                }
                case EDUCATIONTABLE:{
                    return db.delete(EducationTable.TABLE_NAME, selection, selectionArgs);
                }
                default:{
                    break;
                }
            }
        } finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return -1;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        try{
            db = helper.getWritableDatabase();
            int match = sUriMatcher.match(uri);
            switch (match) {
                case USERINFOTABLE:
                    return db.update(UserInfo.TABLE_NAME, values, selection, selectionArgs);
                case ADDRESSTABLE:
                    return db.update(AddressTable.TABLE_NAME, values, selection, selectionArgs);
                case EDUCATIONTABLE:
                    return db.update(EducationTable.TABLE_NAME, values, selection, selectionArgs);
                case USERSUBJECTTABLE:
                    return db.update(UserInfo.TABLE_NAME, values, selection, selectionArgs);
                default:
                    return -1;
            }
        }finally {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }

    public static class DataBaseHelper extends SQLiteOpenHelper{
        public static final int VERSION = 1;

        public static final String createUserTable = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, " +
                        "%s TEXT, %s INTEGER, %s TEXT);",
                UserTable.TABLE_NAME, UserTable._ID,  UserTable.LOGIN,
                UserTable.PASSWORD, UserTable.IFTUTOR, UserTable.PHONE);

        public static final String createUserInfoTable = String.format("CREATE TABLE %s " +
                "(%s TEXT, %s TEXT, %s INTEGER, %s INTEGER, %s INTEGER);",
                UserInfo.TABLE_NAME, UserInfo.FIRST_NAME,
                UserInfo.SECOND_NAME, UserInfo.USER_ID, UserInfo.COST, UserInfo.YEAR_OF_EDUCATION);

//        public static final String createSubjectTable = String.format("CREATE TABLE %s " +
//                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT);",
//                SubjectTable.TABLE_NAME, SubjectTable._ID, SubjectTable.SUBJECT_NAME);

        public static final String createUserSubjectTable = String.format("CREATE TABLE %s " +
                        "(%s INTEGER, %s TEXT);",
                UserSubjectTable.TABLE_NAME, UserSubjectTable.USER_ID, UserSubjectTable.SUBJECT_NAME);

        public static final String createAddressTable = String.format("CREATE TABLE %s " +
                        "(%s INTEGER, %s INTEGER, %s INTEGER);",
                AddressTable.TABLE_NAME, AddressTable.USER_ID, AddressTable.ADDRESS, AddressTable.IF_AT_HOME);

        public static final String createLessonTable = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, " +
                        "%s INTEGER, %s INTEGER, %s TEXT, %s STRING, %s INTEGER, %s INTEGER);",
                LessonsTable.TABLE_NAME, LessonsTable._ID, LessonsTable.TUTOR_ID,
                LessonsTable.STUDENT_ID, LessonsTable.SUBJECT_ID, LessonsTable.ADDRESS,
                LessonsTable.START_TIME, LessonsTable.DURATION, LessonsTable.COST);

        public static final String createEducationTable = String.format("CREATE TABLE %s " +
                        "(%s INTEGER, %s TEXT, %s TEXT, %s INTEGER);",
                EducationTable.TABLE_NAME, EducationTable.USER_ID, EducationTable.UNIVERSITY,
                EducationTable.FACULTY, EducationTable.ENDING_YEAR);

        public static final String createTimeTable = String.format("CREATE TABLE %s " +
                        "(%s INTEGER, %s TEXT, %s TEXT, %s INTEGER);",
                TimeTable.TABLE_NAME, TimeTable.USER_ID, TimeTable.DAY_OF_WEEK,
                TimeTable.START_TIME, TimeTable.END_TIME);



        public DataBaseHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(createUserTable);
            db.execSQL(createAddressTable);
            db.execSQL(createEducationTable);
            db.execSQL(createLessonTable);
            db.execSQL(createUserInfoTable);
//            db.execSQL(createTutorTable);
//            db.execSQL(createSubjectTable);
            db.execSQL(createUserSubjectTable);
            db.execSQL(createTimeTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
