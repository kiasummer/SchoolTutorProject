package com.nn.kovaleva.irina.schooltutor.core.interfaces;

import com.nn.kovaleva.irina.schooltutor.core.transport.data.Request;

public interface ICommunicationManager {
    void sendJsonRequest(Request request, IOnRequestListener iOnRequestListener);
}
