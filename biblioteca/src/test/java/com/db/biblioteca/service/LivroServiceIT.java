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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LivroServiceIT {
    private LivroService livroService;
    private AutorService autorService;

    @BeforeEach
    void setup() {
        AutorRepository autorRepository = new AutorRepository();
        LivroRepository livroRepository = new LivroRepository();

        autorService = new AutorService(autorRepository);
        livroService = new LivroService(livroRepository, autorService);
    }

    @Test
    void deveSalvarLivroQuandoDadosValidos() {
        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

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

    @Test
    void deveRetornarNuloQuandoLivroInexistente() {
        assertThrows(RuntimeException.class,
                () -> livroService.buscarLivro(999L));
    }

    @Test
    void deveRetornarLivroQuandoExistente() {
        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

        Livro livroBuscado = livroService.buscarLivro(livro.getId());

        assertNotNull(livroBuscado);
    }

    @Test
    void deveVincularAutorELivroQuandoAutorELivroExistentes() {
        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "00998877665", LocalDate.of(2010, 10, 10)));

        Autor autor = autorService.criarAutor(
                new AutorRequest("Maria", "NB", 2005, "12345678901"));

        livroService.vincularAutorAoLivro(autor.getId(), livro.getId());

        List<Livro> listaDeLivros =
                autorService.listarLivrosPorAutor(autor.getId());

        assertTrue(listaDeLivros.contains(livro));
    }

    @Test
    void deveEvitarDuplicacaoQuandoAutorJaContemAqueleLivroVinculado() {
        Autor autor = autorService.criarAutor(
                new AutorRequest("Maria", "NB", 2005, "12345678901"));

        Livro livro1 = livroService.criarLivro(
                new LivroRequest("Odisseia", "00998877665", LocalDate.of(2010, 10, 10)));
        Livro livro2 = livroService.criarLivro(
                new LivroRequest("O Pequeno Príncipe", "77665533881", LocalDate.of(2011, 10, 10)));

        livroService.vincularAutorAoLivro(autor.getId(), livro1.getId());
        livroService.vincularAutorAoLivro(autor.getId(), livro2.getId());
        livroService.vincularAutorAoLivro(autor.getId(), livro1.getId());

        List<Livro> listaDeLivros =
                autorService.listarLivrosPorAutor(autor.getId());

        assertEquals(2, listaDeLivros.size());
    }

    @Test
    void deveRetornarListaDeLivrosQuandoListarTodosLivros() {
        Livro livro1 = livroService.criarLivro(
                new LivroRequest("Odisseia", "00998877665", LocalDate.of(2010, 10, 10)));

        Livro livro2 = livroService.criarLivro(
                new LivroRequest("O Pequeno Príncipe", "12345678901", LocalDate.of(2001, 10, 10)));

        List<Livro> listaDeLivros = livroService.listarTodosLivros();

        assertTrue(listaDeLivros.contains(livro1) && listaDeLivros.contains(livro2));
    }

    @Test
    void deveRetornarListaDeLivrosDisponiveisQuandoListarLivrosDisponiveis() {
        Livro livro1 = livroService.criarLivro(
                new LivroRequest("Odisseia", "00998877665", LocalDate.of(2010, 10, 10)));

        Livro livro2 = livroService.criarLivro(
                new LivroRequest("O Pequeno Príncipe", "12345678901", LocalDate.of(2001, 10, 10)));

        List<Livro> listaDeLivros = livroService.listarLivrosDisponiveis();

        assertTrue(listaDeLivros.contains(livro1) && listaDeLivros.contains(livro2));
    }


    @Test
    void deveLancarExcecaoQuandoRemoverLivroInexistente() {
        assertThrows(RuntimeException.class,
                () -> livroService.removerLivro(999L));
    }

    @Test
    void deveRemoverLivroQuandoRemoverLivroExistente() {
        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

        livroService.removerLivro(livro.getId());

        assertThrows(RuntimeException.class,
                () -> livroService.buscarLivro(livro.getId()));
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest(null, "12345678901", LocalDate.of(1996, 1, 14))));
    }

    @Test
    void deveLancarExcecaoQuandoNomeBlank() {
        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest(" ", "12345678901", LocalDate.of(1996, 1, 14))));
    }

    @Test
    void deveLancarExcecaoQuandoIsbnNulo() {
        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest("Odisseia", null, LocalDate.of(1996, 1, 14))));
    }

    @Test
    void deveLancarExcecaoQuandoIsbnBlank() {
        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest("Odisseia", "", LocalDate.of(1996, 1, 14))));
    }

    @Test
    void deveLancarExcecaoQuandoIsbnRepetido() {
        livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest("O Pequeno Príncipe", "12345678901", LocalDate.of(2001, 1, 14))));
    }

    @Test
    void deveLancarExcecaoQuandoIsbnTamanhoInvalido() {
        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest("Odisseia", "12345", LocalDate.of(1996, 1, 14))));
    }

    @Test
    void deveLancarExcecaoQuandoDataPublicacaoNula() {
        assertThrows(RuntimeException.class,
                () -> livroService.criarLivro(
                        new LivroRequest("Odisseia", "12345678901", null)));
    }
}
