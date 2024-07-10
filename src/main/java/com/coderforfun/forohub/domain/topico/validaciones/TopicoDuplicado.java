package com.coderforfun.forohub.domain.topico.validaciones;

import com.coderforfun.forohub.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidadorDeTopicos{

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validarTopico(String titulo, String mensaje) {
        if (topicoRepository.existsByTituloAndMensaje(titulo, mensaje)){
            throw new ValidationException("Este topico ya existe");
        }
    }
}
