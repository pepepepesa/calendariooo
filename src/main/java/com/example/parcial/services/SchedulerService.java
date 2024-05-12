package com.example.parcial.services;

import com.example.parcial.domain.entity.Evento;
import com.example.parcial.infra.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkEventsForToday() {
        LocalDate today = LocalDate.now();
        List<Evento> todayEvents = eventoRepository.findByFecha(today);

        for (Evento event : todayEvents) {
            if (!event.getCompletado()) {
                messagingTemplate.convertAndSend("/topic/events", "Recordatorio: El evento " + event.getTitulo() + " está programado para hoy.");
            }
        }
    }
}