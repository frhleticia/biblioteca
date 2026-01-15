package com.db.biblioteca.controller;

import com.db.biblioteca.dto.AluguelRequest;
import com.db.biblioteca.model.Aluguel;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.service.AluguelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alugueis")
public class AluguelController {
    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @PostMapping
    public void criarAluguel(@RequestBody AluguelRequest request) {
        aluguelService.criarAluguel(request);
    }

    @GetMapping
    public List<Aluguel> listarTodosAlugueis() {
        return aluguelService.listarTodosAlugueis();
    }

    @GetMapping("locatario/{id}/livros")
    public List<Livro> listarLivrosPorLocatario(@PathVariable Long id) {
        return aluguelService.listarLivrosAlugadosPorLocatario(id);
    }

    @DeleteMapping("/{id}")
    public void removerAluguel(@PathVariable Long id) {
        aluguelService.removerAluguel(id);
    }

}
