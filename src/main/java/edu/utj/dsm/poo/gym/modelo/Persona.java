package edu.utj.dsm.poo.gym.modelo;

import java.util.Date;

public abstract class Persona {
    protected String rfc, nombre;
    protected String primerApellido, segundoApellido;
    protected Date nacimiento;
    protected String rutaImagen;
    
    public Persona() {
        this.rfc = "";
        this.nombre = "";
        this.primerApellido = "";
        this.segundoApellido = "";
        this.nacimiento = new Date();
        this.rutaImagen = "";
    }

    public void setRfc(String rfc) { this.rfc = rfc; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public void setNacimiento(Date nacimiento) { this.nacimiento = nacimiento; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    public String getRfc() { return this.rfc; }
    public String getNombre() { return this.nombre; }
    public String getPrimerApellido() { return this.primerApellido; }
    public String getSegundoApellido() { return this.segundoApellido; }
    public Date getNacimiento() { return this.nacimiento; }
    public String getRutaImagen() { return this.rutaImagen; }
}