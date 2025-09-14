package com.examen.wikiAlbum.dto.coleccion;

public class RepetidaResponse {
    private Integer numero;
    private String nombre;
    private Integer repetidas; // cantidad - 1

    public RepetidaResponse() {}
    public RepetidaResponse(Integer numero, String nombre, Integer repetidas) {
        this.numero = numero;
        this.nombre = nombre;
        this.repetidas = repetidas;
    }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getRepetidas() { return repetidas; }
    public void setRepetidas(Integer repetidas) { this.repetidas = repetidas; }
}

