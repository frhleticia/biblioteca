package com.db.biblioteca.service;

import com.db.biblioteca.model.Autor;
import com.db.biblioteca.repository.AutorRepository;
import com.db.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AutorServiceTest {
    private AutorService autorService;
    private LivroService livroService;

    @BeforeEach
    void setup() {
        AutorRepository autorRepository = new AutorRepository();
        LivroRepository livroRepository = new LivroRepository();
        autorService = new AutorService(autorRepository, livroService);
        livroService = new LivroService(livroRepository, autorService);
    }

    //Criar usuário
    @Test
    void deveSalvarUsuarioQuandoTodosDadosValidos() {

        autorService.criarAutor("Maria", "NB", Year.of(2005), "12345678901");

        Autor p = autorService.buscarAutor(1L);

        assertNotNull(p);
    }

    //Atualizar pessoa
    @Test
    void deveLancarExcecaoQuandoCpfRepetidoQuandoAtualizarAutor() {

        autorService.criarAutor("Maria", "NB", Year.of(2005), "12345678901");

        autorService.criarAutor("Joana", "M", Year.of(2005), "joao@email.com");

        assertThrows(RuntimeException.class, () -> autorService.atualizarAutor(1L, "Maria", "NB", Year.of(2005), "12345678901"));
    }

    @Test
    void deveAtualizarAutorQuandoTodosDadosValidos() {

        autorService.criarAutor("Maria", "NB", Year.of(2005), "12345678901");

        autorService.criarAutor("Joana", "M", Year.of(2002), "11122233344");

        autorService.atualizarAutor(1L, "Maria", "NB", Year.of(2005), "10987654321");
        Autor atualizado = autorService.buscarAutor(1L);

        assertEquals("10987654321", atualizado.getCpf());
    }

    //Buscar pessoa por id
    @Test
    void deveRetornarNuloQuandoAutorInexistente() {

        assertThrows(RuntimeException.class, () -> autorService.buscarAutor(999L));
    }

    @Test
    void deveRetornarAutorQuandoExistente() {

        autorService.criarAutor("Maria", "NB", Year.of(2005), "12345678901");

        Autor p = autorService.buscarAutor(1L);

        assertNotNull(p);
    }

    @Test
    void deveLancarExcecaoQuandoListarLivrosDeAutorInexistente() {

        assertThrows(RuntimeException.class, () -> autorService.listarLivrosPorAutor(999L));
    }

    @Test
    void deveRetornarListaDeLivrosQuandoPessoaExistente() {

        livroService.criarLivro("Odisseia", "00998877665", LocalDate.of(2010, 10, 10));
        autorService.criarAutor("Maria", "NB", Year.of(2005), "12345678901");

        String listaDeLivros = autorService.listarLivrosPorAutor(1L);

        assertTrue(listaDeLivros.contains("Odisseia"));
    }

    //Remover pessoa
    @Test
    void deveLancarExcecaoQuandoRemoverAutorInexistente() {

        assertThrows(RuntimeException.class, () -> autorService.removerAutor(999L));
    }

    @Test
    void deveRemoverAutorQuandoAutorExistenteForRemovida() {

        autorService.criarAutor("Maria", "NB", Year.of(2005), "12345678901");

        autorService.removerAutor(1L);

        assertThrows(RuntimeException.class, () -> autorService.buscarAutor(1L));
    }

    @Test
    void deveLancarExcecaoQuandoTentaRemoverComAListaVazia() {

        assertThrows(RuntimeException.class, () -> autorService.removerAutor(1L));
    }

    //Exceções
    @Test
    void deveLancarExcecaoQuandoNomeNulo() {

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("null", "NB", Year.of(2005), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoNomeBlank() {

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("", "NB", Year.of(2005), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoSexoInvalido() {

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("Maria", "Feminino", Year.of(2005), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoDataNascNulo() {

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("Maria", "NB", null, "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNulo() {

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("Maria", "NB", Year.of(2005), null));
    }

    @Test
    void deveLancarExcecaoQuandoCpfBlank() {

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("Maria", "NB", Year.of(2005), " "));
    }

    @Test
    void deveLancarExcecaoQuandoCpfRepetido() {

        autorService.criarAutor("Maria", "NB", Year.of(2005), "12345678901");

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("Joana", "F", Year.of(2005), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoCpfTamanhoInvalido() {

        assertThrows(RuntimeException.class, () -> autorService.criarAutor("Maria", "NB", Year.of(2005), "12345"));
    }
}
