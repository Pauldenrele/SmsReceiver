package com.example.smsreceiveroffiong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG ="SmsBroadcastReceiver";
    String msg, phoneNum = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.i(TAG , "Intent Received: " + intent.getAction() );

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
                Toast.makeText(context, "Message :" + msg +"\nphoneNum" + phoneNum, Toast.LENGTH_LONG).show();
            }
        }
    }
}
