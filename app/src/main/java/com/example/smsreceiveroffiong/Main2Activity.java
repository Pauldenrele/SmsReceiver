package com.example.smsreceiveroffiong;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private WebView webView;
    TextView textView;
    String newString;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        webView= findViewById(R.id.webview);
       // textView = findViewById(R.id.text);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
     //   Bundle extras = getIntent().getExtras();
        
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl( newString);
        WebSettings webSettings =webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
       // textView.setText(newString);
        Toast.makeText(this, newString, Toast.LENGTH_SHORT).show();
    }


}
