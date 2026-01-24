package com.db.biblioteca.service.unit;

import com.db.biblioteca.dto.AutorRequest;
import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.AutorRepository;
import com.db.biblioteca.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutorServiceUnitTest {
    private AutorService autorService;
    private AutorRepository autorRepository;

    @BeforeEach
    void setup() {
        autorRepository = mock(AutorRepository.class);
        autorService = new AutorService(autorRepository);
    }

    @Test
    void deveCriarAutorQuandoDadosValidos() {
        //arrange
        when(autorRepository.getAutores())//Isso garante: CPF não duplicado e teste focado só no AutorService
                .thenReturn(List.of());//quando o service perguntar ao repository quais autores existem, responda: nenhum
                                    //só tem esse when verificando o repository pq criarAutor tem o validar cpf q passa por lá
        //act
        Autor autor = autorService.criarAutor(
                new AutorRequest("Maria", "NB", 2005, "12345678901")
        );

        //assert
        assertNotNull(autor);
        assertEquals("Maria", autor.getNome());
        assertEquals("12345678901", autor.getCpf());
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        when(autorRepository.getAutores())
                .thenReturn(List.of());

        assertThrows(RuntimeException.class, () ->
                autorService.criarAutor(
                        new AutorRequest(null, "M", 2005, "12345678901")
                ));
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        Autor autorExistente = autorService.criarAutor(
                new AutorRequest("Maria", "NB", 2005, "12345678901"));
        autorExistente.setId(1L);

        when(autorRepository.getAutores())
                .thenReturn(List.of(autorExistente));

        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Joana", "F", 2005, "12345678901")
                ));
    }

    @Test
    void deveLancarExcecaoQuandoCpfTamanhoInvalido() {
        when(autorRepository.getAutores())
                .thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> autorService.criarAutor(
                        new AutorRequest("Maria", "NB", 2005, "123")
                ));
    }

    @Test
    void deveBuscarAutorQuandoExiste() {
        Autor autor = new Autor("Maria", "NB", Year.of(2005), "12345678901");
        autor.setId(1L);

        when(autorRepository.buscarPorId(1L))
                .thenReturn(autor);

        Autor resultado = autorService.buscarAutor(1L);

        assertEquals("Maria", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarAutorInexistente() {
        when(autorRepository.buscarPorId(1L))
                .thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> autorService.buscarAutor(1L));
    }

    @Test
    void deveAtualizarAutorQuandoDadosValidos() {
        Autor autor = new Autor("Maria", "NB", Year.of(2005), "12345678901");
        autor.setId(1L);

        when(autorRepository.buscarPorId(1L)).thenReturn(autor);
        when(autorRepository.getAutores()).thenReturn(List.of(autor));

        Autor atualizado = autorService.atualizarAutor(
                1L, new AutorRequest("Maria Clara", "NB", 2005, "12345678901"));

        assertEquals("Maria Clara", atualizado.getNome());
    }

    @Test
    void deveRemoverAutorQuandoNaoPossuiLivros() {
        Autor autor = new Autor("Maria", "NB", Year.of(2005), "12345678901");
        autor.setId(1L);

        when(autorRepository.buscarPorId(1L)).thenReturn(autor);

        autorService.removerAutor(1L);

        verify(autorRepository).remover(autor); //usando pq é uma forma de verificar a funcionalidade de métodos void, q n retornam nada
    }

    @Test
    void deveLancarExcecaoQuandoRemoverAutorComLivros() {
        Autor autor = new Autor("Maria", "NB", Year.of(2005), "12345678901");
        autor.setId(1L);

        Livro livro = new Livro(
                "Odisseia", "12345678901", LocalDate.of(1996, 1, 14), new ArrayList<>());

        autor.getLivros().add(livro);

        when(autorRepository.buscarPorId(1L))
                .thenReturn(autor);

        assertThrows(RuntimeException.class,
                () -> autorService.removerAutor(1L));
    }
}
