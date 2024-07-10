package com.coderforfun.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopicoDTO(Long id,
                                    String titulo,
                                    String mensaje,
                                    LocalDateTime fechaCreacion) {

    public DatosListadoTopicoDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion());
    }
}
