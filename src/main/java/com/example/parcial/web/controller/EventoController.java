package com.example.parcial.web.controller;

import com.example.parcial.domain.dto.EventoDTO;
import com.example.parcial.domain.entity.Evento;
import com.example.parcial.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eventos")
@CrossOrigin("*")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<EventoDTO> createEvento(@RequestBody EventoDTO eventoDTO, @PathVariable Long usuarioId) {
        Evento evento = convertToEntity(eventoDTO);
        Evento creado = eventoService.saveEvento(evento, usuarioId);
        EventoDTO creadoDTO = convertToDTO(creado);
        return new ResponseEntity<>(creadoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EventoDTO>> getEventosByUsuario(@PathVariable Long usuarioId) {
        List<Evento> eventos = eventoService.getEventosByUsuario(usuarioId);
        List<EventoDTO> eventoDTOs = eventos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventoDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> updateEvento(@RequestBody EventoDTO eventoDTO, @PathVariable Long id) {
        Evento evento = convertToEntity(eventoDTO);
        Evento actualizado = eventoService.updateEvento(evento, id);
        EventoDTO actualizadoDTO = convertToDTO(actualizado);
        return ResponseEntity.ok(actualizadoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
        return ResponseEntity.ok().build();
    }

    private EventoDTO convertToDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescripcion(evento.getDescripcion());
        dto.setFecha(evento.getFecha());
        dto.setHora(evento.getHora());
        dto.setTipoEvento(evento.getTipoEvento());
        dto.setNombreUsuario(evento.getUsuario().getNombre());
        return dto;
    }

    private Evento convertToEntity(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setId(dto.getId());
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFecha(dto.getFecha());
        evento.setHora(dto.getHora());
        evento.setTipoEvento(dto.getTipoEvento());
        return evento;
    }

    // En tu EventoController
    @GetMapping("/tipos-eventos")
    public ResponseEntity<List<String>> getTiposDeEventos(@RequestParam String query) {
        List<String> tipos = eventoService.buscarTiposDeEventos(query);
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/pasados/no-completados")
    public ResponseEntity<List<EventoDTO>> getEventosPasadosNoCompletados() {
        List<Evento> eventos = eventoService.getEventosPasadosNoCompletados();
        List<EventoDTO> eventoDTOs = eventos.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(eventoDTOs);
    }
    // En tu EventoController.java
    @PostMapping("/completar/{id}")
    public ResponseEntity<Void> completarEvento(@PathVariable Long id) {
        eventoService.completarEvento(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/usuario/{usuarioId}/activos")
    public ResponseEntity<List<EventoDTO>> getEventosActivosByUsuario(@PathVariable Long usuarioId) {
        List<Evento> eventos = eventoService.getEventosActivosByUsuario(usuarioId);
        List<EventoDTO> eventoDTOs = eventos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventoDTOs);
    }
    @GetMapping("/usuario/{usuarioId}/todos")
    public ResponseEntity<List<EventoDTO>> getAllEventosByUsuario(@PathVariable Long usuarioId) {
        List<Evento> eventos = eventoService.getAllEventosByUsuario(usuarioId);
        List<EventoDTO> eventoDTOs = eventos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventoDTOs);
    }

    @GetMapping("/usuario/{usuarioId}/completados")
    public ResponseEntity<List<EventoDTO>> getEventosCompletadosByUsuario(@PathVariable Long usuarioId) {
        List<Evento> eventos = eventoService.getEventosCompletadosByUsuario(usuarioId);
        List<EventoDTO> eventoDTOs = eventos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventoDTOs);
    }



}
