package com.examen.wikiAlbum.repository;

import com.examen.wikiAlbum.entity.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColeccionRepository extends JpaRepository<Coleccion, Long> {
    List<Coleccion> findByColeccionistaIdAndAlbumId(Long coleccionistaId, Long albumId);
    Optional<Coleccion> findByColeccionistaIdAndAlbumIdAndLaminaId(Long coleccionistaId, Long albumId, Long laminaId);
}

