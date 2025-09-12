package com.examen.wikiAlbum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
  name = "laminas",
  uniqueConstraints = @UniqueConstraint(columnNames = {"album_id","numero"})
)
public class Lamina extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "album_id", nullable = false)
  private Album album;

  @NotNull @Min(1)
  @Column(nullable = false)
  private Integer numero;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_id")
  private TipoLamina tipo;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Album getAlbum() { return album; }
  public void setAlbum(Album album) { this.album = album; }
  public Integer getNumero() { return numero; }
  public void setNumero(Integer numero) { this.numero = numero; }
  public TipoLamina getTipo() { return tipo; }
  public void setTipo(TipoLamina tipo) { this.tipo = tipo; }
}
