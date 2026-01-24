package com.db.biblioteca.service.unit;

import com.db.biblioteca.dto.LocatarioRequest;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.LocatarioRepository;
import com.db.biblioteca.service.LocatarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocatarioServiceUnitTest {
    private LocatarioService locatarioService;
    private LocatarioRepository locatarioRepository;

    @BeforeEach
    void setup() {
        locatarioRepository = mock(LocatarioRepository.class);
        locatarioService = new LocatarioService(locatarioRepository);
    }

    @Test
    void deveCriarLocatarioQuandoDadosValidos() {
        when(locatarioRepository.getLocatarios())
                .thenReturn(List.of());

        Locatario locatario = new Locatario(
                "Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

        assertNotNull(locatario);
        assertEquals("Maria", locatario.getNome());
        assertEquals("12345678901", locatario.getCpf());
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        Locatario existente = new Locatario(
                "Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");
        existente.setId(1L);

        when(locatarioRepository.getLocatarios())
                .thenReturn(List.of(existente));

        LocatarioRequest request = new LocatarioRequest(
                "Joao", "M", "51999666999", "joao@email.com", LocalDate.of(2002, 2, 2), "12345678901");

        assertThrows(RuntimeException.class,
                () -> locatarioService.criarLocatario(request));
    }

    @Test
    void deveBuscarLocatarioQuandoExiste() {
        Locatario locatario = new Locatario(
                "Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");
        locatario.setId(1L);

        when(locatarioRepository.getLocatarios())
                .thenReturn(List.of(locatario));

        Locatario encontrado = locatarioService.buscarLocatario(1L);

        assertEquals("Maria", encontrado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoBuscarLocatarioInexistente() {
        when(locatarioRepository.getLocatarios())
                .thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> locatarioService.buscarLocatario(1L));
    }

    @Test
    void deveAtualizarLocatarioQuandoDadosValidos() {
        Locatario existente = new Locatario(
                "Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2000, 1, 1), "12345678901"
        );
        existente.setId(1L);

        when(locatarioRepository.getLocatarios())
                .thenReturn(List.of(existente));

        Locatario atualizado = locatarioService.atualizarLocatario(
                1L,
                new LocatarioRequest(
                        "Joana", "F", "51999999999", "joana@email.com", LocalDate.of(1998, 5, 10), "12345678901"
                ));

        assertEquals("Joana", atualizado.getNome());
        assertEquals("joana@email.com", atualizado.getEmail());
    }

    @Test
    void deveRemoverLocatarioQuandoExiste() {
        Locatario loc = new Locatario(
                "Maria", "NB", "51999999999", "maria@email.com", LocalDate.of(2000, 1, 1), "12345678901");
        loc.setId(1L);

        when(locatarioRepository.getLocatarios())
                .thenReturn(List.of(loc));

        locatarioService.removerLocatario(1L);

        verify(locatarioRepository).remover(loc);
    }
}
