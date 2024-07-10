package com.coderforfun.forohub.domain.topico;

import com.coderforfun.forohub.domain.curso.Curso;
import com.coderforfun.forohub.domain.curso.CursoRepository;
import com.coderforfun.forohub.domain.topico.validaciones.ValidadorDeTopicos;
import com.coderforfun.forohub.domain.usuario.Usuario;
import com.coderforfun.forohub.domain.usuario.UsuarioRepository;
import com.coderforfun.forohub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidadorDeTopicos> validaciones;

    public Topico registrarTopico(DatosRegistroTopicoDTO datos) {

        if (!usuarioRepository.findById(datos.usuarioId()).isPresent()) {
            throw new ValidacionDeIntegridad("Este id para el usuario no fue encontrado");
        }

        if (!cursoRepository.existsByNombre(datos.curso())){
            throw new ValidacionDeIntegridad("Este curso no fue encontrado");
        }

        validaciones.forEach(v -> v.validarTopico(datos.titulo(), datos.mensaje()));

        Curso curso = cursoRepository.findByNombre(datos.curso());

        Usuario usuario = usuarioRepository.getReferenceById(datos.usuarioId());

        Topico topico = new Topico(
                datos.titulo(),
                datos.mensaje(),
                usuario,
                curso
        );

        topicoRepository.save(topico);

        return topico;
    } // HECHO

    public Topico actualizarTopico(Long id, DatosActualizarTopicoDTO datos) {

        var topico = topicoRepository.getReferenceById(id);

        if (!cursoRepository.existsByNombre(datos.curso())){
            throw new ValidacionDeIntegridad("Este curso no fue encontrado");
        }

        validaciones.forEach(v -> v.validarTopico(datos.titulo(), datos.mensaje()));

        Curso curso = cursoRepository.findByNombre(datos.curso());

        topico.actualizarTopico(datos.titulo(),
                datos.mensaje(),
                datos.status(),
                curso);

        return topico;
    } // HECHO

}
