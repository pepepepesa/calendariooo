package com.example.parcial.web.controller;

import com.example.parcial.domain.entity.Usuario;
import com.example.parcial.infra.repository.UsuarioRepository;
import com.example.parcial.infra.security.LoginRequest;
import com.example.parcial.infra.security.TokenResponse;
import com.example.parcial.services.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*") // Permite solicitudes de cualquier origen
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;
    @Autowired
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse token = usuarioService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<TokenResponse> registrar(@RequestBody Usuario usuario) {
        TokenResponse token = usuarioService.addUsuario(usuario);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}