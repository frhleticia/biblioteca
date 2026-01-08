package com.db.biblioteca.model;

import java.time.LocalDate;
import java.util.List;

public class Autor {
    private Long id;
    private String nome;
    private String sexo;
    private LocalDate anoNasc;
    private String cpf;
    private List<Livro> livros;

    public Autor(String nome, String sexo, LocalDate anoNasc, String cpf, List<Livro> livros) {
        this.nome = nome;
        this.sexo = sexo;
        this.anoNasc = anoNasc;
        this.cpf = cpf;
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ", anoNasc=" + anoNasc +
                ", cpf='" + cpf + '\'' +
                ", livros=" + livros +
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getAnoNasc() {
        return anoNasc;
    }

    public void setAnoNasc(LocalDate anoNasc) {
        this.anoNasc = anoNasc;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}
