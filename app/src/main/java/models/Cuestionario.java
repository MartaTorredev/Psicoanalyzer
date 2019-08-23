package models;

import java.util.ArrayList;

/**
 * Created by enmanuel on 07/07/16.
 */
public class Cuestionario {
    private String id_categoria;
    private ArrayList<String> preguntas_generales;
    private ArrayList<String> preguntas_especificas;
    private ArrayList<Integer> respuestas_generales;
    private ArrayList<Integer> respuestas_especificas;
    private ArrayList<String> enfermedades;

    public ArrayList<String> getAllDescripciones() {
        return allDescripciones;
    }

    public void setAllDescripciones(ArrayList<String> allDescripciones) {
        this.allDescripciones = allDescripciones;
    }

    private ArrayList<String> allDescripciones;

    public ArrayList<String> getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(ArrayList<String> enfermedades) {
        this.enfermedades = enfermedades;
    }

    private int cant_generales;
    private int cant_especificas;

    public int getCant_generales() {
        return cant_generales;
    }

    public ArrayList<Integer> getRespuestas_generales() {
        return respuestas_generales;
    }

    public void setRespuestas_generales(ArrayList<Integer> respuestas_generales) {
        this.respuestas_generales = respuestas_generales;
    }

    public ArrayList<Integer> getRespuestas_especificas() {
        return respuestas_especificas;
    }

    public void setRespuestas_especificas(ArrayList<Integer> respuestas_especificas) {
        this.respuestas_especificas = respuestas_especificas;
    }

    public String getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public ArrayList<String> getPreguntas_generales() {
        return preguntas_generales;
    }

    public void setPreguntas_generales(ArrayList<String> preguntas_generales) {
        this.preguntas_generales = preguntas_generales;
        respuestas_generales=new ArrayList<>();
        int l=this.preguntas_generales.size();
        cant_generales=l;
        for(int i=0;i<l;i++){
            respuestas_generales.add(0);
        }
    }

    public ArrayList<String> getPreguntas_especificas() {
        return preguntas_especificas;
    }

    public int getCant_especificas() {
        return cant_especificas;
    }

    public void setPreguntas_especificas(ArrayList<String> preguntas_especificas) {
        this.preguntas_especificas = preguntas_especificas;
        respuestas_especificas=new ArrayList<>();
        int l=this.preguntas_especificas.size();
        cant_especificas=l;
        for(int i=0;i<l;i++){
            respuestas_especificas.add(0);
        }
    }

}
