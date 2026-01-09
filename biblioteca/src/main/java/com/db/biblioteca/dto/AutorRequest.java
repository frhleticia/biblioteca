package com.db.biblioteca.dto;

import com.db.biblioteca.model.Livro;

import java.time.LocalDate;
import java.util.List;

public record AutorRequest(String nome, String sexo, LocalDate anoNasc, String cpf, List<Livro> livros) {
}
