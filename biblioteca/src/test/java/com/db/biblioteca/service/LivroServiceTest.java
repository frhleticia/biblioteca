package com.db.biblioteca.service;

import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.AutorRepository;
import com.db.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LivroServiceTest {
    private AutorService autorService;
    private LivroService livroService;

    @BeforeEach
    void setup() {
        AutorRepository autorRepository = new AutorRepository();
        LivroRepository livroRepository = new LivroRepository();

        autorService = new AutorService(autorRepository);
        livroService = new LivroService(livroRepository, autorService);
    }

    @Test
    void deveRetornarLivroQuandoExistente() {

        Livro livro = livroService.criarLivro("Odisseia", "12345678901", LocalDate.of(1996, 1, 14));

        livroService.buscarLivro(livro.getId());

        assertNotNull(livro);
    }

    @Test
    void deveConterAutorQuandoVincularAutorAoLivro() {

        Autor autor = autorService.criarAutor("Maria", "F", Year.of(2005), "12345678901");
        Livro livro = livroService.criarLivro("Odisseia", "12345678901", LocalDate.of(1996, 1, 14));

        livroService.vincularAutorAoLivro(autor.getId(), livro.getId());

        List<Livro> listaLivrosDaMaria = livroService.listarLivrosPorAutor(autor.getId());

        assertTrue(listaLivrosDaMaria.contains(livro));
    }

    @Test
    void deveAtualizarLivroQuandoTodosDadosValidos() {

        Livro livro = livroService.criarLivro("Odisseia", "12345678901", LocalDate.of(1996, 1, 14));

        Livro atualizado = livroService.atualizarLivro(livro.getId(), "A Odisseia", "12345678901", LocalDate.of(1996, 1, 14));
        livroService.buscarLivro(atualizado.getId());

        assertEquals("A Odisseia", atualizado.getNome());
    }
}
