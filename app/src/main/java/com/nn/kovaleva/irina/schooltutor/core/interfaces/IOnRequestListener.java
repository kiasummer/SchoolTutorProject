package com.nn.kovaleva.irina.schooltutor.core.interfaces;

public interface IOnRequestListener {
    enum ResponseCode {Ok, Error};
    void onResponse(ResponseCode responseCode, String response);
}
