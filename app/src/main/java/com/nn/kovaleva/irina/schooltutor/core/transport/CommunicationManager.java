package com.nn.kovaleva.irina.schooltutor.core.transport;

import android.util.Log;

import com.nn.kovaleva.irina.schooltutor.core.interfaces.ICommunicationManager;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.IOnRequestListener;
import com.nn.kovaleva.irina.schooltutor.core.transport.data.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunicationManager implements ICommunicationManager{
    public static final String TAG = "CommunicationManager";

    private class HttpClientWorker implements Runnable {
        public static final String TAG = "HttpClientWorker";
        Request mRequest;
        IOnRequestListener mIOnRequestListener;

        public HttpClientWorker(Request request, IOnRequestListener iOnRequestListener) {
            mRequest = request;
            mIOnRequestListener = iOnRequestListener;
        }


        public void run() {
            Log.d(TAG, "run: beginning of run");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String resultJson = null;
            IOnRequestListener.ResponseCode response_code = IOnRequestListener.ResponseCode.Error;


            String server = "http://127.0.0.1:7070";

            OutputStream os = null;
            OutputStreamWriter wos = null;
            InputStream is = null;

            try {
                URL url = new URL(server + "/" + mRequest.method);
                urlConnection = (HttpURLConnection) url.openConnection();
                //эту строчку может можно удалить
                urlConnection.setRequestMethod("POST");

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setConnectTimeout(1000 * 150);
                urlConnection.setReadTimeout(1000 * 150);
                //может убрать integer.to string
                urlConnection.setRequestProperty("Content-Length",
                        Integer.toString(mRequest.data.getBytes().length));
//                    urlConnection.setRequestProperty("Content-Type", "application/octet-stream; text/html; charset=utf-8");
                urlConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                urlConnection.connect();
                os = urlConnection.getOutputStream();
                wos = new OutputStreamWriter(os, "UTF8");
                wos.write(mRequest.data);
                wos.flush();

                if (urlConnection.getResponseCode() == 200) { //OK
                    Log.d(TAG, "run: connection is ok");
                    is = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    reader = new BufferedReader(new InputStreamReader(is));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    resultJson = buffer.toString();
                    response_code = IOnRequestListener.ResponseCode.Ok;
                }
                if (is != null) {
                    is.close();
                }

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "run: MalformedURLException: " + e.getMessage() );
            } catch (IOException e){
                Log.e(TAG, "run: IOException: " + e.getMessage());
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    Log.e(TAG, "run: IOException: " + ex.getMessage() );
                }
            }
            mIOnRequestListener.onResponse(response_code, resultJson);
        }
    }


    @Override
    public void sendJsonRequest(final Request request, final IOnRequestListener iOnRequestListener) {
        Log.d(TAG, "sendJsonRequest: ");
        new Thread(new HttpClientWorker(request, iOnRequestListener)).start();
//        new Thread(){
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void run() {
//                String response = "{}";
//                JsonBaseResponse baseResponse = new JsonBaseResponse();
//                IOnRequestListener.ResponseCode cod = IOnRequestListener.ResponseCode.Ok;
//                if(request.method.trim().equals("login")) {
////                    response = RequestMethods.logIn(mContext, request.data);
//                    response = RequestMethods.logIn(Controller.getsInstance().context, request.data);
//                }
//                if(request.method.trim().equals("addUser")) {
////                    response = RequestMethods.addUser(mContext, request.data);
//                    response = RequestMethods.addUser(Controller.getsInstance().context, request.data);
//                }
//
//                if (request.method.trim().equals("saveChangesProfile")){
//                    response = RequestMethods.editProfile(Controller.getsInstance().context, request.data);
//                }
//                if (request.method.trim().equals("getAllUsers")){
//                    response = RequestMethods.getAllUsers(Controller.getsInstance().context, request.data);
//                }
//                if (request.method.trim().equals("getUserById")){
//                    response = RequestMethods.getUserById(Controller.getsInstance().context, request.data);
//                }
//
////                if(request.method.trim().equals("getevent")){
////                    response = RequestMethods.giveEvent(mContext, request.data);
////                }
////                if(request.method.trim().equals("getteam")){
////                    response = RequestMethods.giveTeam(mContext, request.data);
////                }
////                if(request.method.trim().equals("getleague")){
////                    response = RequestMethods.giveLeague(mContext, request.data);
////                }
////                if(request.method.trim().equals("getfuturegames")){
////                    response = RequestMethods.giveFutureGames(mContext, request.data);
////                }
////                if(request.method.trim().equals("getpreviousgames")){
////                    response = RequestMethods.givePreviousGames(mContext, request.data);
////                }
////                if(request.method.trim().equals("changeprofileuser")){
////                    response = RequestMethods.changeProfileUser(mContext, request.data);
////                }
////                if(request.method.trim().equals("addnewevent")){
////                    response = RequestMethods.addNewEvent(mContext, request.data);
////                }
////                if(request.method.trim().equals("addnewuser")){
////                    response = RequestMethods.addNewUser( mContext, request.data);
////                }
////                if(request.method.trim().equals("addnewleague")){
////                    response = RequestMethods.addNewLeague(mContext, request.data);
////                }
////                if(request.method.trim().equals("getallusers")){
////                    response = RequestMethods.giveAllUsers(mContext);
////                }
////                if(request.method.trim().equals("addnewteam")){
////                    response = RequestMethods.addNewTeam(mContext, request.data);
////                }
////                if(request.method.trim().equals("getallteams")){
////                    response = RequestMethods.giveAllTeams(mContext);
////                }
//                try {
//                    baseResponse.fromJson(new JSONObject(response));
//                    if (baseResponse.errorCode != 0){
//                        cod = IOnRequestListener.ResponseCode.Error;
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, "run: JSONException: " + e.getMessage() );
//                }
//
//                iOnRequestListener.onResponse(cod, response);
//            }
//        }.start();
    }
}
