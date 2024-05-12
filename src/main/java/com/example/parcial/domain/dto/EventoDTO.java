package com.example.parcial.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class EventoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora; // Hora del evento para notificaciones
    private String tipoEvento; // Tipo de evento, ej: "Pagar Luz"
    private String nombreUsuario;
    private boolean completado = false;  // Indica si el evento ha sido completado

}
