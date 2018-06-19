//package com.nn.kovaleva.irina.schooltutor.server;
//
//import android.content.Context;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.util.Log;
//
//import com.koushikdutta.async.http.body.StringBody;
//import com.koushikdutta.async.http.server.AsyncHttpServer;
//import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
//import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
//import com.koushikdutta.async.http.server.HttpServerRequestCallback;
//
//public class SchoolTutorHTTPServer {
//    public static final String TAG = "SchoolTutorHTTPServer";
//    AsyncHttpServer mServer = new AsyncHttpServer();
//    Context mContext = null;
//    private static SchoolTutorHTTPServer sInstance = null;
//
//    public static SchoolTutorHTTPServer Instance (){
//        if (sInstance == null){
//            sInstance = new SchoolTutorHTTPServer();
//        }
//        return sInstance;
//    }
//
//    public void setContext(Context context) {
//        mContext = context;
//    }
//
//    public void serverStart(){
//        Log.d(TAG, "serverStart: ");
//        mServer.post("/login", new HttpServerRequestCallback() {
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send(RequestMethods.logIn(mContext, ((StringBody)request.getBody()).get()));
//            }
//        });
//
//        mServer.post("/addUser", new HttpServerRequestCallback() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send(RequestMethods.addUser(mContext, ((StringBody)request.getBody()).get()));
//            }
//        });
//
//        mServer.post("/saveChangesProfile", new HttpServerRequestCallback() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send(RequestMethods.editProfile(mContext, ((StringBody)request.getBody()).get()));
//            }
//        });
//
//        mServer.post("/getAllUsers", new HttpServerRequestCallback() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send(RequestMethods.getAllUsers(mContext, ((StringBody)request.getBody()).get()));
//            }
//        });
//
//        mServer.post("/getUserById", new HttpServerRequestCallback() {
//            @Override
//            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
//                response.send(RequestMethods.getUserById(mContext, ((StringBody)request.getBody()).get()));
//            }
//        });
//
//        mServer.listen(7070);
//    }
//}
