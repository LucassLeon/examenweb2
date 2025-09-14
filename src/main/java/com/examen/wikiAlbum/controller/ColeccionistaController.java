package com.examen.wikiAlbum.controller;

import com.examen.wikiAlbum.entity.Coleccionista;
import com.examen.wikiAlbum.repository.ColeccionistaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coleccionistas")
public class ColeccionistaController {

    private final ColeccionistaRepository repo;

    public ColeccionistaController(ColeccionistaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Coleccionista> list() { return repo.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coleccionista create(@Valid @RequestBody Coleccionista c) {
        return repo.save(c);
    }
}

