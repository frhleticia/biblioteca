package com.db.biblioteca.service;

import com.db.biblioteca.model.Autor;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.repository.LivroRepository;

import java.time.LocalDate;
import java.util.ArrayList;

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
        //validar

        Livro livro = new Livro(nome, isbn, dataPublicacao, new ArrayList<>());
        livro.setId(proximoId++);
        livroRepository.salvar(livro);
        return livro;
    }

    public void vincularAutorAoLivro(Long autorId, Long livroId) {
        Autor autor = autorService.buscarAutor(autorId);
        Livro livro = buscarLivro(livroId);

        autor.getLivros().add(livro);
        livro.getAutores().add(autor);
    }
}
