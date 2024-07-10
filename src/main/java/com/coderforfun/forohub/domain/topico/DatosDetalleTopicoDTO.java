package com.coderforfun.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopicoDTO(Long id,
                                    String titulo,
                                    String mensaje,
                                    LocalDateTime fechaCreacion,
                                    String status,
                                    String autor,
                                    String curso) {

    public DatosDetalleTopicoDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}
