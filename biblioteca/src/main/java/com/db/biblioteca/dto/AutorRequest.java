package com.db.biblioteca.dto;

import com.db.biblioteca.model.Livro;

import java.time.Year;
import java.util.List;

public record AutorRequest(String nome, String sexo, Year anoNasc, String cpf, List<Livro> livros) {
}
