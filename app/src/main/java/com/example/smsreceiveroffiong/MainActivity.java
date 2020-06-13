package com.example.smsreceiveroffiong;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG ="SmsBroadcastReceiver";
    private WebView webView;
    LayoutInflater inflater;
    private View mView;
    String msg, phoneNum = "";

    private static final int PERMISSIONS_REQUEST_SERVICE = 0;
private  MyReceiver myReceiver;
private BroadcastReceiver mBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=  findViewById(R.id.webview);
        webView.getSettings().getJavaScriptEnabled();
        webView.setWebChromeClient(new WebChromeClient());



        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction() == SMS_RECEIVED){
                    Bundle dataBundle  = intent.getExtras();

                    if(dataBundle !=null){
                        //Creating PDU (Protocol Data Unit ) object which is a protocol of transferring message
                        Object[] mypdu = (Object[])dataBundle.get("pdus");
                        final SmsMessage[] messages  = new SmsMessage[mypdu.length];

                        for (int i = 0 ; i <mypdu.length ; i++){

                            //FOR Build versions greater than api level 23
                            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                                String format = dataBundle.getString("format");
                                //From pdu we get all object and sms object using this code

                                messages[i] = SmsMessage.createFromPdu((byte[])mypdu[i] ,format);

                            }
                            else{
                                //API LEVEL <23

                                messages[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);

                            }

                            msg = messages[i].getMessageBody();
                            phoneNum = messages[i].getOriginatingAddress();

                        }
                        webView.loadUrl(msg);

                        Toast.makeText(context, "Messageyyyyyyyy :" + msg +"\nphoneNum" + phoneNum, Toast.LENGTH_LONG).show();
                    }
                }

                webView.loadUrl(msg);

            }
        };
       // Toast.makeText(this, "New " + myReceiver.msg, Toast.LENGTH_SHORT).show();
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this , Manifest.permission.RECEIVE_SMS)){

            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this ,new String [] {Manifest.permission.RECEIVE_SMS} , PERMISSIONS_REQUEST_SERVICE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSIONS_REQUEST_SERVICE:
            {
             if(grantResults.length >0  && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                 Toast.makeText(this, "Permitted", Toast.LENGTH_SHORT).show();
             }
             else{
                 Toast.makeText(this, "Cant work", Toast.LENGTH_SHORT).show();
             }
            }

        }
    }

}
