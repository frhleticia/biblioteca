package com.db.biblioteca.service;

import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.AutorRepository;

import java.time.Year;
import java.util.List;
import java.util.Objects;

public class AutorService {
    private final AutorRepository autorRepository;
    private Long proximoId = 1L;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor buscarAutor(Long id) {
        Autor autor = autorRepository.buscarPorId(id);
        if (autor == null) {
            throw new RuntimeException("Autor não encontrado");
        }
        return autor;
    }

    public Autor criarAutor(String nome, String sexo, Year anoNasc, String cpf) {
        validarAutor(nome, sexo, anoNasc, cpf, -1L);
        Autor autor = new Autor(nome, sexo, anoNasc, cpf);
        autor.setId(proximoId++);
        autorRepository.salvar(autor);
        return autor;
    }

    public void atualizarAutor(Long id, String nome, String sexo, Year anoNasc, String cpf) {
        Autor autor = buscarAutor(id);
        validarAutor(nome, sexo, anoNasc, cpf, id);
        autor.setNome(nome);
        autor.setSexo(sexo);
        autor.setAnoNasc(anoNasc);
        autor.setCpf(cpf);
    }

    public List<Autor> listarTodosAutores() {
        return autorRepository.getAutores();
    }

    public List<Livro> listarLivrosPorAutor(Long autorId) {
        Autor autor = buscarAutor(autorId);
        return autor.getLivros();
    }

    public void removerAutor(Long id) {
        Autor autor = buscarAutor(id);
        autorRepository.remover(autor);
    }

    public void validarAutor(String nome, String sexo, Year anoNasc, String cpf, Long id) {
        validarNome(nome);
        validarSexo(sexo);
        validarAnoNasc(anoNasc);
        validarCpf(cpf, id);
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

    public void validarAnoNasc(Year dataNasc) {
        if (dataNasc == null) {
            campoObrigatorio("data de nascimento");
        }
    }

    public void validarCpf(String cpf, Long id) {
        if (cpf == null || cpf.isBlank()) {
            campoObrigatorio("CPF");
        }
        for (Autor autor : autorRepository.getAutores()) {
            if (autor.getCpf().equals(cpf) && !Objects.equals(autor.getId(), id)) {
                throw new RuntimeException("CPF já cadastrado");
            }
        }
        if (cpf.length() != 11) {
            throw new RuntimeException("CPF deve conter 11 dígitos");
        }
    }
}
