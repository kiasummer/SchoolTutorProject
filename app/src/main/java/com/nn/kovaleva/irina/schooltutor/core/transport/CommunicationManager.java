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
//                urlConnection.setRequestProperty("Content-Length",
//                        Integer.toString(mRequest.data.getBytes("UTF-8").length));
//                    urlConnection.setRequestProperty("Content-Type", "application/octet-stream; text/html; charset=utf-8");
                urlConnection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
                urlConnection.connect();
                os = urlConnection.getOutputStream();
                wos = new OutputStreamWriter(os, "UTF-8");
                wos.write(mRequest.data);
                //wos.close();
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
    }
}
