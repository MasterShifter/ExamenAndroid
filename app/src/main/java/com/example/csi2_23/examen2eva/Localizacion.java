package com.example.csi2_23.examen2eva;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by CSI2-23 on 19/12/2016.
 */

public class Localizacion implements Serializable{
    private Integer identificador;
    private String direccion;
    private Integer tipoLocalizacion;
    private String comentario;
    private Date fechaInsercion;
    private Double latitude;
    private Double longitude;

    public Localizacion() {
    }

    public Localizacion(Integer identificador, String direccion, Integer tipoLocalizacion, String comentario, Date fechaInsercion, Double latitude, Double longitude) {
        this.identificador = identificador;
        this.direccion = direccion;
        this.tipoLocalizacion = tipoLocalizacion;
        this.comentario = comentario;
        this.fechaInsercion = fechaInsercion;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Integer identificador) {
        this.identificador = identificador;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTipoLocalizacion() {
        return tipoLocalizacion;
    }

    public void setTipoLocalizacion(Integer tipoLocalizacion) {
        this.tipoLocalizacion = tipoLocalizacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Localizacion{" +
                "identificador=" + identificador +
                ", direccion='" + direccion + '\'' +
                ", tipoLocalizacion=" + tipoLocalizacion +
                ", comentario='" + comentario + '\'' +
                ", fechaInsercion=" + fechaInsercion +
                ", altitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
