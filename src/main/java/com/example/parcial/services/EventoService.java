package com.example.parcial.services;

import com.example.parcial.domain.entity.Evento;
import com.example.parcial.domain.entity.Notificacion;
import com.example.parcial.domain.entity.Usuario;
import com.example.parcial.infra.repository.EventoRepository;
import com.example.parcial.infra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private EmailService emailService;
    public Evento saveEvento(Evento evento, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        evento.setUsuario(usuario);

        // Asignar la fecha actual si no se proporciona
        if (evento.getFecha() == null) {
            evento.setFecha(LocalDate.now());  // Usar LocalDate.now()
        }

        // Asignar la hora actual si no se proporciona
        if (evento.getHora() == null) {
            evento.setHora(LocalTime.now());  // Usar LocalTime.now()
        }

        return eventoRepository.save(evento);
    }

    public List<Evento> getEventosByUsuario(Long usuarioId) {
        return eventoRepository.findByUsuarioId(usuarioId);
    }

    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    public Evento updateEvento(Evento evento, Long id) {
        Evento existente = eventoRepository.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        existente.setTitulo(evento.getTitulo());
        existente.setFecha(evento.getFecha());
        existente.setHora(evento.getHora());
        existente.setDescripcion(evento.getDescripcion());
        return eventoRepository.save(existente);
    }


    public List<String> buscarTiposDeEventos(String query) {
        return eventoRepository.findDistinctTiposDeEventos(query + "%");
    }

    @Scheduled(cron = "0 0 8 * * ?")  // Programado para ejecutarse todos los días a las 8 AM
    public void enviarRecordatoriosDiarios() {
        LocalDate hoy = LocalDate.now();
        List<Evento> eventos = eventoRepository.findEventosEntreFechas(hoy, hoy.plusDays(30));  // Ajusta el rango según lo necesites
        for (Evento evento : eventos) {
            enviarCorreoRecordatorio(evento);
        }
    }

    private void enviarCorreoRecordatorio(Evento evento) {
        String asunto = "Recordatorio del Evento: " + evento.getTitulo();
        String mensaje = "Recuerda que tienes programado el evento '" + evento.getTitulo() +
                "' el " + evento.getFecha().toString() +
                " a las " + evento.getHora().toString() + ".";
        emailService.enviarCorreo(evento.getUsuario().getCorreo(), asunto, mensaje);
    }
    public List<Evento> getEventosPasadosNoCompletados() {
        LocalDate hoy = LocalDate.now();
        return eventoRepository.findEventosPasadosNoCompletados(hoy);
    }

    public void completarEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        evento.setCompletado(true);
        eventoRepository.save(evento);
    }

    public List<Evento> getEventosActivosByUsuario(Long usuarioId) {
        return eventoRepository.findByUsuarioIdAndCompletadoFalse(usuarioId);
    }
    public List<Evento> getAllEventosByUsuario(Long usuarioId) {
        return eventoRepository.findByUsuarioId(usuarioId);
    }

    public List<Evento> getEventosCompletadosByUsuario(Long usuarioId) {
        // Suponiendo que tienes un método en tu repositorio que filtre por usuarioId y completado
        return eventoRepository.findByUsuarioIdAndCompletadoTrue(usuarioId);
    }
    public List<Evento> getEventsForToday(LocalDate date) {
        return eventoRepository.findByFecha(date);
    }





}
