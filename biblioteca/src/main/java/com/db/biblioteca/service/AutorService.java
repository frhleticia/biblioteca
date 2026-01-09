package com.db.biblioteca.service;

import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.AutorRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/autores")
public class AutorService {
    private final AutorRepository autorRepository;
    private final LivroService livroService;
    private Long proximoId = 1L;

    public AutorService(AutorRepository autorRepository, LivroService livroService) {
        this.autorRepository = autorRepository;
        this.livroService = livroService;
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

        Autor autor = new Autor(nome, sexo, anoNasc, cpf, new ArrayList<>());
        autor.setId(proximoId++);
        autorRepository.salvar(autor);
        return autor;
    }

    public void atribuirLivroAoAutor(Long autorId, Long livroId) {
        Autor autor = buscarAutor(autorId);
        Livro livro = livroService.buscarLivro(livroId);

        autor.adicionarLivro(livro);
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

    public List<Livro> listarLivrosPorAutor(Long id) {
        Autor autor = buscarAutor(id);
        validarListaDeLivros(autor.getLivros(), id);
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

    public void validarListaDeLivros(List<Livro> livros, Long id){
        Autor autor = buscarAutor(id);
        if (autor.getLivros().isEmpty()) {
            throw new RuntimeException("Este autor não parece ter nenhum livro em seu nome");
        }
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
