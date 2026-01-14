package com.db.biblioteca.dto;

import java.time.Year;

public record AutorRequest(String nome, String sexo, Year anoNasc, String cpf) {
}
