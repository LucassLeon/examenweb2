package com.examen.wikiAlbum.controller;

import com.examen.wikiAlbum.entity.Album;
import com.examen.wikiAlbum.entity.Lamina;
import com.examen.wikiAlbum.repository.AlbumRepository;
import com.examen.wikiAlbum.repository.LaminaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/laminas")
public class LaminaController {

    private final LaminaRepository laminaRepo;
    private final AlbumRepository albumRepo;

    public LaminaController(LaminaRepository laminaRepo, AlbumRepository albumRepo) {
        this.laminaRepo = laminaRepo;
        this.albumRepo = albumRepo;
    }

    @GetMapping
    public List<Lamina> list() {
        return laminaRepo.findAll();
    }

    @GetMapping("/album/{albumId}")
    public List<Lamina> listByAlbum(@PathVariable Long albumId) {
        return laminaRepo.findByAlbumId(albumId);
    }

    @PostMapping("/album/{albumId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Lamina create(@PathVariable Long albumId, @Valid @RequestBody Lamina lamina) {
        Album album = albumRepo.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Álbum no encontrado"));
        lamina.setAlbum(album);
        return laminaRepo.save(lamina);
    }

    @PutMapping("/{id}")
    public Lamina update(@PathVariable Long id, @Valid @RequestBody Lamina data) {
        Lamina l = laminaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lámina no encontrada"));
        l.setNumero(data.getNumero());
        l.setTipo(data.getTipo());
        return laminaRepo.save(l);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        laminaRepo.deleteById(id);
    }
}
