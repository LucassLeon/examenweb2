package com.examen.wikiAlbum.controller;

import com.examen.wikiAlbum.dto.coleccion.CargaLaminasRequest;
import com.examen.wikiAlbum.dto.coleccion.FaltanteResponse;
import com.examen.wikiAlbum.dto.coleccion.RepetidaResponse;
import com.examen.wikiAlbum.service.ColeccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/coleccionistas/{coleccionistaId}/albums/{albumId}")
public class ColeccionController {

    private final ColeccionService coleccionService;

    public ColeccionController(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    // Carga masiva: enviar lista de números (permitidos duplicados)
    @PostMapping("/laminas/cargar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cargar(@PathVariable Long coleccionistaId,
                       @PathVariable Long albumId,
                       @Valid @RequestBody CargaLaminasRequest body) {
        coleccionService.cargarLaminas(coleccionistaId, albumId, body.getNumeros());
    }

    // Listado de faltantes
    @GetMapping("/faltantes")
    public List<FaltanteResponse> faltantes(@PathVariable Long coleccionistaId,
                                            @PathVariable Long albumId) {
        return coleccionService.faltantes(coleccionistaId, albumId);
    }

    // Listado de repetidas (con cantidad de repetidas)
    @GetMapping("/repetidas")
    public List<RepetidaResponse> repetidas(@PathVariable Long coleccionistaId,
                                            @PathVariable Long albumId) {
        return coleccionService.repetidas(coleccionistaId, albumId);
    }

    // Resumen: faltantes y repetidas en una respuesta
    @GetMapping("/resumen")
    public Map<String, Object> resumen(@PathVariable Long coleccionistaId,
                                       @PathVariable Long albumId) {
        Map<String, Object> map = new HashMap<>();
        map.put("faltantes", coleccionService.faltantes(coleccionistaId, albumId));
        map.put("repetidas", coleccionService.repetidas(coleccionistaId, albumId));
        return map;
    }
}

