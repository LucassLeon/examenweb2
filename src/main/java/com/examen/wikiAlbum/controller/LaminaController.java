package com.examen.wikiAlbum.controller;

import com.examen.wikiAlbum.entity.Album;
import com.examen.wikiAlbum.entity.Lamina;
import com.examen.wikiAlbum.entity.TipoLamina;
import com.examen.wikiAlbum.dto.lamina.LaminaRequest;
import com.examen.wikiAlbum.dto.lamina.LaminaResponse;
import com.examen.wikiAlbum.repository.AlbumRepository;
import com.examen.wikiAlbum.repository.LaminaRepository;
import com.examen.wikiAlbum.repository.TipoLaminaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/v1/laminas")
public class LaminaController {

    private final LaminaRepository laminaRepo;
    private final AlbumRepository albumRepo;
    private final TipoLaminaRepository tipoRepo;

    public LaminaController(LaminaRepository laminaRepo, AlbumRepository albumRepo, TipoLaminaRepository tipoRepo) {
        this.laminaRepo = laminaRepo;
        this.albumRepo = albumRepo;
        this.tipoRepo = tipoRepo;
    }

    @GetMapping
    public List<LaminaResponse> list() {
        return laminaRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/album/{albumId}")
    public List<LaminaResponse> listByAlbum(@PathVariable Long albumId) {
        return laminaRepo.findByAlbumId(albumId).stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping("/album/{albumId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LaminaResponse create(@PathVariable Long albumId, @Valid @RequestBody LaminaRequest req) {
        Album album = albumRepo.findById(albumId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Álbum no encontrado"));

        if (req.getNumero() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo 'numero' es requerido");
        }
        if (laminaRepo.findByAlbumIdAndNumero(albumId, req.getNumero()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una lámina con ese número en el álbum");
        }

        Lamina lamina = new Lamina();
        lamina.setAlbum(album);
        lamina.setNumero(req.getNumero());
        lamina.setNombre(req.getNombre());
        if (req.getTipoId() != null) {
            TipoLamina tipo = tipoRepo.findById(req.getTipoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de lámina no encontrado"));
            lamina.setTipo(tipo);
        }
        Lamina saved = laminaRepo.save(lamina);
        return toResponse(saved);
    }

    @PutMapping("/{id}")
    public LaminaResponse update(@PathVariable Long id, @Valid @RequestBody LaminaRequest data) {
        Lamina l = laminaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lámina no encontrada"));

        if (data.getNumero() != null && !data.getNumero().equals(l.getNumero())) {
            Long albumId = l.getAlbum() != null ? l.getAlbum().getId() : null;
            if (albumId != null && laminaRepo.findByAlbumIdAndNumero(albumId, data.getNumero()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una lámina con ese número en el álbum");
            }
            l.setNumero(data.getNumero());
        }
        if (data.getNombre() != null) {
            l.setNombre(data.getNombre());
        }
        if (data.getTipoId() != null) {
            TipoLamina tipo = tipoRepo.findById(data.getTipoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de lámina no encontrado"));
            l.setTipo(tipo);
        }
        Lamina saved = laminaRepo.save(l);
        return toResponse(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        laminaRepo.deleteById(id);
    }

    private LaminaResponse toResponse(Lamina l) {
        LaminaResponse r = new LaminaResponse();
        r.setId(l.getId());
        r.setNumero(l.getNumero());
        r.setNombre(l.getNombre());
        if (l.getAlbum() != null) {
            Long id = l.getAlbum().getId();
            String nombre = null;
            try { nombre = l.getAlbum().getNombre(); } catch (EntityNotFoundException ex) { /* relación rota */ }
            r.setAlbum(new com.examen.wikiAlbum.dto.shared.AlbumSummary(id, nombre));
        }
        if (l.getTipo() != null) {
            Long id = l.getTipo().getId();
            String nombre = null;
            try { nombre = l.getTipo().getNombre(); } catch (EntityNotFoundException ex) { /* relación rota */ }
            r.setTipo(new com.examen.wikiAlbum.dto.shared.TipoLaminaSummary(id, nombre));
        }
        r.setCreatedAt(l.getCreatedAt());
        r.setUpdatedAt(l.getUpdatedAt());
        return r;
    }
}
