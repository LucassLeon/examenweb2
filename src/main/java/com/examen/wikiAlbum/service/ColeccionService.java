package com.examen.wikiAlbum.service;

import com.examen.wikiAlbum.dto.coleccion.FaltanteResponse;
import com.examen.wikiAlbum.dto.coleccion.RepetidaResponse;
import com.examen.wikiAlbum.entity.*;
import com.examen.wikiAlbum.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ColeccionService {

    private final ColeccionRepository coleccionRepo;
    private final ColeccionistaRepository coleccionistaRepo;
    private final AlbumRepository albumRepo;
    private final LaminaRepository laminaRepo;

    public ColeccionService(ColeccionRepository coleccionRepo,
                            ColeccionistaRepository coleccionistaRepo,
                            AlbumRepository albumRepo,
                            LaminaRepository laminaRepo) {
        this.coleccionRepo = coleccionRepo;
        this.coleccionistaRepo = coleccionistaRepo;
        this.albumRepo = albumRepo;
        this.laminaRepo = laminaRepo;
    }

    @Transactional
    public void cargarLaminas(Long coleccionistaId, Long albumId, List<Integer> numeros) {
        Coleccionista coleccionista = coleccionistaRepo.findById(coleccionistaId)
                .orElseThrow(() -> new NoSuchElementException("Coleccionista no encontrado"));
        Album album = albumRepo.findById(albumId)
                .orElseThrow(() -> new NoSuchElementException("Álbum no encontrado"));

        // Pre-cargar laminas del álbum y mapear por numero
        Map<Integer, Lamina> laminaPorNumero = laminaRepo.findByAlbumId(albumId).stream()
                .collect(Collectors.toMap(Lamina::getNumero, l -> l));

        // Pre-cargar colecciones existentes para el par (coleccionista, album)
        Map<Long, Coleccion> coleccionPorLaminaId = coleccionRepo.findByColeccionistaIdAndAlbumId(coleccionistaId, albumId)
                .stream()
                .collect(Collectors.toMap(c -> c.getLamina().getId(), c -> c));

        for (Integer numero : numeros) {
            Lamina lamina = laminaPorNumero.get(numero);
            if (lamina == null) {
                // número no válido para este álbum: se ignora
                continue;
            }
            Coleccion c = coleccionPorLaminaId.get(lamina.getId());
            if (c == null) {
                c = new Coleccion();
                c.setAlbum(album);
                c.setLamina(lamina);
                c.setColeccionista(coleccionista);
                c.setCantidad(0);
                coleccionPorLaminaId.put(lamina.getId(), c);
            }
            c.setCantidad(c.getCantidad() + 1);
            coleccionRepo.save(c);
        }
    }

    @Transactional(readOnly = true)
    public List<FaltanteResponse> faltantes(Long coleccionistaId, Long albumId) {
        // Laminas del album
        List<Lamina> laminasAlbum = laminaRepo.findByAlbumId(albumId);

        // Cantidades del coleccionista para ese album
        Map<Long, Integer> cantidadPorLaminaId = coleccionRepo.findByColeccionistaIdAndAlbumId(coleccionistaId, albumId)
                .stream()
                .collect(Collectors.toMap(c -> c.getLamina().getId(), Coleccion::getCantidad));

        List<FaltanteResponse> res = new ArrayList<>();
        for (Lamina l : laminasAlbum) {
            Integer cant = cantidadPorLaminaId.getOrDefault(l.getId(), 0);
            if (cant == null || cant <= 0) {
                res.add(new FaltanteResponse(l.getNumero(), l.getNombre()));
            }
        }
        // ordenar por numero
        res.sort(Comparator.comparing(FaltanteResponse::getNumero));
        return res;
    }

    @Transactional(readOnly = true)
    public List<RepetidaResponse> repetidas(Long coleccionistaId, Long albumId) {
        // Traer las colecciones con cantidad > 1
        Map<Long, Lamina> laminaPorId = laminaRepo.findByAlbumId(albumId).stream()
                .collect(Collectors.toMap(Lamina::getId, l -> l));

        List<RepetidaResponse> res = new ArrayList<>();
        for (Coleccion c : coleccionRepo.findByColeccionistaIdAndAlbumId(coleccionistaId, albumId)) {
            if (c.getCantidad() != null && c.getCantidad() > 1) {
                Lamina l = laminaPorId.get(c.getLamina().getId());
                if (l != null) {
                    res.add(new RepetidaResponse(l.getNumero(), l.getNombre(), c.getCantidad() - 1));
                }
            }
        }
        // ordenar por numero
        res.sort(Comparator.comparing(RepetidaResponse::getNumero));
        return res;
    }
}

