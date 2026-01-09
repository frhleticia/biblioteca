package com.db.biblioteca.service;

import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.LivroRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorService autorService;
    private Long proximoId = 1L;

    public LivroService(LivroRepository livroRepository, AutorService autorService) {
        this.livroRepository = livroRepository;
        this.autorService = autorService;
    }

    public Livro buscarLivro(Long id) {
        Livro livro = livroRepository.buscarPorId(id);

        if (livro == null) {
            throw new RuntimeException("Livro não encontrado");
        }

        return livro;
    }

    public Livro criarLivro(String nome, String isbn, LocalDate dataPublicacao) {
        validarLivro(nome, isbn, dataPublicacao, -1L);

        Livro livro = new Livro(nome, isbn, dataPublicacao, new ArrayList<>());
        livro.setId(proximoId++);
        livroRepository.salvar(livro);
        return livro;
    }

    public void vincularAutorAoLivro(Long autorId, Long livroId) {
        Autor autor = autorService.buscarAutor(autorId);
        Livro livro = buscarLivro(livroId);

        if (!livro.getAutores().contains(autor)) {
            livro.getAutores().add(autor);
            autor.getLivros().add(livro);
        }
    }

    public void atualizarLivro(Long id, String nome, String isbn, LocalDate dataPublicacao) {
        Livro livro = buscarLivro(id);

        validarLivro(nome, isbn, dataPublicacao, id);

        livro.setNome(nome);
        livro.setIsbn(isbn);
        livro.setDataPublicacao(dataPublicacao);
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.getLivros();
    }

    public List<Autor> listarAutoresPorLivro(Long id) {
        Livro livro = buscarLivro(id);
        return livro.getAutores();
    }

    public List<Livro> listarLivrosPorAutor(Long autorId) {
        autorService.buscarAutor(autorId);

        List<Livro> listaDeResposta = new ArrayList<>();

        for (Livro livro : livroRepository.getLivros()) {
            for (Autor autor : livro.getAutores()) {
                if (autor.getId().equals(autorId)) {
                    listaDeResposta.add(livro);
                    break;
                }
            }
        }

        return listaDeResposta;
    }

    public void removerLivro(Long id) {
        Livro livro = buscarLivro(id);
        livroRepository.remover(livro);
    }

    public void validarLivro(String nome, String isbn, LocalDate dataPublicacao, Long id) {
        validarNome(nome);
        validarIsbn(isbn, id);
        validarDataPublicacao(dataPublicacao);
    }

    public void campoObrigatorio(String campo) {
        throw new RuntimeException("Campo " + campo + " é obrigatório");
    }

    public void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            campoObrigatorio("nome");
        }
    }

    public void validarDataPublicacao(LocalDate dataPublicacao) {
        if (dataPublicacao == null) {
            campoObrigatorio("data de publicação");
        }
    }

    public void validarIsbn(String isbn, Long id) {
        if (isbn == null || isbn.isBlank()) {
            campoObrigatorio("ISBN");
        }

        for (Livro livro : livroRepository.getLivros()) {
            if (livro.getIsbn().equals(isbn) && !Objects.equals(livro.getId(), id)) {
                throw new RuntimeException("ISBN já cadastrado");
            }
        }

        if (isbn.length() != 11) {
            throw new RuntimeException("ISBN deve conter 11 dígitos");
        }
    }
}