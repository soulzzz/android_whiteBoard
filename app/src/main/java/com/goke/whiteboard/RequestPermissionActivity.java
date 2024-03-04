package com.goke.whiteboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class RequestPermissionActivity extends AppCompatActivity {

    private static final String[] permision1 = new String[]{"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final String[] permision2 = new String[]{"android.permission.READ_MEDIA_IMAGES","android.permission.READ_MEDIA_VIDEO","android.permission.READ_MEDIA_AUDIO"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R ) {
            if(checkPermission(permision1)){
                Intent it = new Intent(this,MainActivity.class);
                startActivity(it);
                finish();
            }else {
                requestPermissions(permision1,10085);
            }
        }else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU ){
            if(checkPermission(permision2)){
                Intent it = new Intent(this,MainActivity.class);
                startActivity(it);
                finish();
            }else {
                requestPermissions(permision2,10086);
            }
        }else{
            if(checkPermission(permision1)){
                Intent it = new Intent(this,MainActivity.class);
                startActivity(it);
                finish();
            }else {
                requestPermissions(permision1,10087);
            }
        }


    }
    public boolean checkPermission(String[] permision){
        for(int i=0;i<permision.length;i++){
            if(checkSelfPermission(permision[i])!= PackageManager.PERMISSION_GRANTED){
                Log.d("CheckPermissionActivity", "checkPermission:"+ permision[i]+"false");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 10086){
            for(int i=0;i<permissions.length;i++){
                if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    Log.d("CheckPermissionActivity", "onRequestPermissionsResult: fail ");
                    finish();
                    return;
                }
            }
            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);
            finish();
        }else if(requestCode ==10085){
            for(int i=0;i<permissions.length;i++){
                if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    Log.d("CheckPermissionActivity", "onRequestPermissionsResult: fail ");
                    finish();
                    return;
                }
            }
            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);
            finish();
        }else{
            for(int i=0;i<permissions.length;i++){
                if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    Log.d("CheckPermissionActivity", "onRequestPermissionsResult: fail ");
                    finish();
                    return;
                }
            }
            Intent it = new Intent(this,MainActivity.class);
            startActivity(it);
            finish();
        }

    }
}

