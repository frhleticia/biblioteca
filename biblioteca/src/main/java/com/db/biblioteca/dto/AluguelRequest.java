package com.db.biblioteca.dto;

import java.time.LocalDate;

public record AluguelRequest(LocalDate dataRetirada, LocalDate dataDevolucao) {
}
