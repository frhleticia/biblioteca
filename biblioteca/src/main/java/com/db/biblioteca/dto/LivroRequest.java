package com.db.biblioteca.dto;

import com.db.biblioteca.model.Autor;

import java.time.LocalDate;
import java.util.List;

public record LivroRequest(String nome, String isbn, LocalDate dataPublicacao, List<Autor> autores) {
}
