package com.db.biblioteca.service;

import com.db.biblioteca.dto.LivroRequest;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.AutorRepository;
import com.db.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LivroServiceTest {
    private LivroService livroService;

    @BeforeEach
    void setup() {
        AutorRepository autorRepository = new AutorRepository();
        LivroRepository livroRepository = new LivroRepository();

        AutorService autorService = new AutorService(autorRepository);
        livroService = new LivroService(livroRepository, autorService);
    }

    @Test
    void deveRetornarLivroQuandoExistente() {
        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

        livroService.buscarLivro(livro.getId());

        assertNotNull(livro);
    }

    @Test
    void deveAtualizarLivroQuandoTodosDadosValidos() {
        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

        Livro atualizacao = livroService.atualizarLivro(livro.getId(),
                new LivroRequest("A Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

        livroService.buscarLivro(atualizacao.getId());

        assertEquals("A Odisseia", atualizacao.getNome());
    }
}
