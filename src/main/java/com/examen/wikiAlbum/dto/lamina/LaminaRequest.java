package com.examen.wikiAlbum.dto.lamina;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LaminaRequest {
    @NotNull @Min(1)
    private Integer numero;

    @NotBlank
    private String nombre;

    // opcional: id del tipo
    private Long tipoId;

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getTipoId() { return tipoId; }
    public void setTipoId(Long tipoId) { this.tipoId = tipoId; }
}

