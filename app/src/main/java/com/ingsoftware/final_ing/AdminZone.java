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
public class AdminZone extends Activity implements View.OnClickListener {
    Biodata biodata = new Biodata();
    TableLayout tabelBiodata;

    Button buttonTambahBiodata;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();

    JSONArray arrayBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        tabelBiodata = (TableLayout) findViewById(R.id.tableBiodata);
        buttonTambahBiodata = (Button) findViewById(R.id.buttonTambahBiodata);
        buttonTambahBiodata.setOnClickListener(this);

        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);

        TextView viewHeaderId_alumno = new TextView(this);
        TextView viewHeaderNombre = new TextView(this);
        TextView viewHeadercedula = new TextView(this);
        TextView viewHeaderAction = new TextView(this);

        viewHeaderId_alumno.setText("ID");
        viewHeaderNombre.setText("Nombre");
        viewHeadercedula.setText("Cedula Acudiente");
        viewHeaderAction.setText("       Action");

        viewHeaderId_alumno.setPadding(20, 20, 20, 10);
        viewHeaderNombre.setPadding(20, 20, 20, 10);
        viewHeadercedula.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);

        barisTabel.addView(viewHeaderId_alumno);
        barisTabel.addView(viewHeaderNombre);
        barisTabel.addView(viewHeadercedula);
        barisTabel.addView(viewHeaderAction);

        tabelBiodata.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewPager.LayoutParams.WRAP_CONTENT));

        try {

            arrayBiodata = new JSONArray(biodata.tampilBiodata());

            for (int i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String nombre = jsonChildNode.optString("NOMBRE");
                String cedula = jsonChildNode.optString("CEDULA_ACUDIENTE");
                String id_alumno = jsonChildNode.optString("ID_ALUMNO");

                System.out.println("Nombre :" + nombre);
                System.out.println("Cedula :" + cedula);
                System.out.println("ID :" + id_alumno);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                TextView viewId_alumno = new TextView(this);
                viewId_alumno.setText(id_alumno);
                viewId_alumno.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewId_alumno);

                TextView viewNombre = new TextView(this);
                viewNombre.setText(nombre);
                viewNombre.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNombre);

                TextView viewcedula = new TextView(this);
                viewcedula.setText(cedula);
                viewcedula.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewcedula);

                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id_alumno));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Editar");
                buttonEdit.get(i).setOnClickListener(this);
                barisTabel.addView(buttonEdit.get(i));

                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id_alumno));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Eliminar");
                buttonDelete.get(i).setOnClickListener(this);
                barisTabel.addView(buttonDelete.get(i));

                tabelBiodata.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,
                        ViewPager.LayoutParams.MATCH_PARENT));
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

        if (view.getId() == R.id.buttonTambahBiodata) {
            // Toast.makeText(MainActivity.this, "Button Tambah Data",
            // Toast.LENGTH_SHORT).show();

            tambahBiodata();

        } else {
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
                else if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                    // Toast.makeText(MainActivity.this, "Delete : " +
                    // buttonDelete.get(i).getId(), Toast.LENGTH_SHORT).show();
                    int id = buttonDelete.get(i).getId();
                    deleteBiodata(id);

                }
            }
        }
    }

    public void deleteBiodata(int id_alumno) {
        biodata.deleteBiodata(id_alumno);

  /* restart acrtivity */
        finish();
        startActivity(getIntent());

    }

    public void getDataByID(int id_alumno) {

        String idalumno = null, alamatEdit = null,cedula=null;
        JSONArray arrayPersonal;

        try {

            arrayPersonal = new JSONArray(biodata.getBiodataById(id_alumno));

            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                 idalumno= jsonChildNode.optString("ID_ALUMNO");
                alamatEdit = jsonChildNode.optString("NOMBRE");
                cedula =jsonChildNode.optString("CEDULA_ACUDIENTE");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // buat id tersembunyi di alertbuilder
        final TextView viewId_alumno = new TextView(this);
        viewId_alumno.setText(String.valueOf(id_alumno));
        viewId_alumno.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId_alumno);

        final EditText cedul = new EditText(this);
        cedul.setText(idalumno);
        cedul.setHint(" id alumno");
        cedul.setEnabled(false);
        layoutInput.addView(cedul);

        final EditText editcedula = new EditText(this);
        editcedula.setText(cedula);

        editcedula.setHint("cedula acudiente");
        layoutInput.addView(editcedula);

        final EditText editNombre = new EditText(this);
        editNombre.setText(alamatEdit);
        editNombre.setHint("Nombre");
        layoutInput.addView(editNombre);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setIcon(R.drawable.batagrams);
        builderEditBiodata.setTitle("Actualizar Db");
        builderEditBiodata.setView(layoutInput);

        builderEditBiodata.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id_alumno = Integer.parseInt(viewId_alumno.getText().toString());
                String nombre = editNombre.getText().toString();
                String id_cedula = editcedula.getText().toString();


                System.out.println("nombre: " + nombre + " cedula : " + id_cedula+"id alumno"+id_alumno);

                String laporan = biodata.updateBiodata(id_alumno,nombre,id_cedula);

                Toast.makeText(AdminZone.this, laporan, Toast.LENGTH_SHORT).show();

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

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setIcon(R.drawable.batagrams);
        builderInsertBiodata.setTitle("Insertar Db");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Insertar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id_alumno = editNama.getText().toString();
                String nombre = editAlamat.getText().toString();
                String CEDULA_ACUDIENTE=cedulaacudiente.getText().toString();

                System.out.println("Nama : " + id_alumno + " Alamat : " + nombre);

                String laporan = biodata.inserBiodata(id_alumno, nombre,CEDULA_ACUDIENTE);

                Toast.makeText(AdminZone.this, laporan, Toast.LENGTH_SHORT).show();

    /* restart acrtivity */
                finish();
                startActivity(getIntent());
            }

        });

        builderInsertBiodata.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderInsertBiodata.show();
    }
    public void cerrar(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
