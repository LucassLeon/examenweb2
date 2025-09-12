package com.examen.wikiAlbum.controller;

import com.examen.wikiAlbum.entity.Album;
import com.examen.wikiAlbum.repository.AlbumRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumRepository albumRepo;

    public AlbumController(AlbumRepository albumRepo) {
        this.albumRepo = albumRepo;
    }

    @GetMapping
    public List<Album> list() {
        return albumRepo.findAll();
    }

    @GetMapping("/{id}")
    public Album get(@PathVariable Long id) {
        return albumRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Album create(@Valid @RequestBody Album album) {
        return albumRepo.save(album);
    }

    @PutMapping("/{id}")
    public Album update(@PathVariable Long id, @Valid @RequestBody Album data) {
        Album a = albumRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
        a.setNombre(data.getNombre());
        a.setFechaLanzamiento(data.getFechaLanzamiento());
        a.setFechaSorteo(data.getFechaSorteo());
        a.setCategoria(data.getCategoria());
        a.setTags(data.getTags());
        a.setCantidadLaminas(data.getCantidadLaminas());
        a.setActivo(data.getActivo());
        return albumRepo.save(a);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        albumRepo.deleteById(id);
    }
}
