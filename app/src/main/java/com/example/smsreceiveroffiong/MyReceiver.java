package com.example.smsreceiveroffiong;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class MyReceiver extends  BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG ="SmsBroadcastReceiver";
    private WebView webView;
    LayoutInflater inflater;
    private View mView;
    String msg, phoneNum = "";





    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.i(TAG , "Intent Received: " + intent.getAction() );



      // webView=  mView.findViewById(R.id.webview);
   //     webView.getSettings().getJavaScriptEnabled();
   //     webView.setWebChromeClient(new WebChromeClient());
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
                    else {
                        //API LEVEL <23

                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);

                    }



                    msg = messages[i].getMessageBody();
                    phoneNum = messages[i].getOriginatingAddress();

                   String url = "http://www.example.com";


                 /*   if (!msg.startsWith("http://") && !msg.startsWith("https://"))
                    {
                        msg = "http://" + msg;
*/
                     /*   Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent1);
*/

                    Intent intent1 = new Intent(context , Main2Activity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //intent.setData(Uri.parse(url));
                    intent1.putExtra("STRING" , msg);
                    context.startActivity(intent1);

                    // }


                }
             //   webView.loadUrl(msg);

             /*   if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
*/

                Toast.makeText(context, "Message :" + msg +"\nphoneNum" + phoneNum, Toast.LENGTH_LONG).show();
            }
        }
    }
}
