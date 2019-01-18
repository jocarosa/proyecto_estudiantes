package com.example.jrosario.studientdb.studentThings;

import java.io.Serializable;

public class Estudiante implements Serializable {


    public static final String NOMBRES="Nombres";
    public static final String APELLIDOS="Apellidos";
    public static final String MATERIA ="Materia";
    public static final String CALIFICACION="Calificacion";

    private long _id;
    private String Nombres;
    private String Apellidos;
    private String Materia;
    private int Calificacion;
    private boolean favorite;



    public Estudiante(String nombres, String apellidos, String materia, int calificacion) {

        Nombres = nombres;
        Apellidos = apellidos;
        Materia = materia;
        Calificacion = calificacion;
        this.favorite = false;

    }


    public long getId() {
        return this._id;
    }
    public void setId(long id){
        this._id = id;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getMateria() {
        return Materia;
    }

    public void setMateria(String materia) {
        Materia = materia;
    }

    public int getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(int calificacion) {
        Calificacion = calificacion;
    }

    public void setFavorite(boolean isFavorite){
            this.favorite = isFavorite;
    }

    public boolean getFavorite(){
        return this.favorite;
    }
}
