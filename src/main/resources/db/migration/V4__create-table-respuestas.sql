CREATE TABLE respuestas (

    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(300) NOT NULL,
    topico_id bigint NOT NULL,
    fecha_creacion VARCHAR(100) NOT NULL,
    usuario_id bigint NOT NULL,
    solucion TINYINT,

    PRIMARY KEY (id),

    constraint fk_respuestas_topico_id foreign key(topico_id) references topicos(id),
    constraint fk_respuestas_usuario_id foreign key(usuario_id) references usuarios(id)

);