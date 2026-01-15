package com.db.biblioteca.service;

import com.db.biblioteca.dto.LocatarioRequest;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.LocatarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class LocatarioService {
    private final LocatarioRepository locatarioRepository;
    private Long proximoId = 1L;

    public LocatarioService(LocatarioRepository locatarioRepository) {
        this.locatarioRepository = locatarioRepository;
    }

    public Locatario buscarLocatario(Long locId) {
        for (Locatario locatario : locatarioRepository.getLocatarios()) {
            if (Objects.equals(locatario.getId(), locId)) {
                return locatario;
            }
        }
        throw new RuntimeException("Locatário não encontrado");
    }

    public Locatario criarLocatario(LocatarioRequest request) {
        validarLocatario(request.nome(), request.sexo(), request.telefone(), request.email(), request.dataNasc(), request.cpf(), -1L);

        Locatario loc = new Locatario(request.nome(), request.sexo(), request.telefone(), request.email(), request.dataNasc(), request.cpf());
        loc.setId(proximoId++);
        locatarioRepository.salvar(loc);

        return loc;
    }

    public Locatario atualizarLocatario(Long locId, LocatarioRequest request) {
        Locatario loc = buscarLocatario(locId);

        validarLocatario(request.nome(), request.sexo(), request.telefone(), request.email(), request.dataNasc(), request.cpf(), locId);

        loc.setNome(request.nome());
        loc.setSexo(request.sexo());
        loc.setTelefone(request.telefone());
        loc.setEmail(request.email());
        loc.setDataNasc(request.dataNasc());
        loc.setCpf(request.cpf());

        return loc;
    }

    public List<Locatario> listarTodosLocatarios() {
        return locatarioRepository.getLocatarios();
    }

    public void removerLocatario(Long locId) {
        Locatario loc = buscarLocatario(locId);
        locatarioRepository.remover(loc);
    }

    //Tratando de validações e exceções
    public void validarLocatario(String nome, String sexo, String telefone, String email, LocalDate dataNasc, String cpf, Long locId) {
        validarNome(nome);
        validarSexo(sexo);
        validarTelefone(telefone, locId);
        validarEmail(email, locId);
        validarDataNasc(dataNasc);
        validarCpf(cpf, locId);
    }

    public void campoObrigatorio(String campo) {
        throw new RuntimeException("Campo " + campo + " é obrigatório");
    }

    public void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            campoObrigatorio("nome");
        }
    }

    public void validarSexo(String sexo) {
        if (sexo == null || sexo.isBlank()) {
            return;
        }

        if (!sexo.equals("M") && !sexo.equals("F") && !sexo.equals("NB")) {
            throw new RuntimeException("Deve ser 'M', 'F', 'NB', ou vazio");
        }
    }

    public void validarTelefone(String telefone, Long locId) {
        if (telefone == null || telefone.isBlank()) {
            campoObrigatorio("telefone");
        }

        if (telefone.length() != 11) {
            throw new RuntimeException("Um telefone válido contém 10 dígitos");
        }

        for (Locatario loc : locatarioRepository.getLocatarios()) {
            if (loc.getTelefone().equals(telefone) && !Objects.equals(loc.getId(), locId)) {
                throw new RuntimeException("Telefone já cadastrado");
            }
        }
    }

    public void validarEmail(String email, Long locId) {
        if (email == null || email.isBlank()) {
            campoObrigatorio("email");
        }

        for (Locatario loc : locatarioRepository.getLocatarios()) {
            if (loc.getEmail().equals(email) && !Objects.equals(loc.getId(), locId)) {
                throw new RuntimeException("Email já cadastrado");
            }
        }
    }

    public void validarDataNasc(LocalDate dataNasc) {
        if (dataNasc == null) {
            campoObrigatorio("data de nascimento");
        }
    }

    public void validarCpf(String cpf, Long locId) {
        if (cpf == null || cpf.isBlank()) {
            campoObrigatorio("CPF");
        }

        for (Locatario loc : locatarioRepository.getLocatarios()) {
            if (loc.getCpf().equals(cpf) && !Objects.equals(loc.getId(), locId)) {
                throw new RuntimeException("CPF já cadastrado");
            }
        }

        if (cpf.length() != 11) {
            throw new RuntimeException("CPF deve conter 11 dígitos");
        }
    }
}
