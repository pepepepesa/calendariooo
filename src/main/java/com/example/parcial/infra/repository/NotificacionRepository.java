package com.example.parcial.infra.repository;

import com.example.parcial.domain.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioIdAndLeido(Long usuarioId, boolean leido);
}