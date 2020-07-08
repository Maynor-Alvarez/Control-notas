/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pedroarmas.bean;

/**
 *
 * @author Maynor E. Alvarez
 */
public class Grado {
    private int codigoGrado; 
    private String grado;
    private String seccion;
    private String modalidad;
    private String jornada;

    public Grado() {
    }

    public Grado(int codigoGrado, String grado, String seccion, String modalidad, String jornada) {
        this.codigoGrado = codigoGrado;
        this.grado = grado;
        this.seccion = seccion;
        this.modalidad = modalidad;
        this.jornada = jornada;
    }

    public int getCodigoGrado() {
        return codigoGrado;
    }

    public void setCodigoGrado(int codigoGrado) {
        this.codigoGrado = codigoGrado;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }
    
    
}
