package com.db.biblioteca.service;

import com.db.biblioteca.exceptions.LocatarioValidator;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.LocatarioRepository;

import java.time.LocalDate;
import java.util.Objects;

public class LocatarioService {
    private final LocatarioRepository locatarioRepository;
    private final LocatarioValidator locatarioValidator;
    private Long proximoId = 1L;

    public LocatarioService(LocatarioRepository locatarioRepository, LocatarioValidator locatarioValidator) {
        this.locatarioRepository = locatarioRepository;
        this.locatarioValidator = locatarioValidator;
    }

    public Locatario buscarLocatario(Long locId) {
        for (Locatario locatario : locatarioRepository.getLocatarios()) {
            if (Objects.equals(locatario.getId(), locId)) {
                return locatario;
            }
        }
        throw new RuntimeException("Locatário não encontrado");
    }

    public void criarLocatario(String nome, String sexo, String telefone, String email, LocalDate dataNasc, String cpf) {
        locatarioValidator.validarLocatario(nome, sexo, telefone, email, dataNasc, cpf, -1L);

        Locatario loc = new Locatario(nome, sexo, telefone, email, dataNasc, cpf);
        loc.setId(proximoId++);
        locatarioRepository.salvar(loc);
    }

    public void atualizarLocatario(Long locId, String nome, String sexo, String telefone, String email, LocalDate dataNasc, String cpf) {
        Locatario loc = buscarLocatario(locId);

        locatarioValidator.validarLocatario(nome, sexo, telefone, email, dataNasc, cpf, locId);

        loc.setNome(nome);
        loc.setSexo(sexo);
        loc.setTelefone(telefone);
        loc.setEmail(email);
        loc.setDataNasc(dataNasc);
        loc.setCpf(cpf);
    }

    public String listarTodosLocatarios() {
        return locatarioRepository.getLocatarios().toString();
    }

    public void removerLocatario(Long locId) {
        Locatario loc = buscarLocatario(locId);
        locatarioRepository.remover(loc);
    }
}
