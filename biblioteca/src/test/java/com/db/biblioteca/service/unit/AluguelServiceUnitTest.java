package com.db.biblioteca.service.unit;

import com.db.biblioteca.dto.AluguelRequest;
import com.db.biblioteca.model.Aluguel;
import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.AluguelRepository;
import com.db.biblioteca.service.AluguelService;
import com.db.biblioteca.service.LivroService;
import com.db.biblioteca.service.LocatarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AluguelServiceUnitTest {
    @Mock
    private AluguelRepository aluguelRepository;

    @Mock
    private LivroService livroService;

    @Mock
    private LocatarioService locatarioService;

    @InjectMocks
    private AluguelService aluguelService;

    @Test
    void deveCriarAluguelQuandoDadosValidos() {
        // arrange
        Locatario locatario = new Locatario(
                "Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2000, 1, 1), "12345678901");
        locatario.setId(1L);

        Livro livro = new Livro(
                "Odisseia", "123", LocalDate.of(1996, 1, 1), List.of());
        livro.setId(1L);
        livro.setAlugado(false);

        when(locatarioService.buscarLocatario(1L))
                .thenReturn(locatario);

        when(livroService.buscarLivro(1L))
                .thenReturn(livro);

        List<Long> livrosIds = new ArrayList<>();
        livrosIds.add(1L);

        AluguelRequest request =
                new AluguelRequest(livrosIds, 1L);

        Aluguel aluguel = aluguelService.criarAluguel(request);

        assertNotNull(aluguel);
        assertTrue(livro.isAlugado());
    }


    @Test
    void deveLancarExcecaoQuandoLocatarioInexistente() {
        when(locatarioService.buscarLocatario(1L))
                .thenThrow(new RuntimeException("Locatário não encontrado"));

        List<Long> livrosIds = new ArrayList<>();
        livrosIds.add(1L);

        AluguelRequest request =
                new AluguelRequest(livrosIds, 1L);

        assertThrows(RuntimeException.class,
                () -> aluguelService.criarAluguel(request));
    }

    @Test
    void deveLancarExcecaoQuandoBuscarAluguelInexistente() {
        when(aluguelRepository.buscarPorId(1L))
                .thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> aluguelService.buscarAluguel(1L));
    }

    @Test
    void deveRemoverAluguelQuandoExiste() {
        Livro livro = new Livro("Odisseia", "123", LocalDate.now(), List.of());
        livro.setAlugado(true);

        Aluguel aluguel = new Aluguel(LocalDate.now(), LocalDate.now().plusDays(2));
        aluguel.setId(1L);

        List<Livro> livros = new ArrayList<>();
        livros.add(livro);
        aluguel.setLivros(livros);

        when(aluguelRepository.buscarPorId(1L))
                .thenReturn(aluguel);

        aluguelService.removerAluguel(1L);

        assertFalse(livro.isAlugado());
    }



    @Test
    void deveLancarExcecaoQuandoLivroJaAlugado() {
        Locatario locatario = new Locatario(
                "Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2000, 1, 1), "12345678901");
        locatario.setId(1L);

        Autor autor = new Autor(
                "Homero", "M", Year.of(2005), "11111111111");

        Livro livro = new Livro(
                "Odisseia", "123", LocalDate.of(1996, 1, 1), List.of(autor));
        livro.setId(1L);
        livro.setAlugado(true);

        when(locatarioService.buscarLocatario(1L)).thenReturn(locatario);
        when(livroService.buscarLivro(1L)).thenReturn(livro);

        List<Long> livrosIds = new ArrayList<>();
        livrosIds.add(1L);

        AluguelRequest request = new AluguelRequest(livrosIds, 1L);

        assertThrows(RuntimeException.class,
                () -> aluguelService.criarAluguel(request));
    }

}
