package com.db.biblioteca.service.unit;

import com.db.biblioteca.dto.LivroRequest;
import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.LivroRepository;
import com.db.biblioteca.service.AutorService;
import com.db.biblioteca.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivroServiceUnitTest {
    private LivroService livroService;
    private LivroRepository livroRepository;
    private AutorService autorService;

    @BeforeEach
    void setup() {
        livroRepository = mock(LivroRepository.class);
        autorService = mock(AutorService.class);
        livroService = new LivroService(livroRepository, autorService);
    }

    @Test
    void deveCriarLivroQuandoDadosValidos() {
        when(livroRepository.getLivros()).thenReturn(List.of());

        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.now()));

        assertNotNull(livro);
    }

    @Test
    void deveLancarExcecaoQuandoIsbnDuplicado() {
        Livro existente = new Livro("Odisseia", "12345678901", LocalDate.of(1996, 1, 14), new ArrayList<>());

        when(livroRepository.getLivros()).thenReturn(List.of(existente));

        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest("Outro", "1234567890123", LocalDate.now())
                ));
    }

    @Test
    void deveBuscarLivroQuandoExiste() {
        Livro livro = new Livro("Odisseia", "12345678901", LocalDate.of(1996, 1, 14), new ArrayList<>());
        livro.setId(1L);

        when(livroRepository.buscarPorId(1L))
                .thenReturn(livro);

        Livro resultado = livroService.buscarLivro(1L);

        assertEquals("Odisseia", resultado.getNome());
        assertEquals("12345678901", resultado.getIsbn());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarLivroInexistente() {
        when(livroRepository.buscarPorId(1L))
                .thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> livroService.buscarLivro(1L));
    }

    @Test
    void deveRemoverLivroQuandoPossuiAutores() {
        Livro livro = new Livro("Odisseia", "12345678901", LocalDate.of(1996, 1, 14), new ArrayList<>());
        livro.setAutores(List.of());

        when(livroRepository.buscarPorId(1L))
                .thenReturn(livro);

        livroService.removerLivro(1L);

        verify(livroRepository).remover(livro);
    }

    @Test
    void deveLancarExcecaoQuandoRemoverLivroComAutores() {
        Livro livro = new Livro("Odisseia", "12345678901", LocalDate.of(1996, 1, 14), new ArrayList<>());
        livro.setAutores(List.of(new Autor("Maria", "NB", Year.of(2005), "12345678901")));

        when(livroRepository.buscarPorId(1L))
                .thenReturn(livro);

        assertThrows(RuntimeException.class,
                () -> livroService.removerLivro(1L));
    }
}