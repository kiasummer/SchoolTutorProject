package com.nn.kovaleva.irina.schooltutor.core.transport;

import android.content.Context;

import com.nn.kovaleva.irina.schooltutor.core.interfaces.ICommunicationManager;
import com.nn.kovaleva.irina.schooltutor.core.interfaces.IOnRequestListener;
import com.nn.kovaleva.irina.schooltutor.core.transport.data.Request;
import com.nn.kovaleva.irina.schooltutor.server.RequestMethods;

public class CommunicationManager implements ICommunicationManager{

    Context mContext;

    public CommunicationManager(Context context) {
        mContext = context;
    }

    @Override
    public void sendJsonRequest(final Request request, final IOnRequestListener iOnRequestListener) {
        new Thread(){
            @Override
            public void run() {
                String response = "{}";
                IOnRequestListener.ResponseCode cod = IOnRequestListener.ResponseCode.Ok;
                if(request.method.trim().equals("login")) {
                    response = RequestMethods.logIn(mContext, request.data);
                }
                if(request.method.trim().equals("addUser")) {
                    response = RequestMethods.addUser(mContext, request.data);
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

                iOnRequestListener.onResponse(cod, response);
            }
        }.start();
    }
}
