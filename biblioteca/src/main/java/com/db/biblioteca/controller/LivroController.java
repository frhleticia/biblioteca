package com.db.biblioteca.controller;

import com.db.biblioteca.dto.LivroRequest;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public void criarLivro(@RequestBody LivroRequest request) {
        livroService.criarLivro(request);
    }

    @PutMapping("/{id}")
    public void atualizarLivro(@PathVariable Long id, @RequestBody LivroRequest request) {
        livroService.atualizarLivro(id, request);
    }

    @GetMapping("/disponiveis")
    public List<Livro> listarLivrosDisponiveis() {
        return livroService.listarLivrosDisponiveis();
    }

    @GetMapping
    public List<Livro> listarTodosLivros() {
        return livroService.listarTodosLivros();
    }

    @DeleteMapping("/{id}")
    public void removerAutor(@PathVariable Long id) {
        livroService.removerLivro(id);
    }

}
