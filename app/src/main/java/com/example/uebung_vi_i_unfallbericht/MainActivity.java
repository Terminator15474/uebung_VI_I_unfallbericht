package com.example.uebung_vi_i_unfallbericht;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final int RQC_STORAGE = 1;

    private static boolean HAS_PERMISSIONS = false;

    private LinkedList<String> allFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HAS_PERMISSIONS = checkForPermissions();

        if(!HAS_PERMISSIONS) rerequestPermissions();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener((v) -> {
            startActivity(new Intent(this, UserInputActivity.class));
        });

        /* Handle the extra given by the start intent */
        Intent i = getIntent();
        Bundle extra = i.getExtras();
        if(extra != null) {
            Object accidentReportObj = extra.get(getString(R.string.AccidentReportNewKey));
            if(accidentReportObj instanceof AccidentReport) {
                writeAccidentReport((AccidentReport) accidentReportObj, ((AccidentReport)accidentReportObj).getId() + "");
            }
        }

        /* Get all FileNames in a LinkedList */
        allFiles = new LinkedList<>();
        allFiles.addAll(Arrays.asList(fileList()));

        /* Bind the allFiles list to the ListView */
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allFiles);
        ListView lw = findViewById(R.id.lv_files);
        lw.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        /* Add listener to  */
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String file = allFiles.get(i);
                AccidentReport report = readAccidentReport(file);
                Intent intent = new Intent(view.getContext(), UserInputActivity.class);
                intent.putExtra(getString(R.string.AccidentReportNewKey), report);
                startActivity(intent);
            }
        });
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
        ObjectOutputStream oos = null;
        deleteFile(fileName);
        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
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
        try {
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object read = ois.readObject();
            if(!(read instanceof AccidentReport)) {
                return null;
            }
            ois.close();
            return (AccidentReport) read;
        } catch (Exception e) {
            Log.e(this.getLocalClassName(), e.getLocalizedMessage());
        }
        return null;
    }


}