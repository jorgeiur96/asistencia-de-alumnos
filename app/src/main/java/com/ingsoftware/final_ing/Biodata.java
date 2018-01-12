package com.ingsoftware.final_ing;

public class Biodata extends Koneksi {
    String URL = "http://ingpas.atwebpages.com/jorge/servernombre.php";
    String url = "";
    String response = "";

    public String tampilBiodata() {
        try {
            url = URL + "?operasi=view";
            System.out.println("URL Tampil Biodata: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String inserBiodata(String id_alumno, String nombre, String CEDULA_ACUDIENTE) {
        try {
            url = URL + "?operasi=insert&id_alumno=" + id_alumno + "&nombre=" + nombre+"&CEDULA_ACUDIENTE="+CEDULA_ACUDIENTE;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBiodataById(int id_alumno) {
        try {
            url = URL + "?operasi=get_biodata_by_id&id_alumno=" + id_alumno;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updateBiodata(int id_alumno, String nombre,String id_cedula) {
        try {
            url = URL + "?operasi=update&id_alumno=" + id_alumno + "&nombre=" +nombre +"&CEDULA_ACUDIENTE="+id_cedula;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String deleteBiodata(int id_alumno) {
        try {
            url = URL + "?operasi=delete&id_alumno=" + id_alumno;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

}