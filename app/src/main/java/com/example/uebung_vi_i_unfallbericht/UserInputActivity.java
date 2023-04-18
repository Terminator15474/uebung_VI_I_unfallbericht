package com.example.uebung_vi_i_unfallbericht;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Objects;

public class UserInputActivity extends AppCompatActivity {

    private final String TAG = "UserInputActivity";
    EditText date, time, place, plz, street, nr;
    CheckBox hurt, damage;

    AccidentReport current_report = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        ListView witness_list = findViewById(R.id.witnesses_list);


        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if(extra != null) {
            current_report = (AccidentReport) extra.getSerializable(getString(R.string.AccidentReportNewKey));
        }

        if(current_report != null) {
            ArrayAdapter<Witness> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, current_report.getWitnesses());
            witness_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Log.d(TAG, "if2: " + current_report.getWitnesses());
        }

        if(current_report != null && witness_list.getAdapter() instanceof ArrayAdapter) {
            ((BaseAdapter)witness_list.getAdapter()).notifyDataSetChanged();
        }

        date = findViewById(R.id.date_accident);
        time = findViewById(R.id.editTextTime);
        place = findViewById(R.id.place_accident);
        street = findViewById(R.id.street_accident);
        plz = findViewById(R.id.postalcode_accident);
        nr = findViewById(R.id.number_accident);
        hurt = findViewById(R.id.hurt_accident);
        damage = findViewById(R.id.damage_accident);

        if(extra != null) {
            if(current_report != null) {
                date.setText(((AccidentReport) current_report).getDate());
                time.setText(((AccidentReport) current_report).getTime());
                place.setText(((AccidentReport) current_report).getOrt());
                plz.setText(((AccidentReport) current_report).getPlz()+"");
                nr.setText(((AccidentReport) current_report).getNr()+"");
                hurt.setChecked(((AccidentReport) current_report).isInjured());
                damage.setChecked(((AccidentReport) current_report).isDamage());
            }
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.add_witnesses).setOnClickListener(view -> {
            Intent i = new Intent(this, WitnessInputActivity.class);
            i.putExtra(getString(R.string.AccidentReportNewKey), (current_report == null ? parseAccidentReport() : current_report));
            startActivity(i);
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /* Start the MainActivity intent */
                Intent i = new Intent(this, MainActivity.class);
                Log.d("HOME BUTTON", "ACTIVITY STARTED");
                AccidentReport ar = parseAccidentReport();
                if(ar != null && current_report != null)
                    ar.setId(current_report.getId());
                if(ar != null)
                    i.putExtra(getString(R.string.AccidentReportNewKey), ar);

                startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public AccidentReport parseAccidentReport() {
        System.out.println(date);
        AccidentReport ar;
        try {
            String sDate = date.getText().toString();
            String sTime = time.getText().toString();

            String sPlace = place.getText().toString();
            int sPLZ = Integer.parseInt(plz.getText().toString());
            String sStreet = street.getText().toString();
            int iNr = Integer.parseInt(nr.getText().toString());
            boolean bHurt = hurt.isChecked();
            boolean bDamage = damage.isChecked();

            ar = new AccidentReport(-1,sDate, sTime, sPlace, sPLZ, sStreet, iNr, bHurt, bDamage);
        } catch(Exception e) {
            ar = null;
            Log.e("PARSE ERROR", e.getLocalizedMessage());
        }
        return ar;
    }
}