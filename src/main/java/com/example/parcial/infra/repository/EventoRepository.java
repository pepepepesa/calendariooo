package com.example.parcial.infra.repository;

import com.example.parcial.domain.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByUsuarioId(Long usuarioId);
    List<Evento> findByFecha(LocalDate fecha);
    List<Evento> findByUsuarioIdAndCompletadoFalse(Long usuarioId);

    List<Evento> findByUsuarioIdAndCompletadoTrue(Long usuarioId);
    @Query("SELECT DISTINCT e.tipoEvento FROM Evento e WHERE e.tipoEvento LIKE ?1")
    List<String> findDistinctTiposDeEventos(String tipoEventoPattern);
    @Query("SELECT e FROM Evento e WHERE e.fecha >= :start AND e.fecha <= :end AND e.usuario.id = :usuarioId")
    List<Evento> findEventosEntreFechas(LocalDate start, LocalDate end, Long usuarioId);
    @Query("SELECT e FROM Evento e WHERE e.fecha >= :start AND e.fecha <= :end AND e.completado = false")
    List<Evento> findEventosEntreFechas(LocalDate start, LocalDate end);

    @Query("SELECT e FROM Evento e WHERE e.fecha < :now AND e.completado = false")
    List<Evento> findEventosPasadosNoCompletados(@Param("now") LocalDate now);

}
