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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private final int RQC_STORAGE = 1;

    private LinkedList<String> allFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            FileOutputStream count = openFileOutput("count.txt", MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(count);
            pw.write(fileList().length - 1 + "");
            pw.close();
            count.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!checkForPermissions()) rerequestPermissions();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener((v) -> {
            startActivity(new Intent(this, UserInputActivity.class));
        });

        /* Handle the extra given by the start intent */
        Intent i = getIntent();
        Bundle extra = i.getExtras();
        if(extra != null) {
            Object accidentReportObj = extra.getSerializable(getString(R.string.AccidentReportNewKey));
            if(accidentReportObj instanceof AccidentReport) {
                if(((AccidentReport) accidentReportObj).getId() == -1) {
                    ((AccidentReport) accidentReportObj).setId(advanceCount());
                }
                writeAccidentReport((AccidentReport) accidentReportObj, ((AccidentReport) accidentReportObj).getId() + "");
            }
        }

        /* Get all FileNames in a LinkedList */
        allFiles = new LinkedList<>();
        allFiles.addAll(Arrays.asList(fileList()));
        allFiles.remove(0);

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

    public int advanceCount()  {
        int prev = -1;
        try {
            FileInputStream fis = openFileInput("count.txt");
            Scanner sc = new Scanner(fis);
            prev = Integer.parseInt(sc.nextLine());
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        deleteFile("count.txt");
        FileOutputStream fout = null;
        try {
            fout = openFileOutput("count.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = new PrintWriter(fout);
        pw.print(++prev);
        pw.close();

        return prev;
    }

    public boolean writeAccidentReport(AccidentReport ar, String fileName) {
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


}