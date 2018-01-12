package com.ingsoftware.final_ing;

public class Biodatadocente extends Koneksi {
    String URL = "http://ingpas.atwebpages.com/jorge/serverdocente.php";
    String url = "";
    String response = "";

    public String tampilBiodata() {
        try {
            url = URL + "?operasi=VER";
            System.out.println("URL Tampil Biodata: " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String inserBiodata(String id_docente, String nombre, String CONTRASENA) {
        try {
            url = URL + "?operasi=insert&id_docente=" + id_docente + "&nombre=" + nombre+"&CONTRASENA="+CONTRASENA;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getBiodataById(int id_docente) {
        try {
            url = URL + "?operasi=get_biodata_by_id&id_docente=" + id_docente;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updateBiodata(int id_docente, String nombre,String contrasena) {
        try {
            url = URL + "?operasi=update&id_docente=" + id_docente + "&nombre=" +nombre +"&CONTRASENA="+contrasena;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String deleteBiodata(int id_docente) {
        try {
            url = URL + "?operasi=delete&id_docente=" + id_docente;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

}