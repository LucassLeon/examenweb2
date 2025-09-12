package com.examen.wikiAlbum.repository;

import com.examen.wikiAlbum.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    // aquí puedes agregar métodos personalizados más adelante
}
