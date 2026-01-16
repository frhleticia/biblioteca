package com.db.biblioteca.dto;

import java.time.Year;

public record AutorRequest(String nome, String sexo, Integer anoNasc, String cpf) {
}
