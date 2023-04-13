package com.example.uebung_vi_i_unfallbericht;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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


    public boolean writeAccidentReport(AccidentReport ar, String fileName) {
        HAS_PERMISSIONS = checkForPermissions();
        if(!HAS_PERMISSIONS) rerequestPermissions();
        if(!HAS_PERMISSIONS) return false;
        File out = getFileStreamPath(fileName);
        ObjectOutputStream oos = null;
        try {
            out.createNewFile();
            FileOutputStream fos = new FileOutputStream(out);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ar);
        } catch (IOException e) {
            Log.e(this.getLocalClassName(), e.getLocalizedMessage());
        } finally {
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                Log.e(this.getLocalClassName(), e.getLocalizedMessage());
                return false;
            }
        }
        return true;
    }

    public AccidentReport readAccidentReport(String fileName) {
        HAS_PERMISSIONS = checkForPermissions();
        if(!HAS_PERMISSIONS) rerequestPermissions();
        if(!HAS_PERMISSIONS) return null;
        File in = getFileStreamPath(fileName);
        if(!in.exists()) return null;
        try {
            FileInputStream fis = new FileInputStream(in);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object read = ois.readObject();
            if(!(read instanceof AccidentReport)) {
                return null;
            }

            return (AccidentReport) read;
        } catch (Exception e) {
            Log.e(this.getLocalClassName(), e.getLocalizedMessage());
        }
        return null;
    }


}