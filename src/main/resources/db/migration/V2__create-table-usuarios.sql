CREATE TABLE usuarios (

    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    correo_electronico VARCHAR(300) NOT NULL UNIQUE,
    contrasena VARCHAR(300) NOT NULL,
    perfiles VARCHAR(100),
    activo TINYINT,

    PRIMARY KEY (id)

);