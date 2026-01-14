package com.db.biblioteca.controller;

import com.db.biblioteca.dto.AutorRequest;
import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.service.AutorService;
import com.db.biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {
    private final AutorService autorService;
    private final LivroService livroService;

    public AutorController(AutorService autorService, LivroService livroService) {
        this.autorService = autorService;
        this.livroService = livroService;
    }

    @PostMapping
    public void criarAutor(@RequestBody AutorRequest request) {
        autorService.criarAutor(request.nome(), request.sexo(), request.anoNasc(), request.cpf());
    }

    @PostMapping("/{autorId}/livros/{livroId}")
    public void atribuirLivro(@PathVariable Long livroId, @PathVariable Long autorId) {
        livroService.vincularAutorAoLivro(autorId, livroId);
    }

    @PutMapping("/{id}")
    public void atualizarAutor(@PathVariable Long id, @RequestBody AutorRequest request) {
        autorService.atualizarAutor(id, request.nome(), request.sexo(), request.anoNasc(), request.cpf());
    }

    @GetMapping
    public List<Autor> listarTodosAutores() {
        return autorService.listarTodosAutores();
    }

    @GetMapping("/{id}/livros")
    public List<Livro> listarLivrosPorAutor(@PathVariable Long id) {
        return autorService.listarLivrosPorAutor(id);
    }

    @DeleteMapping("/{id}")
    public void removerAutor(@PathVariable Long id) {
        autorService.removerAutor(id);
    }
}
