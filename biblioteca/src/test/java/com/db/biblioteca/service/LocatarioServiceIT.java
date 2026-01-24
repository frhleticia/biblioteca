package com.db.biblioteca.service;

import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.LocatarioRepository;
import com.db.biblioteca.dto.LocatarioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LocatarioServiceIT {

    private LocatarioService locatarioService;

    @BeforeEach
    void setup() {
        LocatarioRepository repository = new LocatarioRepository();

        locatarioService = new LocatarioService(repository);
    }

    @Test
    void deveSalvarUsuarioQuandoTodosDadosValidos() {
        locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        Locatario locatario = locatarioService.buscarLocatario(1L);

        assertNotNull(locatario);
    }

    @Test
    void deveLancarExcecaoQuandoCpfRepetidoQuandoAtualizarLocatario() {
        locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        locatarioService.criarLocatario(
                new LocatarioRequest("Joana", "M", "55522233344", "joao@email.com", LocalDate.of(2002, 2, 10), "55522233344"));

        assertThrows(RuntimeException.class,
                () -> locatarioService.atualizarLocatario(1L, new LocatarioRequest("Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "55522233344")));
    }

    @Test
    void deveAtualizarLocatarioQuandoTodosDadosValidos() {
        locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        locatarioService.criarLocatario(
                new LocatarioRequest("Joana", "M", "55522233344", "joao@email.com", LocalDate.of(2002, 2, 10), "55522233344"));

        locatarioService.atualizarLocatario(1L, new LocatarioRequest(
                "Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "10987654321"));

        Locatario atualizado = locatarioService.buscarLocatario(1L);

        assertEquals("10987654321", atualizado.getCpf());
    }

    @Test
    void deveRetornarNuloQuandoLocatarioInexistente() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.buscarLocatario(999L));
    }

    @Test
    void deveRetornarLocatarioQuandoExistente() {
        locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        Locatario locatario = locatarioService.buscarLocatario(1L);

        assertNotNull(locatario);
    }

    @Test
    void deveLancarExcecaoQuandoRemoverLocatarioInexistente() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.removerLocatario(999L));
    }

    @Test
    void deveRemoverLocatarioQuandoLocatarioExistenteForRemovida() {
        Locatario locatario = locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        locatarioService.removerLocatario(locatario.getId());

        assertThrows(RuntimeException.class,
                () -> locatarioService.buscarLocatario(locatario.getId()));
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest(null, "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoNomeBlank() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(new LocatarioRequest("", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoSexoInvalido() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "Feminino", "123456789", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneNulo() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", null, "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneBlank() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", " ", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneRepetido() {
        locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Joana", "F", "51999999999", "joana@email.com", LocalDate.of(2002, 2, 2), "12345678912")));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneTamanhoInvalido() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "123456789", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoEmailNulo() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", null, LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoEmailBlank() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", " ", LocalDate.of(2005, 5, 5), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoEmailRepetido() {
        locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "banana@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Joana", "F", "51999999999", "banana@email.com", LocalDate.of(2002, 2, 2), "12345678912")));
    }

    @Test
    void deveLancarExcecaoQuandoDataNascNulo() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", null, "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNulo() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), null)));
    }

    @Test
    void deveLancarExcecaoQuandoCpfBlank() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "")));
    }

    @Test
    void deveLancarExcecaoQuandoCpfRepetido() {
        locatarioService.criarLocatario(new LocatarioRequest("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));

        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Joana", "F", "51999999998", "joana@email.com", LocalDate.of(2002, 2, 2), "12345678901")));
    }

    @Test
    void deveLancarExcecaoQuandoCpfTamanhoInvalido() {
        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(
                new LocatarioRequest("Maria", "NB", "123456789", "maria@email.com", LocalDate.of(2005, 5, 5), "123456789")));
    }
}
