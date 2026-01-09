package com.db.biblioteca.service;

import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.AutorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutorService {
    private final AutorRepository autorRepository;
    private Long proximoId = 1L;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor buscarAutor(Long autorId) {
        for (Autor autor : autorRepository.getAutores()) {
            if (Objects.equals(autor.getId(), autorId)) {
                return autor;
            }
        }
        throw new RuntimeException("Autor não encontrado");
    }

    public void criarAutor(String nome, String sexo, LocalDate anoNasc, String cpf) {
        validarLocatario(nome, sexo, anoNasc, cpf, -1L);

        List<Livro> livros = new ArrayList<>();
        Autor autor = new Autor(nome, sexo, anoNasc, cpf, livros);
        autor.setId(proximoId++);
        autorRepository.salvar(autor);
    }

    public void atualizarLocatario(Long autorId, String nome, String sexo, LocalDate anoNasc, String cpf) {
        Autor autor = buscarAutor(autorId);

        validarLocatario(nome, sexo, anoNasc, cpf, autorId);

        autor.setNome(nome);
        autor.setSexo(sexo);
        autor.setAnoNasc(anoNasc);
        autor.setCpf(cpf);
    }

    public String listarTodosAutores() {
        return autorRepository.getAutores().toString();
    }

    public String listarLivrosPorAutor(Long autorId) {
        Autor autor = buscarAutor(autorId);
        return autor.getLivros().toString();
    }


    public void removerAutor(Long autorId) {
        Autor autor = buscarAutor(autorId);
        autorRepository.remover(autor);
    }


    public void validarLocatario(String nome, String sexo, LocalDate anoNasc, String cpf, Long autorId) {
        validarNome(nome);
        validarSexo(sexo);
        validarAnoNasc(anoNasc);
        validarCpf(cpf, autorId);
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

    public void validarAnoNasc(LocalDate dataNasc) {
        if (dataNasc == null) {
            campoObrigatorio("data de nascimento");
        }
    }

    public void validarCpf(String cpf, Long autorId) {
        if (cpf == null || cpf.isBlank()) {
            campoObrigatorio("CPF");
        }

        for (Autor autor : autorRepository.getAutores()) {
            if (autor.getCpf().equals(cpf) && !Objects.equals(autor.getId(), autorId)) {
                throw new RuntimeException("CPF já cadastrado");
            }
        }

        if (cpf.length() != 11) {
            throw new RuntimeException("CPF deve conter 11 dígitos");
        }
    }

}
