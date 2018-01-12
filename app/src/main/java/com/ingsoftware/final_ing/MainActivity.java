package com.ingsoftware.final_ing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner escoger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        escoger = (Spinner) findViewById(R.id.sEscoger);

        String[] opciones = {"Administrador", "Docente", "Acudiente"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);

        escoger.setAdapter(adapter);
    }

    public void I(View v) {
        Intent i;
        String selec = escoger.getSelectedItem().toString();
        if (selec.equals("Administrador")) {
            i=new Intent(this, MainActivity2.class);
            startActivity(i);
        }else {
            if (selec.equals("Docente")) {
                i=new Intent(this, MainActivitydocente.class);
                startActivity(i);
            }else {
                    i = new Intent(this, MainActivityacu.class);
                    startActivity(i);

            }

        }
    }

}