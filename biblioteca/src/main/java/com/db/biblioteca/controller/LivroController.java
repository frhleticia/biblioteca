package com.db.biblioteca.controller;

import com.db.biblioteca.dto.LivroRequest;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.service.AutorService;
import com.db.biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService LivroService;
    private final LivroService livroService;


    public LivroController(AutorService LivroService, LivroService livroService) {
        this.LivroService = livroService;
        this.livroService = livroService;
    }

    @PostMapping
    public void criarLivro(@RequestBody LivroRequest request) {
        LivroService.criarLivro(request);
    }

    @PutMapping("/{id}")
    public void atualizarLivro(@PathVariable Long id, @RequestBody LivroRequest request) {
        LivroService.atualizarLivro(id, request);
    }

    @GetMapping
    public List<Livro> listarTodosLivros() {
        return LivroService.listarTodosLivros();
    }

    @DeleteMapping("/{id}")
    public void removerAutor(@PathVariable Long id) {
        LivroService.removerLivro(id);
    }
}
