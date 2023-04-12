package com.example.uebung_vi_i_unfallbericht;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int RQC_STORAGE = 1;

    private static boolean HAS_PERMISSIONS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HAS_PERMISSIONS = checkForPermissions();

        if(!HAS_PERMISSIONS) rerequestPermissions();


    }

    private boolean checkForPermissions() {
        int hasWrite = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasRead = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        if(hasWrite == PackageManager.PERMISSION_GRANTED && hasRead == PackageManager.PERMISSION_GRANTED) return true;
        return false;
    }

    private void rerequestPermissions() {
        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RQC_STORAGE);
        Log.d(this.getLocalClassName(), "Permissions rerequested!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == RQC_STORAGE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thank you for granting the permissions", Toast.LENGTH_SHORT);
                HAS_PERMISSIONS = true;
            } else {
                Toast.makeText(this, "This Permissions are required to work", Toast.LENGTH_LONG);
                HAS_PERMISSIONS = false;
            }
        }
    }


}