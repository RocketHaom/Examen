package com.example.examen;

public class MainModel {

    String ID, Nombre, Cantidad, imgURL;

    public MainModel() {

    }

    public MainModel(String ID, String nombre, String cantidad, String imgURL) {
        this.ID = ID;
        Nombre = nombre;
        Cantidad = cantidad;
        this.imgURL = imgURL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
