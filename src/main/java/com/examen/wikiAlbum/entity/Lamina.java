package com.examen.wikiAlbum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(
  name = "laminas",
  uniqueConstraints = @UniqueConstraint(columnNames = {"album_id","numero"})
)
@JsonIgnoreProperties({"album", "hibernateLazyInitializer", "handler"})
public class Lamina extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column
  private String nombre;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "album_id", nullable = false)
  @NotFound(action = NotFoundAction.IGNORE)
  private Album album;

  @NotNull @Min(1)
  @Column(nullable = false)
  private Integer numero;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_id")
  @NotFound(action = NotFoundAction.IGNORE)
  private TipoLamina tipo;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public Album getAlbum() { return album; }
  public void setAlbum(Album album) { this.album = album; }
  public Integer getNumero() { return numero; }
  public void setNumero(Integer numero) { this.numero = numero; }
  public TipoLamina getTipo() { return tipo; }
  public void setTipo(TipoLamina tipo) { this.tipo = tipo; }
}
