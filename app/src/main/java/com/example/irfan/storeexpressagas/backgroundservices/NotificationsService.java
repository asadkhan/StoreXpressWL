package com.example.irfan.storeexpressagas.backgroundservices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.irfan.storeexpressagas.R;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBack;
import com.example.irfan.storeexpressagas.abstract_classess.GeneralCallBackService;
import com.example.irfan.storeexpressagas.extras.Auth;
import com.example.irfan.storeexpressagas.extras.Constants;
import com.example.irfan.storeexpressagas.models.CustomerOrderResponse;
import com.example.irfan.storeexpressagas.models.NotificationResponse;
import com.example.irfan.storeexpressagas.network.RestClient;
import com.google.gson.Gson;

import java.util.List;

public class NotificationsService extends Service {
    Intent intent;
    public int counter = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    private static final int NOTIFICATION_ID = 1;
    Context context;
    public static String str_receiver = "servicetutorial.service.receiver";


    private final static int INTERVAL = 40000; //1 minutes
    Handler mHandler = new Handler();

    Runnable mHandlerTask = new Runnable()
    {
        @Override
        public void run() {
           // Toast.makeText(NotificationsService.this, "running", Toast.LENGTH_SHORT).show();
            Log.d("testme","Token "+Auth.getToken(NotificationsService.this));
            if(Auth.getToken(NotificationsService.this)==null || Auth.getToken(NotificationsService.this)==""){




            }
            else{


                Log.d("testme","in callig");
                RestClient.getAuthAdapterToekn(Auth.getToken(NotificationsService.this)).getNotifications().enqueue(new GeneralCallBackService<NotificationResponse>(NotificationsService.this) {
                    @Override
                    public void onSuccess(NotificationResponse response) {


                        //Log.d("testme","Token "+Auth.getToken(NotificationsService.this));
                        Gson gson = new Gson();
                        String Reslog= gson.toJson(response);
                        Log.d("testme", Reslog);

                        if (!response.getIserror()) {


                            if(response.getValue().size() >0) {

                                List<NotificationResponse.Value> list = response.getValue();
                                for (NotificationResponse.Value obj : list) {

                                    showForegroundNotification(obj.getMessege(),obj.getTitle());
                                    break;
                                }


                            }


                            //getFproducts();

                        }
                        else{



                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        //onFailure implementation would be in GeneralCallBack class

                        Log.d("test",throwable.getMessage());

                    }



                });



            }


            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    void startRepeatingTask()
    {
        mHandlerTask.run();
    }

    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "service Started....", Toast.LENGTH_SHORT).show();

        super.onCreate();

        intent = new Intent(str_receiver);
        String input ="test";
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, NotificationsService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)

                .setContentIntent(pendingIntent)
                .build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, notification);
        }
        startRepeatingTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true); //true will remove notification
        }
        stopRepeatingTask();
        Toast.makeText(this, "service destroy", Toast.LENGTH_SHORT).show();
        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    private void showForegroundNotification(String contentText,String title) {
        // Create intent that will bring our app to the front, as if it was tapped in the app
        // launcher

        Log.d("testme", "call noy");
        Intent showTaskIntent = new Intent(getApplicationContext(), NotificationsService.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(contentText)
                .setSmallIcon(R.mipmap.notification)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .build();
        startForeground(NOTIFICATION_ID, notification);
    }

}
