package com.db.biblioteca.controller;

import com.db.biblioteca.dto.LocatarioRequest;
import com.db.biblioteca.service.LocatarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locatarios")
public class LocatarioController {
    private final LocatarioService locatarioService;

    public LocatarioController(LocatarioService locatarioService) {
        this.locatarioService = locatarioService;
    }

    @PostMapping
    public void criarLocatario(@RequestBody LocatarioRequest request) {
        locatarioService.criarLocatario(request.nome(), request.sexo(), request.telefone(), request.email(), request.dataNasc(), request.cpf());
    }

    @PutMapping("/{id}")
    public void atualizarLocatario(@PathVariable Long id, @RequestBody LocatarioRequest request) {
        locatarioService.atualizarLocatario(id, request.nome(), request.sexo(), request.telefone(), request.email(), request.dataNasc(), request.cpf());
    }

    @GetMapping
    public void listarTodosLocatarios() {
        locatarioService.listarTodosLocatarios();
    }

    @DeleteMapping("/{id}")
    public void removerLocatario(@PathVariable Long id) {
        locatarioService.removerLocatario(id);
    }
}
