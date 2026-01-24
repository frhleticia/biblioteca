package com.db.biblioteca.model;

import java.time.LocalDate;
import java.util.List;

public class Livro {
    private Long id;
    private String nome;
    private String isbn;
    private LocalDate dataPublicacao;
    private List<Autor> autores;
    private boolean alugado;

    public Livro(String nome, String isbn, LocalDate dataPublicacao, List<Autor> autores) {
        this.nome = nome;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", isbn=" + isbn +
                ", dataPublicacao=" + dataPublicacao +
                ", autores=" + autores +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public boolean isAlugado() {
        return alugado;
    }

    public void setAlugado(boolean alugado) {
        this.alugado = alugado;
    }
}
