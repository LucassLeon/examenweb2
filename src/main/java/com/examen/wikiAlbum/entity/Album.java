package com.examen.wikiAlbum.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "albums")
@JsonIgnoreProperties({"laminas", "hibernateLazyInitializer", "handler"})
public class Album extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String nombre;

  private LocalDate fechaLanzamiento;
  private LocalDate fechaSorteo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoria_id")
  @NotFound(action = NotFoundAction.IGNORE)
  private Categoria categoria;

  // tags como colección simple (tabla album_tags)
  @ElementCollection
  @CollectionTable(name = "album_tags", joinColumns = @JoinColumn(name = "album_id"))
  @Column(name = "tag", nullable = false)
  private Set<@NotBlank String> tags = new LinkedHashSet<>();

  @Min(0)
  private Integer cantidadLaminas;

  @NotNull
  @Column(nullable = false)
  private Boolean activo = true;

  @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Lamina> laminas = new ArrayList<>();

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
  public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
  public LocalDate getFechaSorteo() { return fechaSorteo; }
  public void setFechaSorteo(LocalDate fechaSorteo) { this.fechaSorteo = fechaSorteo; }
  public Categoria getCategoria() { return categoria; }
  public void setCategoria(Categoria categoria) { this.categoria = categoria; }
  public Set<String> getTags() { return tags; }
  public void setTags(Set<String> tags) { this.tags = tags; }
  public Integer getCantidadLaminas() { return cantidadLaminas; }
  public void setCantidadLaminas(Integer cantidadLaminas) { this.cantidadLaminas = cantidadLaminas; }
  public Boolean getActivo() { return activo; }
  public void setActivo(Boolean activo) { this.activo = activo; }
  public List<Lamina> getLaminas() { return laminas; }
  public void setLaminas(List<Lamina> laminas) { this.laminas = laminas; }
}
