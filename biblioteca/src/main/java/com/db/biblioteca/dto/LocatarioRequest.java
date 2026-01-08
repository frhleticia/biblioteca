package com.db.biblioteca.dto;

import java.time.LocalDate;

public record LocatarioRequest(String nome, String sexo, String telefone, String email, LocalDate dataNasc, String cpf) {
}
