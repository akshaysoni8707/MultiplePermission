package com.app.multiplepermission;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<String[]> PermissionResultLauncher;

    private boolean isPhoneCallPermissionGranted = false;
    private boolean isCameraPermissionGranted = false;
    private boolean isMediaLocationPermissionGranted = false;
    private boolean isAudioRecordPermissionGranted = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionResultLauncher=registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if(result.get(Manifest.permission.CALL_PHONE)!= null){
                    isPhoneCallPermissionGranted=result.get(Manifest.permission.CALL_PHONE);
                }
                if (result.get(Manifest.permission.CAMERA)!=null){
                    isCameraPermissionGranted=result.get(Manifest.permission.CAMERA);
                }
                if(result.get(Manifest.permission.ACCESS_MEDIA_LOCATION)!=null){
                    isMediaLocationPermissionGranted=result.get(Manifest.permission.ACCESS_MEDIA_LOCATION);
                }
                if(result.get(Manifest.permission.RECORD_AUDIO)!=null){
                    isAudioRecordPermissionGranted=result.get(Manifest.permission.RECORD_AUDIO);
                }
            }
        });
        requestPermission();
    }


    private void requestPermission() {
        isPhoneCallPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

        isCameraPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        isAudioRecordPermissionGranted = ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED;

        isMediaLocationPermissionGranted=ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_MEDIA_LOCATION)==PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequest=new ArrayList<>();
        if(!isPhoneCallPermissionGranted){
            permissionRequest.add(Manifest.permission.CALL_PHONE);
        }
        if(!isCameraPermissionGranted){
            permissionRequest.add(Manifest.permission.CAMERA);
        }
        if(!isMediaLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_MEDIA_LOCATION);
        }
        if(!isAudioRecordPermissionGranted){
            permissionRequest.add(Manifest.permission.RECORD_AUDIO);
        }

        if(!permissionRequest.isEmpty()){
            PermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }
    }

}