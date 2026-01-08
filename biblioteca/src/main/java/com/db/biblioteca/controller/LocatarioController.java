package com.db.biblioteca.controller;

import com.db.biblioteca.dto.LocatarioRequest;
import com.db.biblioteca.service.LocatarioService;
import org.springframework.web.bind.annotation.*;

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
    public void atualizarLocatario(@PathVariable Long locId, @RequestBody LocatarioRequest request) {
        locatarioService.atualizarLocatario(locId, request.nome(), request.sexo(), request.telefone(), request.email(), request.dataNasc(), request.cpf());
    }

    @GetMapping
    public void listarTodosLocatarios() {
        locatarioService.listarTodosLocatarios();
    }

    @DeleteMapping("/{id}")
    public void removerLocatario(@PathVariable Long locId) {
        locatarioService.removerLocatario(locId);
    }


}
