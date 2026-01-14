package com.db.biblioteca.service;

import com.db.biblioteca.dto.AutorRequest;
import com.db.biblioteca.dto.LivroRequest;
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

public class AutorServiceTest {

    private AutorService autorService;
    private LivroService livroService;

    @BeforeEach
    void setup() {
        AutorRepository autorRepository = new AutorRepository();
        LivroRepository livroRepository = new LivroRepository();

        autorService = new AutorService(autorRepository);
        livroService = new LivroService(livroRepository, autorService);
    }

    // Criar autor
    @Test
    void deveSalvarAutorQuandoTodosDadosValidos() {

        AutorRequest request = new AutorRequest(
                "Maria", "NB", Year.of(2005), "12345678901"
        );

        autorService.criarAutor(request);

        Autor a = autorService.buscarAutor(1L);

        assertNotNull(a);
    }

    // Atualizar autor
    @Test
    void deveLancarExcecaoQuandoCpfRepetidoQuandoAtualizarAutor() {

        autorService.criarAutor(
                new AutorRequest("Maria", "NB", Year.of(2005), "11188833399")
        );

        autorService.criarAutor(
                new AutorRequest("Joana", "M", Year.of(2005), "12345678901")
        );

        AutorRequest requestAtualizacao = new AutorRequest(
                "Maria", "NB", Year.of(2005), "12345678901"
        );

        assertThrows(RuntimeException.class,
                () -> autorService.atualizarAutor(1L, requestAtualizacao));
    }

    @Test
    void deveAtualizarAutorQuandoTodosDadosValidos() {

        autorService.criarAutor(
                new AutorRequest("Maria", "NB", Year.of(2005), "12345678901")
        );

        autorService.criarAutor(
                new AutorRequest("Joana", "M", Year.of(2002), "11122233344")
        );

        AutorRequest requestAtualizacao = new AutorRequest(
                "Maria", "NB", Year.of(2005), "10987654321"
        );

        autorService.atualizarAutor(1L, requestAtualizacao);

        Autor atualizado = autorService.buscarAutor(1L);

        assertEquals("10987654321", atualizado.getCpf());
    }

    @Test
    void deveConterAutorQuandoVincularAutorAoLivro() {
        AutorRequest autorRequest = new AutorRequest(
                "Maria", "F", Year.of(2005), "12345678901"
        );

        LivroRequest livroRequest = new LivroRequest(
                "Odisseia", "12345678901", LocalDate.of(1996, 1, 14)
        );

        Autor autor = autorService.criarAutor(autorRequest);
        Livro livro = livroService.criarLivro(livroRequest);

        livroService.vincularAutorAoLivro(autor.getId(), livro.getId());

        List<Livro> listaLivrosDaMaria =
                autorService.listarLivrosPorAutor(autor.getId());

        assertTrue(listaLivrosDaMaria.contains(livro));
    }

    // Buscar autor
    @Test
    void deveRetornarNuloQuandoAutorInexistente() {
        assertThrows(RuntimeException.class,
                () -> autorService.buscarAutor(999L));
    }

    @Test
    void deveRetornarAutorQuandoExistente() {

        autorService.criarAutor(
                new AutorRequest("Maria", "NB", Year.of(2005), "12345678901")
        );

        Autor a = autorService.buscarAutor(1L);

        assertNotNull(a);
    }

    @Test
    void deveLancarExcecaoQuandoListarLivrosDeAutorInexistente() {
        assertThrows(RuntimeException.class,
                () -> autorService.listarLivrosPorAutor(999L));
    }

    @Test
    void deveRetornarListaDeLivrosQuandoPessoaExistente() {

        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "00998877665",
                        LocalDate.of(2010, 10, 10))
        );

        Autor autor = autorService.criarAutor(
                new AutorRequest("Maria", "NB", Year.of(2005), "12345678901")
        );

        livroService.vincularAutorAoLivro(autor.getId(), livro.getId());

        List<Livro> listaDeLivros =
                autorService.listarLivrosPorAutor(autor.getId());

        assertTrue(listaDeLivros.contains(livro));
    }

    // Remover autor
    @Test
    void deveLancarExcecaoQuandoRemoverAutorInexistente() {
        assertThrows(RuntimeException.class,
                () -> autorService.removerAutor(999L));
    }

    @Test
    void deveRemoverAutorQuandoAutorExistenteForRemovida() {

        autorService.criarAutor(
                new AutorRequest("Maria", "NB", Year.of(2005), "12345678901")
        );

        autorService.removerAutor(1L);

        assertThrows(RuntimeException.class,
                () -> autorService.buscarAutor(1L));
    }

    @Test
    void deveLancarExcecaoQuandoTentaRemoverComAListaVazia() {
        assertThrows(RuntimeException.class,
                () -> autorService.removerAutor(1L));
    }

    // Exceções
    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest(null, "NB", Year.of(2005), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoNomeBlank() {
        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("", "NB", Year.of(2005), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoSexoInvalido() {
        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Maria", "Feminino",
                                Year.of(2005), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoDataNascNulo() {
        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Maria", "NB", null, "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNulo() {
        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Maria", "NB", Year.of(2005), null)));
    }

    @Test
    void deveLancarExcecaoQuandoCpfBlank() {
        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Maria", "NB", Year.of(2005), " ")));
    }

    @Test
    void deveLancarExcecaoQuandoCpfRepetido() {

        autorService.criarAutor(
                new AutorRequest("Maria", "NB", Year.of(2005), "12345678901")
        );

        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Joana", "F", Year.of(2005), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoCpfTamanhoInvalido() {
        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Maria", "NB", Year.of(2005), "12345")));
    }
}
