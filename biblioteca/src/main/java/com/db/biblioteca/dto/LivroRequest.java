package com.db.biblioteca.dto;

import java.time.LocalDate;

public record LivroRequest(String nome, String isbn, LocalDate dataPublicacao) {
}
