package com.db.biblioteca.service;

import com.db.biblioteca.dto.LivroRequest;
import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.LivroRepository;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Livro criarLivro(LivroRequest request) {
        validarLivro(request.nome(), request.isbn(), request.dataPublicacao(), -1L);

        Livro livro = new Livro(request.nome(), request.isbn(), request.dataPublicacao(), new ArrayList<>());
        livro.setId(proximoId++);
        livroRepository.salvar(livro);
        return livro;
    }

    public Livro atualizarLivro(Long id, LivroRequest request) {
        Livro livro = buscarLivro(id);

        validarLivro(request.nome(), request.isbn(), request.dataPublicacao(), id);

        livro.setNome(request.nome());
        livro.setIsbn(request.isbn());
        livro.setDataPublicacao(request.dataPublicacao());

        return livro;
    }

    public void vincularAutorAoLivro(Long autorId, Long livroId) {
        Autor autor = autorService.buscarAutor(autorId);
        Livro livro = buscarLivro(livroId);

        if (!livro.getAutores().contains(autor)) {
            livro.getAutores().add(autor);
        }

        if (!autor.getLivros().contains(livro)) {
            autor.getLivros().add(livro);
        }
    }

    public List<Livro> listarTodosLivros() {
        return livroRepository.getLivros();
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