package com.ingsoftware.final_ing;

public class BiodataAlumno2 extends Koneksi {
    String URL = "http://ingpas.atwebpages.com/jorge/servernotapadre.php";
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



    public String getBiodataById(int id_notificaciones) {
        try {
            url = URL + "?operasi=get_biodata_by_id&id_notificaciones=" + id_notificaciones;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String updateBiodata(int id_notificaciones, String nota,String ASISTENCIA) {
        try {
            url = URL + "?operasi=update&id_notificaciones=" + id_notificaciones + "&nota=" +nota +"&ASISTENCIA="+ASISTENCIA;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }



}