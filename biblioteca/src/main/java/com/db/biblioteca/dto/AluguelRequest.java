package com.db.biblioteca.dto;

import com.db.biblioteca.model.Locatario;

import java.time.LocalDate;
import java.util.List;

public record AluguelRequest(List<Long> livroIds, Long locatarioId) {
}
