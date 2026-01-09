package com.db.biblioteca.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Autor {
    private Long id;
    private String nome;
    private String sexo;
    private Year anoNasc;
    private String cpf;
    private List<Livro> livros = new ArrayList<>();

    public Autor(String nome, String sexo, Year anoNasc, String cpf) {
        this.nome = nome;
        this.sexo = sexo;
        this.anoNasc = anoNasc;
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sexo='" + sexo + '\'' +
                ", anoNasc=" + anoNasc +
                ", cpf='" + cpf + '\'' +
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

    public Year getAnoNasc() {
        return anoNasc;
    }

    public void setAnoNasc(Year anoNasc) {
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
}
