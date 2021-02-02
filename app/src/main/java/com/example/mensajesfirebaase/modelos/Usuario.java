package com.example.mensajesfirebaase.modelos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    String uid,email,nombre,telefono,provider,photoUrl;
    List<Mensajes> listaRecibidos,listaEnviados;


    public Usuario(String uid, String email, String nombre, String telefono, String provider, String photoUrl, List<Mensajes> listaRecibidos, List<Mensajes> listaEnviados) {
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.provider = provider;
        this.photoUrl = photoUrl;
        this.listaRecibidos = listaRecibidos;
        this.listaEnviados = listaEnviados;
    }

    public Usuario() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String toString() {
        return email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Mensajes> getListaRecibidos() {
        return listaRecibidos;
    }

    public void setListaRecibidos(ArrayList<Mensajes> listaRecibidos) {
        this.listaRecibidos = listaRecibidos;
    }

    public List<Mensajes> getListaEnviados() {
        return listaEnviados;
    }

    public void setListaEnviados(ArrayList<Mensajes> listaEnviados) {
        this.listaEnviados = listaEnviados;
    }
}
