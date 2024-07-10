CREATE TABLE topicos (

    id bigint NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(300) NOT NULL,
    fecha_creacion VARCHAR(100) NOT NULL,
    status TINYINT,
    usuario_id bigint NOT NULL,
    curso_id bigint NOT NULL,
    activo TINYINT,

    PRIMARY KEY (id),

    constraint fk_topicos_usuario_id foreign key(usuario_id) references usuarios(id),
    constraint fk_topicos_curso_id foreign key(curso_id) references cursos(id)

);