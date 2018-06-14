package com.nn.kovaleva.irina.schooltutor.core.transport;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.nn.kovaleva.irina.schooltutor.Model.JsonBaseResponse;
import com.nn.kovaleva.irina.schooltutor.core.Controller;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.ICommunicationManager;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.IOnRequestListener;
import com.nn.kovaleva.irina.schooltutor.core.transport.data.Request;
import com.nn.kovaleva.irina.schooltutor.server.RequestMethods;

import org.json.JSONException;
import org.json.JSONObject;

public class CommunicationManager implements ICommunicationManager{
    public static final String TAG = "CommunicationManager";

    Context mContext;

    public CommunicationManager(Context context) {
        mContext = context;
    }

    @Override
    public void sendJsonRequest(final Request request, final IOnRequestListener iOnRequestListener) {
        new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                String response = "{}";
                JsonBaseResponse baseResponse = new JsonBaseResponse();
                IOnRequestListener.ResponseCode cod = IOnRequestListener.ResponseCode.Ok;
                if(request.method.trim().equals("login")) {
//                    response = RequestMethods.logIn(mContext, request.data);
                    response = RequestMethods.logIn(Controller.getsInstance().context, request.data);
                }
                if(request.method.trim().equals("addUser")) {
//                    response = RequestMethods.addUser(mContext, request.data);
                    response = RequestMethods.addUser(Controller.getsInstance().context, request.data);
                }

                if (request.method.trim().equals("saveChangesProfile")){
                    response = RequestMethods.editProfile(Controller.getsInstance().context, request.data);
                }
                if (request.method.trim().equals("getAllUsers")){
                    response = RequestMethods.getAllUsers(Controller.getsInstance().context, request.data);
                }
                if (request.method.trim().equals("getUserById")){
                    response = RequestMethods.getUserById(Controller.getsInstance().context, request.data);
                }

//                if(request.method.trim().equals("getevent")){
//                    response = RequestMethods.giveEvent(mContext, request.data);
//                }
//                if(request.method.trim().equals("getteam")){
//                    response = RequestMethods.giveTeam(mContext, request.data);
//                }
//                if(request.method.trim().equals("getleague")){
//                    response = RequestMethods.giveLeague(mContext, request.data);
//                }
//                if(request.method.trim().equals("getfuturegames")){
//                    response = RequestMethods.giveFutureGames(mContext, request.data);
//                }
//                if(request.method.trim().equals("getpreviousgames")){
//                    response = RequestMethods.givePreviousGames(mContext, request.data);
//                }
//                if(request.method.trim().equals("changeprofileuser")){
//                    response = RequestMethods.changeProfileUser(mContext, request.data);
//                }
//                if(request.method.trim().equals("addnewevent")){
//                    response = RequestMethods.addNewEvent(mContext, request.data);
//                }
//                if(request.method.trim().equals("addnewuser")){
//                    response = RequestMethods.addNewUser( mContext, request.data);
//                }
//                if(request.method.trim().equals("addnewleague")){
//                    response = RequestMethods.addNewLeague(mContext, request.data);
//                }
//                if(request.method.trim().equals("getallusers")){
//                    response = RequestMethods.giveAllUsers(mContext);
//                }
//                if(request.method.trim().equals("addnewteam")){
//                    response = RequestMethods.addNewTeam(mContext, request.data);
//                }
//                if(request.method.trim().equals("getallteams")){
//                    response = RequestMethods.giveAllTeams(mContext);
//                }
                try {
                    baseResponse.fromJson(new JSONObject(response));
                    if (baseResponse.errorCode != 0){
                        cod = IOnRequestListener.ResponseCode.Error;
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "run: JSONException: " + e.getMessage() );
                }

                iOnRequestListener.onResponse(cod, response);
            }
        }.start();
    }
}
