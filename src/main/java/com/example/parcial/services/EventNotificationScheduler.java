package com.example.parcial.services;

import com.example.parcial.domain.entity.Evento;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EventNotificationScheduler {

    private final EventoService eventoService;

    public EventNotificationScheduler(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @Scheduled(cron = "0 0/30 * * * ?") // Ejecuta cada 30 minutos
    public void checkForEventReminders() {
        LocalDate today = LocalDate.now();
        List<Evento> eventsToday = eventoService.getEventsForToday(today);

        for (Evento event : eventsToday) {
            if (!event.getCompletado()) {
                notifyEvent(event);
            }
        }
    }

    private void notifyEvent(Evento event) {
        // Logica para notificar al usuario, puede ser email, SMS, o WhatsApp
        System.out.println("Recordatorio enviado para el evento: " + event.getTitulo());
    }
}