package com.db.biblioteca.service;

import com.db.biblioteca.dto.AutorRequest;
import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Objects;

@Service
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

    public Autor criarAutor(AutorRequest request) {
        validarAutor(request.nome(), request.sexo(), request.anoNasc(), request.cpf(), -1L);

        Autor autor = new Autor(request.nome(), request.sexo(), request.anoNasc(), request.cpf());
        autor.setId(proximoId++);
        autorRepository.salvar(autor);

        return autor;
    }

    public Autor atualizarAutor(Long id, AutorRequest request) {
        Autor autor = buscarAutor(id);

        validarAutor(request.nome(), request.sexo(), request.anoNasc(), request.cpf(), id);
        autor.setNome(request.nome());
        autor.setSexo(request.sexo());
        autor.setAnoNasc(request.anoNasc());
        autor.setCpf(request.cpf());

        return autor;
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

        if (!autor.getLivros().isEmpty()) {
            throw new RuntimeException("Autor possui livros associados");
        }

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
