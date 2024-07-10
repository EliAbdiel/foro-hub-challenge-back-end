package com.coderforfun.forohub.controller;

import com.coderforfun.forohub.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopicoDTO datos) {

        var topico = service.registrarTopico(datos);

        return ResponseEntity.ok(new DatosDetalleTopicoDTO(topico));
    } // HECHO

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicoDTO>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable pageable) {

        var page = topicoRepository.findByActivoTrue(pageable).map(DatosListadoTopicoDTO::new);

        return ResponseEntity.ok(page);
    } // HECHO

    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {

        if (topicoRepository.existsById(id) && topicoRepository.findActivoById(id)){
            var topico = topicoRepository.getReferenceById(id);
            return ResponseEntity.ok(new DatosDetalleTopicoDTO(topico));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe el tópico solicitado o fue borrado");
    } // HECHO

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopicoDTO datos) {

        var topico = service.actualizarTopico(id, datos);

        return ResponseEntity.ok(new DatosListadoTopicoDTO(topico));
    } // HECHO

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {

        if (topicoRepository.existsById(id) && topicoRepository.findActivoById(id)) {
            var topico = topicoRepository.getReferenceById(id);
            topico.desactivarTopico();
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el tópico solicitado o fue borrado");
    } // HECHO
}
