package com.db.biblioteca.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.db.biblioteca.repository.LocatarioRepository;
import com.db.biblioteca.model.Locatario;

public class LocatarioServiceTest {
    private LocatarioService locatarioService;

    @BeforeEach
    void setup() {
        LocatarioRepository repository = new LocatarioRepository();
        locatarioService = new LocatarioService(repository);
    }

    //Criar usuário
    @Test
    void deveSalvarUsuarioQuandoTodosDadosValidos() {

        locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        Locatario p = locatarioService.buscarLocatario(1L);

        assertNotNull(p);
    }

    //Atualizar pessoa
    @Test
    void deveLancarExcecaoQuandoCpfRepetidoQuandoAtualizarLocatario() {

        locatarioService.criarLocatario("Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        locatarioService.criarLocatario("Joana", "M", "55522233344", "joao@email.com", LocalDate.of(2002, 2, 10), "55522233344");

        assertThrows(RuntimeException.class, () -> locatarioService.atualizarLocatario(1L, "Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "55522233344"));
    }

    @Test
    void deveAtualizarLocatarioQuandoTodosDadosValidos() {

        locatarioService.criarLocatario("Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        locatarioService.criarLocatario("Joana", "M", "55522233344", "joao@email.com", LocalDate.of(2002, 2, 10), "55522233344");

        locatarioService.atualizarLocatario(1L, "Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "10987654321");
        Locatario atualizado = locatarioService.buscarLocatario(1L);

        assertEquals("10987654321", atualizado.getCpf());
    }

    //Buscar pessoa por id
    @Test
    void deveRetornarNuloQuandoLocatarioInexistente() {

        assertThrows(RuntimeException.class, () -> locatarioService.buscarLocatario(999L));
    }

    @Test
    void deveRetornarLocatarioQuandoExistente() {

        locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        Locatario p = locatarioService.buscarLocatario(1L);

        assertNotNull(p);
    }

    //Remover pessoa
    @Test
    void deveLancarExcecaoQuandoRemoverLocatarioInexistente() {

        assertThrows(RuntimeException.class, () -> locatarioService.removerLocatario(999L));
    }

    @Test
    void deveRemoverLocatarioQuandoLocatarioExistenteForRemovida() {

        locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        locatarioService.removerLocatario(1L);

        assertThrows(RuntimeException.class, () -> locatarioService.buscarLocatario(1L));
    }

    @Test
    void deveLancarExcecaoQuandoTentaRemoverComAListaVazia() {

        assertThrows(RuntimeException.class, () -> locatarioService.removerLocatario(1L));
    }

    //Exceções
    @Test
    void deveLancarExcecaoQuandoNomeNulo() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario(null, "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoNomeBlank() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoSexoInvalido() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "Feminino", "123456789", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneNulo() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", null, "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneBlank() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", " ", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneRepetido() {

        locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Joana", "F", "51999999999", "joana@email.com", LocalDate.of(2002, 2, 2), "12345678912"));
    }

    @Test
    void deveLancarExcecaoQuandoTelefoneTamanhoInvalido() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", "123456789", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoEmailNulo() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", "51999999999", null, LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoEmailBlank() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", "51999999999", " ", LocalDate.of(2005, 5, 5), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoEmailRepetido() {

        locatarioService.criarLocatario("Maria", "NB", "51999999999", "banana@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Joana", "F", "51999999999", "banana@email.com", LocalDate.of(2002, 2, 2), "12345678912"));
    }

    @Test
    void deveLancarExcecaoQuandoDataNascNulo() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", null, "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoCpfNulo() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), null));
    }

    @Test
    void deveLancarExcecaoQuandoCpfBlank() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), ""));
    }

    @Test
    void deveLancarExcecaoQuandoCpfRepetido() {

        locatarioService.criarLocatario("Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Joana", "F", "51999999998", "joana@email.com", LocalDate.of(2002, 2, 2), "12345678901"));
    }

    @Test
    void deveLancarExcecaoQuandoCpfTamanhoInvalido() {

        assertThrows(RuntimeException.class, () -> locatarioService.criarLocatario("Maria", "NB", "123456789", "maria@email.com", LocalDate.of(2005, 5, 5), "123456789"));
    }
}