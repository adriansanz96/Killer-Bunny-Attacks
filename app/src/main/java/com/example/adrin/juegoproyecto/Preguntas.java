package com.example.adrin.juegoproyecto;

/**
 * Created by Adrián on 02/02/2016.
 */
public class Preguntas {
    private int _id;
    private String pregunta;
    private String res_verdadera;
    private String res_incorrecta;

    // Constructor de un objeto Preguntas
    public Preguntas(int _id, String pregunta, String res_verdadera, String res_incorrecta) {
        this._id = _id;
        this.pregunta = pregunta;
        this.res_verdadera = res_verdadera;
        this.res_incorrecta = res_incorrecta;
    }

    // Recuperar/establecer ID
    public int getID() {
        return _id;
    }
    public void setID(int _id) {
        this._id = _id;
    }

    // Recuperar/establecer PREGUNTA
    public String getPregunta() {
        return pregunta;
    }
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    // Recuperar/establecer Res_Verdadera
    public String getRes_verdadera() {
        return res_verdadera;
    }
    public void setRes_verdadera(String res_verdadera) {
        this.res_verdadera = res_verdadera;
    }
    // Recuperar/establecer Res_Incorrecta
    public String getRes_incorrecta() {
        return res_incorrecta;
    }
    public void getRes_incorrecta(String res_incorrecta) {
        this.res_incorrecta = res_incorrecta;
    }
}
