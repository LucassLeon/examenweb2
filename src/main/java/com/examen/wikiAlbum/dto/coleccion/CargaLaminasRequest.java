package com.examen.wikiAlbum.dto.coleccion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CargaLaminasRequest {
    @NotNull
    @NotEmpty
    private List<Integer> numeros;

    public List<Integer> getNumeros() { return numeros; }
    public void setNumeros(List<Integer> numeros) { this.numeros = numeros; }
}

