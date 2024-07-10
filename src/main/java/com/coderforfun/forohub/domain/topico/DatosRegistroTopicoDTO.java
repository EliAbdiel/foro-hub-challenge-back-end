package com.coderforfun.forohub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopicoDTO(@NotNull
                                     String titulo,
                                     @NotNull
                                     String mensaje,
                                     @NotNull
                                     Long usuarioId,
                                     @NotNull
                                     String curso) {
}
