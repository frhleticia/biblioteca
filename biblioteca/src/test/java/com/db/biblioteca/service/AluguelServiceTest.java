package com.db.biblioteca.service;

import com.db.biblioteca.dto.AluguelRequest;
import com.db.biblioteca.dto.LivroRequest;
import com.db.biblioteca.dto.LocatarioRequest;
import com.db.biblioteca.model.Aluguel;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.AluguelRepository;
import com.db.biblioteca.repository.AutorRepository;
import com.db.biblioteca.repository.LivroRepository;
import com.db.biblioteca.repository.LocatarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AluguelServiceTest {
    private AluguelService aluguelService;
    private LocatarioService locatarioService;
    private LivroService livroService;

    @BeforeEach
    void setup() {
        LocatarioRepository locatarioRepository = new LocatarioRepository();
        AutorRepository autorRepository = new AutorRepository();
        LivroRepository livroRepository = new LivroRepository();
        AluguelRepository aluguelRepository = new AluguelRepository();

        AutorService autorService = new AutorService(autorRepository);
        locatarioService = new LocatarioService(locatarioRepository);
        livroService = new LivroService(livroRepository, autorService);
        aluguelService = new AluguelService(aluguelRepository, locatarioService, livroService);
    }

    private Aluguel criarAluguelPadrao() {
        Locatario locatario = locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        Livro livro = livroService.criarLivro(
                new LivroRequest("Odisseia", "12345678901", LocalDate.of(1996, 1, 14)));

        List<Long> idLivros = new ArrayList<>();
        idLivros.add(livro.getId());

        return aluguelService.criarAluguel(
                new AluguelRequest(idLivros, locatario.getId()));
    }

    @Test
    void deveSalvarAluguelQuandoDadosValidos() {
        Aluguel aluguel = criarAluguelPadrao();

        assertNotNull(aluguel);
    }

    @Test
    void deveLancarExcecaoQuandoCriarAluguelComLivroAlugado() {
        Aluguel aluguel = criarAluguelPadrao();

        List<Long> idLivrosDoJoao = new ArrayList<>();
        idLivrosDoJoao.add(aluguel.getLivros().getFirst().getId());

        Locatario locatario = locatarioService.criarLocatario(
                new LocatarioRequest("João", "M", "51991234999", "joao@email.com", LocalDate.of(2007, 1, 5), "09876543210"));

        assertThrows(RuntimeException.class,
                () -> aluguelService.criarAluguel(
                        new AluguelRequest(idLivrosDoJoao, locatario.getId())));
    }

    @Test
    void deveLancarExcecaoQuandoAluguelInexistente() {
        assertThrows(RuntimeException.class,
                () -> aluguelService.buscarAluguel(999L));
    }

    @Test
    void deveRetornarAluguelQuandoExistente() {
        Aluguel aluguel = criarAluguelPadrao();

        Aluguel aluguelBuscado = aluguelService.buscarAluguel(aluguel.getId());

        assertNotNull(aluguelBuscado);
    }

    @Test
    void deveRetornarListaDeAlugueisQuandoListarTodosAlugueis() {
        Aluguel aluguel = criarAluguelPadrao();

        List<Aluguel> listaAlugueis = aluguelService.listarTodosAlugueis();

        assertTrue(listaAlugueis.contains(aluguel));
    }

    @Test
    void deveListarLivrosAlugadosDeLocatarioQuandoLocatarioExistente() {
        Aluguel aluguel = criarAluguelPadrao();

        List<Livro> livrosAlugadosDeLocatario = aluguelService.listarLivrosAlugadosPorLocatario(aluguel.getLocatario().getId());

        assertTrue(livrosAlugadosDeLocatario.contains(aluguel.getLivros().getFirst()));
    }

    @Test
    void deveLancarExcecaoQuandoListarLivrosAlugadosDeLocatarioInexistente() {
        assertThrows(RuntimeException.class,
                () -> aluguelService.listarLivrosAlugadosPorLocatario(999L));
    }

    @Test
    void deveLancarExcecaoQuandoRemoverAluguelExistente() {
        assertThrows(RuntimeException.class,
                () -> aluguelService.removerAluguel(999L));
    }

    @Test
    void deveRemoverAluguelQuandoRemoverAluguelExistente() {
        Aluguel aluguel = criarAluguelPadrao();

        aluguelService.removerAluguel(aluguel.getId());

        assertThrows(RuntimeException.class,
                () -> aluguelService.buscarAluguel(aluguel.getId()));
    }

    @Test
    void deveDesalugarLivroQuandoAluguelForRemovido() {
        Aluguel aluguel = criarAluguelPadrao();

        Livro livro = aluguel.getLivros().getFirst();
        assertTrue(livro.isAlugado());

        aluguelService.removerAluguel(aluguel.getId());

        assertFalse(livro.isAlugado());
    }
}
