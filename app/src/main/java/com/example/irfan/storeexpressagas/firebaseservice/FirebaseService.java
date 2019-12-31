package com.example.irfan.storeexpressagas.firebaseservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBackService;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.extras.Constants;
import com.example.irfan.storeexpressagas.extras.PrefManager;
import com.example.irfan.storeexpressagas.models.DeviceInfoRequest;
import com.example.irfan.storeexpressagas.models.DeviceInfoResponse;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class FirebaseService extends FirebaseInstanceIdService {
    private static final String TAG = "testme";
    private static final String TOPIC_CUSTOMER = "customer";
    public PrefManager sharedperference;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sharedperference = new PrefManager(this);
        // now subscribe to `global` topic to receive app wide notifications
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_CUSTOMER);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Log.d(TAG, "token: " + sharedperference.getToken());

        sharedperference.saveFCMToken(refreshedToken);
        if(sharedperference.getToken()==null || sharedperference.getToken()=="") {

        }
        else {
            sendRegistrationToServer(refreshedToken);
        }
        }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.

      //  showProgress();
        DeviceInfoRequest obj = new DeviceInfoRequest();
        obj.setAppType(Constants.APP_TYPE);
        obj.setFCMToken(token);


        Gson gson = new Gson();
        String Reslog= gson.toJson(obj);
        Log.d("testme", Reslog);

        RestClient.getAuthAdapterToekn(Auth.getToken(this)).updateDeviceInfo(obj).enqueue(new GeneralCallBackService<DeviceInfoResponse>(this) {
            @Override
            public void onSuccess(DeviceInfoResponse response) {
                Gson gson = new Gson();
                String Reslog= gson.toJson(response);
                Log.d("testme", Reslog);

                if(!response.getIserror()){
                    String msg=getApplicationContext().getString(R.string.msg_device_info_successfull);
                    Toast.makeText(getApplicationContext(),msg ,
                            Toast.LENGTH_LONG).show();
                }
                else{

                    String msg=getApplicationContext().getString(R.string.msg_device_info_failed);
                    Toast.makeText(getApplicationContext(),msg ,
                            Toast.LENGTH_LONG).show();

                }

                //hideProgress();



            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class
               // hideProgress();
                String msg=getApplicationContext().getString(R.string.msg_device_info_failed);
                Toast.makeText(getApplicationContext(),msg ,
                        Toast.LENGTH_LONG).show();

            }



        });

    }
}
