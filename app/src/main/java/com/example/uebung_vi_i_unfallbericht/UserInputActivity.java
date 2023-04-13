package com.example.uebung_vi_i_unfallbericht;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

public class UserInputActivity extends AppCompatActivity {

    EditText date, time, place, plz, street, nr;
    CheckBox hurt, damage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);


        date = findViewById(R.id.date_accident);
        time = findViewById(R.id.editTextTime);
        place = findViewById(R.id.place_accident);
        street = findViewById(R.id.street_accident);
        plz = findViewById(R.id.postalcode_accident);
        nr = findViewById(R.id.number_accident);
        hurt = findViewById(R.id.hurt_accident);
        damage = findViewById(R.id.damage_accident);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /* Start the MainActivity intent */
                Intent i = new Intent(this, MainActivity.class);
                AccidentReport ar = parseAccidentReport();
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

            ar = new AccidentReport(sDate, sTime, sPlace, sPLZ, sStreet, iNr, bHurt, bDamage);
        } catch(Exception e) {
            ar = null;
            Log.e(this.getLocalClassName(), e.getLocalizedMessage());
        }
        return ar;
    }
}