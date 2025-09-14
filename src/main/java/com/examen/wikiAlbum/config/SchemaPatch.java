package com.examen.wikiAlbum.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchemaPatch {

    private final JdbcTemplate jdbc;

    public SchemaPatch(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void patch() {
        try {
            jdbc.execute("ALTER TABLE laminas ADD COLUMN nombre TEXT");
        } catch (Exception ignored) {
            // columna ya existe o motor no permite volver a agregarla
        }
        try {
            jdbc.update("UPDATE laminas SET nombre = 'Sin nombre' WHERE nombre IS NULL");
        } catch (Exception ignored) {
        }
    }
}

