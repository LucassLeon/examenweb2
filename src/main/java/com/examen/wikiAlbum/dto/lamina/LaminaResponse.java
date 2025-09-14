package com.examen.wikiAlbum.dto.lamina;

import java.time.Instant;
import com.examen.wikiAlbum.dto.shared.AlbumSummary;
import com.examen.wikiAlbum.dto.shared.TipoLaminaSummary;

public class LaminaResponse {
    private Long id;
    private Integer numero;
    private String nombre;
    private AlbumSummary album;
    private TipoLaminaSummary tipo;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public AlbumSummary getAlbum() { return album; }
    public void setAlbum(AlbumSummary album) { this.album = album; }
    public TipoLaminaSummary getTipo() { return tipo; }
    public void setTipo(TipoLaminaSummary tipo) { this.tipo = tipo; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
