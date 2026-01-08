package com.db.biblioteca.service;

import com.db.biblioteca.exceptions.LocatarioValidator;
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
        void setUp() {
            LocatarioRepository locatarioRepository = new LocatarioRepository();
            LocatarioValidator locatarioValidator = new LocatarioValidator(locatarioRepository);
            locatarioService = new LocatarioService(locatarioRepository, locatarioValidator);
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
        void deveLancarExcecaoQuandoCpfRepetidoAoAtualizarLocatario() {

            locatarioService.criarLocatario("Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "12345678901");

            locatarioService.criarLocatario("Joana", "M", "55522233344", "joao@email.com", LocalDate.of(2002, 2, 10), "55522233344");

            assertThrows(RuntimeException.class, () -> locatarioService.atualizarLocatario(1L, "Maria", "NB", "11122233344", "maria@email.com", LocalDate.of(2005, 5, 5), "55522233344"));
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
    }