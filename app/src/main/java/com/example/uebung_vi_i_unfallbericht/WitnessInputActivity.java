package com.example.uebung_vi_i_unfallbericht;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WitnessInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witness_input);

        Button ok_button = (Button) findViewById(R.id.ok_button);
        Button cancel_button = (Button) findViewById(R.id.cancel_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);

        EditText name = (EditText) findViewById(R.id.firstname_witness);
        EditText lastname = (EditText) findViewById(R.id.lastname_witness);
        EditText street = (EditText) findViewById(R.id.street_witness);
        EditText place = (EditText) findViewById(R.id.place_witness);
        EditText tel = (EditText) findViewById(R.id.tel_witness);


        ok_button.setOnClickListener(view -> {
            if(name.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || street.getText().toString().isEmpty() || place.getText().toString().isEmpty() || tel.getText().toString().isEmpty()) {
                Toast.makeText(this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
            } else {
                Witness witness = new Witness(name.getText().toString(), lastname.getText().toString(), street.getText().toString(), place.getText().toString(), tel.getText().toString());
                Intent i = new Intent(this, UserInputActivity.class);
                i.putExtra(getString(R.string.WitnessNewKey), witness);
                startActivity(i);
            }
        });
    }
}