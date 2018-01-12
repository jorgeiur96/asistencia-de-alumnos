package com.ingsoftware.final_ing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jorge on 03/12/16.
 */
public class ZonaNotaAcudiente extends Activity implements View.OnClickListener {
    BiodataAlumno2 biodata = new BiodataAlumno2();
    TableLayout tabelBiodata;

   static  String a;

    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();

    JSONArray arrayBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notaacudiente);if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        tabelBiodata = (TableLayout) findViewById(R.id.tableBiodata);


        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);

        TextView viewHeaderId_alumno = new TextView(this);
        TextView viewHeaderNombre = new TextView(this);
        TextView viewHeadercedula = new TextView(this);
        //TextView viewHeaderAction = new TextView(this);

        viewHeaderId_alumno.setText("ID ESTUDIANTE");
        viewHeaderNombre.setText("NOTA");
        viewHeadercedula.setText("ASISTENCIA");
        //viewHeaderAction.setText("       Action");

        viewHeaderId_alumno.setPadding(45, 10, 45, 10);
        viewHeaderNombre.setPadding(95, 10, 95, 10);
        viewHeadercedula.setPadding(70, 10, 70, 1);
        //viewHeaderAction.setPadding(5, 1, 5, 1);

        barisTabel.addView(viewHeaderId_alumno);
        barisTabel.addView(viewHeaderNombre);
        barisTabel.addView(viewHeadercedula);
        //barisTabel.addView(viewHeaderAction);

        tabelBiodata.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewPager.LayoutParams.WRAP_CONTENT));

        try {

            arrayBiodata = new JSONArray(biodata.tampilBiodata());

            for (int i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String nota = jsonChildNode.optString("NOTA");
                String asistencia = jsonChildNode.optString("ASISTENCIA");
                String id_notificaciones = jsonChildNode.optString("ID_NOTIFICACIONES");

                System.out.println("Nombre :" + nota);
                System.out.println("Cedula :" + asistencia);
                System.out.println("ID :" + id_notificaciones);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                TextView viewId_alumno = new TextView(this);
                viewId_alumno.setText(id_notificaciones);
                viewId_alumno.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewId_alumno);

                TextView viewNombre = new TextView(this);
                viewNombre.setText(nota);
                viewNombre.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNombre);

                TextView viewcedula = new TextView(this);
                viewcedula.setText(asistencia);
                viewcedula.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewcedula);

                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id_notificaciones));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("  Notificaciones  ");
                buttonEdit.get(i).setOnClickListener(this);
                //barisTabel.addView(buttonEdit.get(i));

                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id_notificaciones));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Eliminar");
                buttonDelete.get(i).setOnClickListener(this);
               // barisTabel.addView(buttonDelete.get(i));

                if(i==0){
                    a=id_notificaciones;
                }
                if(id_notificaciones.equals(a)){
                    tabelBiodata.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,
                            ViewPager.LayoutParams.MATCH_PARENT));

                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  void p2(View v){

        Intent i=new Intent(this,AdminZoneDocente.class);
        startActivity(i);

    }

    public void onClick(View view) {


   /*
    * Melakukan pengecekan pada data array, agar sesuai dengan index
    * masing-masing button
    */
            for (int i = 0; i < buttonEdit.size(); i++) {

    /* jika yang diklik adalah button edit */
                if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                    // Toast.makeText(MainActivity.this, "Edit : " +
                    // buttonEdit.get(i).getId(), Toast.LENGTH_SHORT).show();
                    int id = buttonEdit.get(i).getId();
                    getDataByID(id);

                } /* jika yang diklik adalah button delete */


        }
    }



    public void getDataByID(int id_notificaciones) {

        String idalumno = null, alamatEdit = null,cedula=null;
        JSONArray arrayPersonal;

        try {

            arrayPersonal = new JSONArray(biodata.getBiodataById(id_notificaciones));

            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                 idalumno= jsonChildNode.optString("ID_NOTIFICACIONES");
                alamatEdit = jsonChildNode.optString("NOTA");
                cedula =jsonChildNode.optString("ASISTENCIA");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // buat id tersembunyi di alertbuilder
        final TextView viewId_alumno = new TextView(this);
        viewId_alumno.setText(String.valueOf(id_notificaciones));
        viewId_alumno.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId_alumno);

        final EditText cedul = new EditText(this);
        cedul.setText(idalumno);
        cedul.setHint(" id notificacion");
        cedul.setEnabled(false);
        layoutInput.addView(cedul);

        final EditText editcedula = new EditText(this);
        editcedula.setText(cedula);

        editcedula.setHint("notificacion");
        layoutInput.addView(editcedula);

        final EditText editNombre = new EditText(this);
        editNombre.setText(alamatEdit);
        editNombre.setHint("nota");
        layoutInput.addView(editNombre);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setIcon(R.drawable.batagrams);
        builderEditBiodata.setTitle("MANDAR  NOTIFICACION");
        builderEditBiodata.setView(layoutInput);

        builderEditBiodata.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id_alumno = Integer.parseInt(viewId_alumno.getText().toString());
                String nombre = editNombre.getText().toString();
                String id_cedula = editcedula.getText().toString();


                System.out.println("nombre: " + nombre + " cedula : " + id_cedula+"id alumno"+id_alumno);

                String laporan = biodata.updateBiodata(id_alumno,nombre,id_cedula);

                Toast.makeText(ZonaNotaAcudiente.this, laporan, Toast.LENGTH_SHORT).show();

    /* restart acrtivity */
                finish();
                startActivity(getIntent());
            }

        });

        builderEditBiodata.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderEditBiodata.show();

    }

    public void tambahBiodata() {
  /* layout akan ditampilkan pada AlertDialog */
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editNama = new EditText(this);
        editNama.setHint("ID  ALUMNO");
        layoutInput.addView(editNama);

        final EditText editAlamat = new EditText(this);
        editAlamat.setHint("NOMBRE");
        layoutInput.addView(editAlamat);

        final EditText cedulaacudiente = new EditText(this);
        cedulaacudiente.setHint("CEDULA ACUDIENTE");
        layoutInput.addView(cedulaacudiente);


    }
    public void cerrar(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
