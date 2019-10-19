package com.example.smsreceiveroffiong;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final int PERMISSIONS_REQUEST_SERVICE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
