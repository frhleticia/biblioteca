package com.db.biblioteca.dto;

import java.util.List;

public record AluguelRequest(List<Long> livroIds, Long locatarioId) {
}
