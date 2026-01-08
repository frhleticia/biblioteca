package com.db.biblioteca.model;

import java.time.LocalDate;
import java.util.List;

public class Livro {
    private Long id;
    private String nome;
    private Long isbn;
    private LocalDate dataPublicacao;
    private List<Autor> autoresDoLivro;

    public Livro(String nome, Long isbn, LocalDate dataPublicacao, List<Autor> autoresDoLivro) {
        this.nome = nome;
        this.isbn = isbn;
        this.dataPublicacao = dataPublicacao;
        this.autoresDoLivro = autoresDoLivro;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", isbn=" + isbn +
                ", dataPublicacao=" + dataPublicacao +
                ", autoresDoLivro=" + autoresDoLivro +
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

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public List<Autor> getAutoresDoLivro() {
        return autoresDoLivro;
    }

    public void setAutoresDoLivro(List<Autor> autoresDoLivro) {
        this.autoresDoLivro = autoresDoLivro;
    }
}
