package com.db.biblioteca.service;

import com.db.biblioteca.exceptions.LocatarioValidator;
import com.db.biblioteca.repository.LocatarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocatarioValidatorTest {
    private LocatarioService locatarioService;

    @BeforeEach
    void setUp() {
        LocatarioRepository locatarioRepository = new LocatarioRepository();
        LocatarioValidator locatarioValidator = new LocatarioValidator(locatarioRepository);
        locatarioService = new LocatarioService(locatarioRepository, locatarioValidator);
    }

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
