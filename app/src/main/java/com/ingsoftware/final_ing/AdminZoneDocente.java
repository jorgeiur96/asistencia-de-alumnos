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
public class AdminZoneDocente extends Activity implements View.OnClickListener {
    Biodatadocente biodata = new Biodatadocente();
    TableLayout tabelBiodata;

    Button buttonTambahBiodata;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();

    JSONArray arrayBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maindocente);

        metodohome();


    }
    void metodohome(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tabelBiodata = (TableLayout) findViewById(R.id.tableBiodata);
        buttonTambahBiodata = (Button) findViewById(R.id.buttonTambahBiodata);
        buttonTambahBiodata.setOnClickListener(this);


        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);

        TextView viewHeaderId_docente = new TextView(this);
        TextView viewHeaderNombre = new TextView(this);
        TextView viewHeaderId_contrasena = new TextView(this);
        TextView viewHeaderAction = new TextView(this);

        viewHeaderId_docente.setText("ID");
        viewHeaderNombre.setText("Nombre");
        viewHeaderId_contrasena.setText("contrasena docente");
        viewHeaderAction.setText("       Action");

        viewHeaderId_docente.setPadding(20, 20, 20, 10);
        viewHeaderNombre.setPadding(20, 20, 20, 10);
        viewHeaderId_contrasena.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);

        barisTabel.addView(viewHeaderId_docente);
        barisTabel.addView(viewHeaderNombre);
        barisTabel.addView(viewHeaderId_contrasena);
        barisTabel.addView(viewHeaderAction);

        tabelBiodata.addView(barisTabel, new TableLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewPager.LayoutParams.WRAP_CONTENT));

        try {

            arrayBiodata = new JSONArray(biodata.tampilBiodata());

            for (int i = 0; i < arrayBiodata.length(); i++) {
                JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                String nombre = jsonChildNode.optString("NOMBRE");
                String contrasena = jsonChildNode.optString("CONTRASENA");
                String id_docente = jsonChildNode.optString("ID_DOCENTE");

                System.out.println("Nombre :" + nombre);
                System.out.println("contrasena :" + contrasena);
                System.out.println("ID :" + id_docente);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                TextView viewid_docente = new TextView(this);
                viewid_docente.setText(id_docente);
                viewid_docente.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewid_docente);

                TextView viewNombre = new TextView(this);
                viewNombre.setText(nombre);
                viewNombre.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewNombre);

                TextView viewcontrasena = new TextView(this);
                viewcontrasena.setText(contrasena);
                viewcontrasena.setPadding(5, 1, 5, 1);
                barisTabel.addView(viewcontrasena);

                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id_docente));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Editar");
                buttonEdit.get(i).setOnClickListener(this);
                barisTabel.addView(buttonEdit.get(i));

                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id_docente));
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

        Intent i=new Intent(this,AdminZone.class);
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

    public void deleteBiodata(int id_docente) {
        biodata.deleteBiodata(id_docente);

  /* restart acrtivity */
        finish();
        startActivity(getIntent());

    }

    public void getDataByID(int id_docente) {

        String idalumno = null, alamatEdit = null,contrasena=null;
        JSONArray arrayPersonal;

        try {

            arrayPersonal = new JSONArray(biodata.getBiodataById(id_docente));

            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                 idalumno= jsonChildNode.optString("ID_DOCENTE");
                alamatEdit = jsonChildNode.optString("NOMBRE");
                contrasena =jsonChildNode.optString("CONTRASENA");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        // buat id tersembunyi di alertbuilder
        final TextView viewid_docente = new TextView(this);
        viewid_docente.setText(String.valueOf(id_docente));
        viewid_docente.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewid_docente);

        final EditText cedul = new EditText(this);
        cedul.setText(idalumno);
        cedul.setHint(" ID DOCENTE");
        cedul.setEnabled(false);
        layoutInput.addView(cedul);

        final EditText editcontrasena = new EditText(this);
        editcontrasena.setText(contrasena);

        editcontrasena.setHint("CONTRASEÑA ");
        layoutInput.addView(editcontrasena);

        final EditText editNombre = new EditText(this);
        editNombre.setText(alamatEdit);
        editNombre.setHint("NOMBRE");
        layoutInput.addView(editNombre);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(this);
        builderEditBiodata.setIcon(R.drawable.batagrams);
        builderEditBiodata.setTitle("Actualizar Db");
        builderEditBiodata.setView(layoutInput);

        builderEditBiodata.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id_docente = Integer.parseInt(viewid_docente.getText().toString());
                String nombre = editNombre.getText().toString();
                String id_contrasena = editcontrasena.getText().toString();


                System.out.println("nombre: " + nombre + " contrasena : " + id_contrasena+"id alumno"+id_docente);

                String laporan = biodata.updateBiodata(id_docente,nombre,id_contrasena);

                Toast.makeText(AdminZoneDocente.this, laporan, Toast.LENGTH_SHORT).show();

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
        editNama.setHint("ID  INSERTAR ");
        layoutInput.addView(editNama);

        final EditText editAlamat = new EditText(this);
        editAlamat.setHint("NOMBRE");
        layoutInput.addView(editAlamat);

        final EditText contrasenaacudiente = new EditText(this);
        contrasenaacudiente.setHint("CONTRASEÑA");
        layoutInput.addView(contrasenaacudiente);

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setIcon(R.drawable.batagrams);
        builderInsertBiodata.setTitle("Insertar Db");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Insertar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id_docente = editNama.getText().toString();
                String nombre = editAlamat.getText().toString();
                String contrasena_ACUDIENTE=contrasenaacudiente.getText().toString();

                System.out.println("Nama : " + id_docente + " Alamat : " + nombre);

                String laporan = biodata.inserBiodata(id_docente, nombre,contrasena_ACUDIENTE);

                Toast.makeText(AdminZoneDocente.this, laporan, Toast.LENGTH_SHORT).show();

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
