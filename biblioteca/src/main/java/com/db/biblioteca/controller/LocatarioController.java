package com.db.biblioteca.controller;

import com.db.biblioteca.dto.LocatarioRequest;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.service.LocatarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locatarios")
public class LocatarioController {
    private final LocatarioService locatarioService;

    public LocatarioController(LocatarioService locatarioService) {
        this.locatarioService = locatarioService;
    }

    @PostMapping
    public void criarLocatario(@RequestBody LocatarioRequest request) {
        locatarioService.criarLocatario(request);
    }

    @PutMapping("/{id}")
    public void atualizarLocatario(@PathVariable Long id, @RequestBody LocatarioRequest request) {
        locatarioService.atualizarLocatario(id, request);
    }

    @GetMapping
    public List<Locatario> listarTodosLocatarios() {
        return locatarioService.listarTodosLocatarios();
    }

    @DeleteMapping("/{id}")
    public void removerLocatario(@PathVariable Long id) {
        locatarioService.removerLocatario(id);
    }
}
