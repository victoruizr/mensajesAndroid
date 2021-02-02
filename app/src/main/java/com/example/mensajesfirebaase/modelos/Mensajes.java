package com.example.mensajesfirebaase.modelos;

import java.io.Serializable;

public class Mensajes implements Serializable {
    String De;
    String Para;
    String Asunto;
    String Contenido;
    String Fecha;
    String ImagenUsuarioRecibido;
    String ImagenUsuarioEnviado;

    public Mensajes() {
    }

    public Mensajes(String de, String para, String asunto, String contenido, String fecha, String imagenUsuarioRecibido, String imagenUsuarioEnviado) {
        De = de;
        Para = para;
        Asunto = asunto;
        Contenido = contenido;
        Fecha = fecha;
        ImagenUsuarioRecibido = imagenUsuarioRecibido;
        ImagenUsuarioEnviado = imagenUsuarioEnviado;
    }


    public String getDe() {
        return De;
    }

    public void setDe(String de) {
        De = de;
    }

    public String getPara() {
        return Para;
    }

    public void setPara(String para) {
        Para = para;
    }

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String asunto) {
        Asunto = asunto;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String contenido) {
        Contenido = contenido;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getImagenUsuarioRecibido() {
        return ImagenUsuarioRecibido;
    }

    public void setImagenUsuarioRecibido(String imagenUsuarioRecibido) {
        ImagenUsuarioRecibido = imagenUsuarioRecibido;
    }

    public String getImagenUsuarioEnviado() {
        return ImagenUsuarioEnviado;
    }

    public void setImagenUsuarioEnviado(String imagenUsuarioEnviado) {
        ImagenUsuarioEnviado = imagenUsuarioEnviado;
    }
}
