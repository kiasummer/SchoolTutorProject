//package com.nn.kovaleva.irina.schooltutor.server;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.location.Address;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.util.Log;
//
//import com.nn.kovaleva.irina.schooltutor.Model.Actor;
//import com.nn.kovaleva.irina.schooltutor.Model.Education;
//import com.nn.kovaleva.irina.schooltutor.Model.LogIn;
//import com.nn.kovaleva.irina.schooltutor.Model.User;
//import com.nn.kovaleva.irina.schooltutor.Model.UserList;
//import com.nn.kovaleva.irina.schooltutor.core.Controller;
//import com.nn.kovaleva.irina.schooltutor.core.transport.data.Request;
//import com.nn.kovaleva.irina.schooltutor.provider.tables.AddressTable;
//import com.nn.kovaleva.irina.schooltutor.provider.tables.EducationTable;
////import com.nn.kovaleva.irina.schooltutor.provider.tables.SubjectTable;
//import com.nn.kovaleva.irina.schooltutor.provider.tables.UserInfo;
//import com.nn.kovaleva.irina.schooltutor.provider.tables.UserSubjectTable;
//import com.nn.kovaleva.irina.schooltutor.provider.tables.UserTable;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class RequestMethods {
//    private static final String TAG = "RequestMethods";
//    private static boolean ifAllOk;
//
//    public static String logIn(Context context, String request){
//        Log.d(TAG, "logIn: try to log in db");
//        String response = null;
//        User user = null;
//        LogIn logIn = new LogIn();
//        try{
//            JSONObject obj = new JSONObject(request);
//            logIn.fromJson(obj);
//        } catch (JSONException e){
//            Log.e(TAG, "addUser: JSONException: " + e.getMessage());
//            logIn = null;
//        }
//
//        if (logIn != null){
//            Cursor c = context.getContentResolver().query(UserTable.CONTENT_URI, null, null,
//                    null, null);
//            if (c.moveToFirst() && c.getCount() != 0){
//                Log.d(TAG, "In this db ");
//                do {
//                    Log.d(TAG, " " + c.getString(c.getColumnIndex(UserTable._ID))
//                            + " " + c.getString(c.getColumnIndex(UserTable.LOGIN)) + " " +
//                    c.getString(c.getColumnIndex(UserTable.PASSWORD)) + " " +
//                            c.getInt(c.getColumnIndex(UserTable.IFTUTOR)));
//                } while (c.moveToNext());
//            }
//            String[] selectionArgs = new String[] {logIn.userName};
//            Cursor cursor = context.getContentResolver().query(UserTable.CONTENT_URI, null,
//                    UserTable.LOGIN + "= ?", selectionArgs, null);
//            if (cursor.moveToFirst() && cursor.getCount() == 1){
//                if (cursor.getString(cursor.getColumnIndex(UserTable.LOGIN)).equals(logIn.userName)
//                        && cursor.getString(cursor.getColumnIndex(UserTable.PASSWORD)).equals(logIn.password)){
//                    int id = cursor.getInt(cursor.getColumnIndex(UserTable._ID));
//                    user = new User();
//                    user.userId = id;
//                    user.login = cursor.getString(cursor.getColumnIndex(UserTable.LOGIN));
//                    user.password = cursor.getString(cursor.getColumnIndex(UserTable.PASSWORD));
//                    user.telNumber = cursor.getString(cursor.getColumnIndex(UserTable.PHONE));
//                    int ifTutor = cursor.getInt(cursor.getColumnIndex(UserTable.IFTUTOR));
//                    user.ifTutor = (ifTutor == 1);
//
//                    //write extra info
//                    selectionArgs = new String[] {String.valueOf(id)};
//                    Cursor cursor1 = context.getContentResolver().query(UserInfo.CONTENT_URI, null,
//                            UserInfo.USER_ID + "= ?", selectionArgs, null);
//                    if (cursor1 != null && cursor1.moveToFirst()){
//                        user.firstName = cursor1.getString(cursor1.getColumnIndex
//                                (UserInfo.FIRST_NAME));
//                        user.secondName = cursor1.getString(cursor1.getColumnIndex
//                                (UserInfo.SECOND_NAME));
//                        if (ifTutor == 1) {
//                            user.cost = cursor1.getInt(cursor1.getColumnIndex(UserInfo.COST));
////                            user.patronymic = cursor1.getString(
////                                    cursor1.getColumnIndex(UserInfo.PATRONYMIC));
//                        } else {
//                            user.yearOfEducation = cursor1.getInt
//                                    (cursor1.getColumnIndex(UserInfo.YEAR_OF_EDUCATION));
//
//                        }
//                    }
//
//                    //write subjects of actor
//                    Cursor cursor2 = context.getContentResolver().query(UserSubjectTable.CONTENT_URI, null,
//                            UserSubjectTable.USER_ID + "= ?", selectionArgs, null);
//                    if (cursor2 != null && cursor2.moveToFirst()){
//                        do {
//                            user.themes.add(cursor2.getString(cursor2.getColumnIndex
//                                    (UserSubjectTable.SUBJECT_NAME)));
//                        } while (cursor2.moveToNext());
//                    }
//
//                    //write address
//                    Cursor cursor3 = context.getContentResolver().query(AddressTable.CONTENT_URI, null,
//                            AddressTable.USER_ID + "= ?", selectionArgs, null);
//                    if (cursor3 != null && cursor3.moveToFirst()){
//                        user.address = cursor3.getString(cursor3.getColumnIndex
//                                (AddressTable.ADDRESS));
//                        user.ifAtHome = (cursor3.getInt(cursor3.getColumnIndex
//                                (AddressTable.IF_AT_HOME)) == 1);
//                    }
//
//                    //write education
//                    if (ifTutor == 1){
//                        Cursor cursor4 = context.getContentResolver().query(EducationTable.CONTENT_URI,
//                                null,EducationTable.USER_ID + "= ?",
//                                selectionArgs, null);
//                        if (cursor4 != null && cursor4.moveToFirst()){
//                            do {
//                                Education education = new Education();
//                                education.nameOfUniversity = cursor4.getString(cursor4.getColumnIndex
//                                        (EducationTable.UNIVERSITY));
//                                education.faculty = cursor4.getString(cursor4.getColumnIndex
//                                        (EducationTable.FACULTY));
//                                education.yearOfEnd = cursor4.getInt(cursor4.getColumnIndex
//                                        (EducationTable.ENDING_YEAR));
//                                user.educations.add(education);
//                            } while (cursor4.moveToNext());
//                        }
//                    }
//                    //response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                } else{
//                    response = "{\"message\":\"Incorrect password\",\"errorCode\":401}";
//                }
//            } else {
//                response = "{\"message\":\"Login not found\",\"errorCode\":400}";
//            }
//        } else {
//            response = "{\"message\":\"Response Error\",\"errorCode\":500}";
//        }
//        if (user != null){
//            user.message = "Ok";
//            user.errorCode = 0;
//            response = user.toJson().toString();
//        }
//        return response;
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String addUser(Context context, String request){
//        Log.d(TAG, "addUser: try to add user in db");
//        String response = null;
//        User user = new User();
//        try {
//            JSONObject obj = new JSONObject(request);
//            user.fromJson(obj);
//        } catch (JSONException e){
//            Log.e(TAG, "addUser: JSONException: " + e.getMessage());
//            user = null;
//        }
//
//        if (user != null){
//            String[] selectionArgs = new String[] {user.login};
//            Cursor cursor = context.getContentResolver().query(UserTable.CONTENT_URI, null,
//                    UserTable.LOGIN + "= ?", selectionArgs, null);
//            if (cursor != null && cursor.getCount() == 0){
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(UserTable.LOGIN, user.login);
//                contentValues.put(UserTable.PASSWORD, user.password);
//                contentValues.put(UserTable.PHONE, user.telNumber);
//                contentValues.put(UserTable.IFTUTOR, (user.ifTutor ? 1 : 0));
//                if (context.getContentResolver().insert(UserTable.CONTENT_URI, contentValues) == null){
//                    response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                } else {
//                    user.message = "Ok";
//                    user.errorCode = 0;
//                    response = user.toJson().toString();
//                    //response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                }
//            } else {
//                response = "{\"message\":\"Already exists\",\"errorCode\":400}";
//            }
//        } else {
//            response = "{\"message\":\"Response Error\",\"errorCode\":500}";
//        }
//
//        return response;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String editProfile(Context context, String request){
//        Log.d(TAG, "editProfile: try to save changes");
//        String response = null;
//        User user = new User();
//        try {
//            JSONObject obj = new JSONObject(request);
//            user.fromJson(obj);
//        } catch (JSONException e){
//            Log.e(TAG, "addUser: JSONException: " + e.getMessage());
//            user = null;
//        }
//
//        if (user != null){
//            ifAllOk = true;
//            response = RequestMethods.tryToAddUserInfo(user, context);
//            if (ifAllOk){
//                response = RequestMethods.tryToAddAddressInfo(user, context);
//            }
//            if (ifAllOk){
//                response = RequestMethods.tryToAddThingInfo(user, context);
//            }
//            if (ifAllOk && Actor.getsInstance().ifTutor){
//                response = RequestMethods.tryToAddEducationInfo(user, context);
//            }
//            if (ifAllOk){
//                user.message = "Ok";
//                user.errorCode = 0;
//                response = user.toJson().toString();
//            }
//        } else {
//            response = "{\"message\":\"Response Error\",\"errorCode\":500}";
//        }
//        return response;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static String getAllUsers(Context context, String request){
//        Log.d(TAG, "addUser: try to add user in db");
//        String response = null;
//        User user = new User();
//        try {
//            JSONObject obj = new JSONObject(request);
//            user.fromJson(obj);
//        } catch (JSONException e){
//            Log.e(TAG, "getAllUsers: JSONException: " + e.getMessage());
//            user = null;
//        }
//
//        if (user != null){
//            String[] selectionArgs;
//            if (user.ifTutor){
//                selectionArgs = new String[] {"0"};
//            } else { selectionArgs = new String[] {"1"}; }
//            Cursor cursor = context.getContentResolver().query(UserTable.CONTENT_URI, null,
//                    UserTable.IFTUTOR + "= ?", selectionArgs, null);
//            ArrayList<Integer> indexes = new ArrayList<>();
//            if (cursor != null && cursor.moveToFirst() && cursor.getCount() != 0) {
//                do {
//                    indexes.add(cursor.getInt(cursor.getColumnIndex(UserTable._ID)));
//                } while (cursor.moveToNext());
//                UserList users = new UserList();
//                String[] selectionArgsById;
//                for (int i = 0; i < indexes.size(); i++) {
//                    User addingUser = new User();
//                    addingUser.userId = indexes.get(i);
//                    selectionArgsById = new String[]{String.valueOf(indexes.get(i))};
//                    Cursor cursor1 = context.getContentResolver().query(UserInfo.CONTENT_URI, null,
//                            UserInfo.USER_ID + "= ?", selectionArgsById, null);
//                    if (cursor1 != null && cursor1.moveToFirst()) {
//                        addingUser.firstName = cursor1.getString(cursor1.getColumnIndex(UserInfo.FIRST_NAME));
//                        addingUser.secondName = cursor1.getString(cursor1.getColumnIndex(UserInfo.SECOND_NAME));
//                    }
//                    Cursor cursor2 = context.getContentResolver().query(UserSubjectTable.CONTENT_URI, null,
//                            UserSubjectTable.USER_ID + "= ?", selectionArgsById, null);
//                    if (cursor2 != null && cursor2.moveToFirst()) {
//                        do {
//                            addingUser.themes.add(cursor2.getString(cursor2.getColumnIndex
//                                    (UserSubjectTable.SUBJECT_NAME)));
//                        } while (cursor2.moveToNext());
//                    }
//                    users.userList.add(addingUser);
//                }
//                users.message = "Ok";
//                users.errorCode = 0;
//                response = users.toJson().toString();
//                //response = "{\"message\":\"Ok\",\"errorCode\":0}";
//            } else {
//                response = "{\"message\":\"Users not found\",\"errorCode\":400}";
//            }
//        } else {
//            response = "{\"message\":\"Response Error\",\"errorCode\":500}";
//        }
//
//        return response;
//    }
//
//    public static String getUserById(Context context, String request) {
//        Log.d(TAG, "getUserById: try to find user by id");
//        String response = null;
//        User user = new User();
//        String[] selectionArgs;
//        selectionArgs = new String[]{request};
//        Cursor cursor = context.getContentResolver().query(UserTable.CONTENT_URI, null,
//                UserTable._ID + "= ?", selectionArgs, null);
//        if (cursor != null && cursor.moveToFirst() && cursor.getCount() == 1) {
//            user.userId = Integer.parseInt(request);
//            user.telNumber = cursor.getString(cursor.getColumnIndex(UserTable.PHONE));
//            Cursor cursor1 = context.getContentResolver().query(UserInfo.CONTENT_URI, null,
//                    UserInfo.USER_ID + "= ?", selectionArgs, null);
//            if (cursor1 != null && cursor1.moveToFirst()) {
//                user.firstName = cursor1.getString(cursor1.getColumnIndex(UserInfo.FIRST_NAME));
//                user.secondName = cursor1.getString(cursor1.getColumnIndex(UserInfo.SECOND_NAME));
//                user.cost = cursor1.getInt(cursor1.getColumnIndex(UserInfo.COST));
//                user.yearOfEducation = cursor1.getInt(cursor1.getColumnIndex(UserInfo.YEAR_OF_EDUCATION));
//            }
//            Cursor cursor2 = context.getContentResolver().query(AddressTable.CONTENT_URI, null,
//                    AddressTable.USER_ID + "= ?", selectionArgs, null);
//            if (cursor2 != null && cursor2.moveToFirst()) {
//                user.address = cursor2.getString(cursor2.getColumnIndex(AddressTable.ADDRESS));
//                user.ifAtHome = cursor2.getInt(cursor2.getColumnIndex(AddressTable.IF_AT_HOME)) == 1;
//            }
//            Cursor cursor3 = context.getContentResolver().query(UserSubjectTable.CONTENT_URI, null,
//                    UserSubjectTable.USER_ID + "= ?", selectionArgs, null);
//            if (cursor3 != null && cursor3.moveToFirst() && cursor3.getCount() != 0){
//                do {
//                    user.themes.add(cursor3.getString(cursor3.getColumnIndex
//                            (UserSubjectTable.SUBJECT_NAME)));
//                } while (cursor3.moveToNext());
//            }
//            Cursor cursor4 = context.getContentResolver().query(EducationTable.CONTENT_URI, null,
//                    EducationTable.USER_ID + "= ?", selectionArgs, null);
//            if (cursor4 != null && cursor4.moveToFirst() && cursor4.getCount() != 0){
//                do {
//                    Education education = new Education();
//                    education.nameOfUniversity = cursor4.getString(cursor4
//                            .getColumnIndex(EducationTable.UNIVERSITY));
//                    education.faculty= cursor4.getString(cursor4
//                            .getColumnIndex(EducationTable.FACULTY));
//                    education.yearOfEnd = cursor4.getInt(cursor4
//                            .getColumnIndex(EducationTable.ENDING_YEAR));
//                    user.educations.add(education);
//                } while (cursor4.moveToNext());
//            }
//            user.message = "Ok";
//            user.errorCode = 0;
//            response = user.toJson().toString();
//        } else {
//            response = "{\"message\":\"User not found\",\"errorCode\":400}";
//        }
//        return response;
//    }
//
//    private static String tryToAddUserInfo(User user, Context context){
//        Log.d(TAG, "tryToAddUserInfo: ");
//        String response = null;
//        String[] selectionArgs = new String[] {String.valueOf(Actor.getsInstance().id)};
//        Cursor cursor = context.getContentResolver().query(UserInfo.CONTENT_URI, null,
//                UserInfo.USER_ID + "= ?", selectionArgs, null);
//        if (cursor != null){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(UserInfo.USER_ID, Actor.getsInstance().id);
//            contentValues.put(UserInfo.FIRST_NAME, user.firstName);
//            //contentValues.put(UserInfo.PATRONYMIC, user.patronymic);
//            contentValues.put(UserInfo.SECOND_NAME, user.secondName);
//            contentValues.put(UserInfo.COST, user.cost);
//            contentValues.put(UserInfo.YEAR_OF_EDUCATION, user.yearOfEducation);
//
//            if (cursor.getCount() == 0) {
//                if (context.getContentResolver().insert(UserInfo.CONTENT_URI, contentValues) == null) {
//                    response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                    ifAllOk = false;
//                    return response;
//                } else {
//                    response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                }
//            } else if (cursor.getCount() == 1){
//                if (context.getContentResolver().update(UserInfo.CONTENT_URI, contentValues,
//                        UserInfo.USER_ID + "= ?", selectionArgs) == -1) {
//                    response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                    ifAllOk = false;
//                    return response;
//                } else {
//                    response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                }
//            }
//        } else {
//            response = "{\"message\":\"Provider error\",\"errorCode\":400}";
//        }
//        return response;
//    }
//
//    private static String tryToAddAddressInfo(User user, Context context){
//        Log.d(TAG, "tryToAddAddressInfo: ");
//        String response = null;
//        String[] selectionArgs = new String[] {String.valueOf(Actor.getsInstance().id)};
//        Cursor cursor = context.getContentResolver().query(AddressTable.CONTENT_URI, null,
//                AddressTable.USER_ID + "= ?", selectionArgs, null);
//        if (cursor != null){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(AddressTable.IF_AT_HOME, (user.ifAtHome) ? 1 : 0);
//            contentValues.put(AddressTable.ADDRESS, user.address);
//            contentValues.put(AddressTable.USER_ID, Actor.getsInstance().id);
//
//            if (cursor.getCount() == 0) {
//                if (context.getContentResolver().insert(AddressTable.CONTENT_URI, contentValues) == null) {
//                    response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                    ifAllOk = false;
//                    return response;
//                } else {
//                    response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                }
//            } else if (cursor.getCount() == 1){
//                if (context.getContentResolver().update(AddressTable.CONTENT_URI, contentValues,
//                        AddressTable.USER_ID + "= ?", selectionArgs) == -1) {
//                    response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                    ifAllOk = false;
//                    return response;
//                } else {
//                    response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                }
//            }
//
//        } else {
//            response = "{\"message\":\"Provider error\",\"errorCode\":400}";
//        }
//        return response;
//    }
//
//    private static String tryToAddThingInfo(User user, Context context){
//        Log.d(TAG, "tryToAddThingInfo: ");
//        String response = null;
//        String[] selectionArgs = new String[] {String.valueOf(Actor.getsInstance().id)};
//        Cursor cursor = context.getContentResolver().query(UserSubjectTable.CONTENT_URI, null,
//                UserSubjectTable.USER_ID + "= ?", selectionArgs, null);
//        if (cursor != null && cursor.getCount() != 0){
//            cursor.moveToFirst();
//            do {
//                if (context.getContentResolver().delete(UserSubjectTable.CONTENT_URI,
//                        UserSubjectTable.USER_ID + "= ?", selectionArgs) == -1){
//                    response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                    ifAllOk = false;
//                    return response;
//                } else {
//                    response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                }
//            } while (cursor.moveToNext());
//
//        }
//        for (String s : user.themes){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(UserSubjectTable.USER_ID, Actor.getsInstance().id);
//            contentValues.put(UserSubjectTable.SUBJECT_NAME, s);
//            if (context.getContentResolver().insert(UserSubjectTable.CONTENT_URI, contentValues) == null) {
//                response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                ifAllOk = false;
//                return response;
//            } else {
//                response = "{\"message\":\"Ok\",\"errorCode\":0}";
//            }
//        }
//        return response;
//    }
//
//    private static String tryToAddEducationInfo(User user, Context context){
//        Log.d(TAG, "tryToAddEducationInfo: ");
//        String response = null;
//        String[] selectionArgs = new String[] {String.valueOf(Actor.getsInstance().id)};
//        Cursor cursor = context.getContentResolver().query(EducationTable.CONTENT_URI, null,
//                EducationTable.USER_ID + "= ?", selectionArgs, null);
//        if (cursor != null && cursor.getCount() != 0){
//            cursor.moveToFirst();
//            do {
//                if (context.getContentResolver().delete(EducationTable.CONTENT_URI,
//                        EducationTable.USER_ID + "= ?", selectionArgs) == -1){
//                    response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                    ifAllOk = false;
//                    return response;
//                } else {
//                    response = "{\"message\":\"Ok\",\"errorCode\":0}";
//                }
//            } while (cursor.moveToNext());
//
//        }
//        for (Education ed : user.educations){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(EducationTable.UNIVERSITY, ed.nameOfUniversity);
//            contentValues.put(EducationTable.FACULTY, ed.faculty);
//            contentValues.put(EducationTable.ENDING_YEAR, ed.yearOfEnd);
//            contentValues.put(EducationTable.USER_ID, Actor.getsInstance().id);
//            if (context.getContentResolver().insert(EducationTable.CONTENT_URI, contentValues) == null) {
//                response = "{\"message\":\"Internal Server Error\",\"errorCode\":501}";
//                ifAllOk = false;
//                return response;
//            } else {
//                response = "{\"message\":\"Ok\",\"errorCode\":0}";
//            }
//        }
//        return response;
//    }
//}
