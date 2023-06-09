package com.example.uebung_vi_i_unfallbericht;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WitnessInputActivity extends AppCompatActivity {
    private final String TAG = "WitnessInputActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witness_input);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        AccidentReport ac = null;
        Witness w = null;
        if(bundle != null) {
            ac = (AccidentReport) bundle.getSerializable(getString(R.string.AccidentReportNewKey));
            w = (Witness) bundle.getSerializable(getString(R.string.WitnessNewKey));
            Log.d(TAG, "onCreate: " + w);
        }

        Button ok_button = (Button) findViewById(R.id.ok_button);
        Button cancel_button = (Button) findViewById(R.id.cancel_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);

        EditText name = (EditText) findViewById(R.id.firstname_witness);
        EditText lastname = (EditText) findViewById(R.id.lastname_witness);
        EditText street = (EditText) findViewById(R.id.street_witness);
        EditText place = (EditText) findViewById(R.id.place_witness);
        EditText tel = (EditText) findViewById(R.id.tel_witness);

        if(w != null) {
            name.setText(w.getFirstname());
            lastname.setText(w.getLastname());
            street.setText(w.getStreet());
            place.setText(w.getCity());
            tel.setText(w.getPhone());
        }

        AccidentReport finalAc = ac;
        Witness finalW1 = w;
        ok_button.setOnClickListener(view -> {
            if(name.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || street.getText().toString().isEmpty() || place.getText().toString().isEmpty() || tel.getText().toString().isEmpty()) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
            } else {
                if(finalW1 != null) {
                    Intent i = new Intent(this, UserInputActivity.class);
                    Witness editWitness = finalAc.getWitness(finalW1.getId());
                    editWitness.setFirstname(name.getText().toString());
                    editWitness.setLastname(lastname.getText().toString());
                    editWitness.setStreet(street.getText().toString());
                    editWitness.setCity(place.getText().toString());
                    editWitness.setPhone(tel.getText().toString());
                    i.putExtra(getString(R.string.AccidentReportNewKey), finalAc);
                    startActivity(i);
                } else {
                    Witness witness = new Witness(name.getText().toString(), lastname.getText().toString(), street.getText().toString(), place.getText().toString(), tel.getText().toString());
                    Intent i = new Intent(this, UserInputActivity.class);

                    finalAc.addWitness(witness);
                    i.putExtra(getString(R.string.AccidentReportNewKey), finalAc);
                    startActivity(i);
                }
            }
        });

        cancel_button.setOnClickListener(view -> {
            Intent i = new Intent(this, UserInputActivity.class);
            i.putExtra(getString(R.string.AccidentReportNewKey), finalAc);
            startActivity(i);
        });

        AccidentReport finalAc1 = ac;
        Witness finalW = w;
        delete_button.setOnClickListener(view -> {
            if(finalW == null) {
                Intent i = new Intent(this, UserInputActivity.class);
                i.putExtra(getString(R.string.AccidentReportNewKey), finalAc1);
                startActivity(i);
            } else {
                finalAc1.removeWitness(finalW);
                Intent i = new Intent(this, UserInputActivity.class);
                i.putExtra(getString(R.string.AccidentReportNewKey), finalAc1);
                startActivity(i);
            }
        });
    }
}