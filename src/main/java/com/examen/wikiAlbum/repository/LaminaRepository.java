package com.examen.wikiAlbum.repository;

import com.examen.wikiAlbum.entity.Lamina;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LaminaRepository extends JpaRepository<Lamina, Long> {
    // método extra: todas las láminas de un álbum
    List<Lamina> findByAlbumId(Long albumId);
    Optional<Lamina> findByAlbumIdAndNumero(Long albumId, Integer numero);
}
