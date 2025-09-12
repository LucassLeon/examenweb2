package com.examen.wikiAlbum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
  name = "colecciones",
  uniqueConstraints = @UniqueConstraint(columnNames = {"album_id","lamina_id","coleccionista_id"})
)
public class Coleccion extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "album_id", nullable = false)
  private Album album;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "lamina_id", nullable = false)
  private Lamina lamina;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "coleccionista_id", nullable = false)
  private Coleccionista coleccionista;

  /** 0 = faltante, 1 = la tiene, >1 = repetidas (repetidas = cantidad - 1) */
  @NotNull @Min(0)
  @Column(nullable = false)
  private Integer cantidad = 0;

  @NotNull
  @Column(nullable = false)
  private Boolean activo = true;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Album getAlbum() { return album; }
  public void setAlbum(Album album) { this.album = album; }
  public Lamina getLamina() { return lamina; }
  public void setLamina(Lamina lamina) { this.lamina = lamina; }
  public Coleccionista getColeccionista() { return coleccionista; }
  public void setColeccionista(Coleccionista coleccionista) { this.coleccionista = coleccionista; }
  public Integer getCantidad() { return cantidad; }
  public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
  public Boolean getActivo() { return activo; }
  public void setActivo(Boolean activo) { this.activo = activo; }
}