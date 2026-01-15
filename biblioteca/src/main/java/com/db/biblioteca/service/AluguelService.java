package com.db.biblioteca.service;

import com.db.biblioteca.dto.AluguelRequest;
import com.db.biblioteca.model.Aluguel;
import com.db.biblioteca.model.Livro;
import com.db.biblioteca.model.Locatario;
import com.db.biblioteca.repository.AluguelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AluguelService {
    private final AluguelRepository aluguelRepository;
    private final LocatarioService locatarioService;
    private final LivroService livroService;
    private Long proximoId = 1L;

    public AluguelService(AluguelRepository aluguelRepository, LocatarioService locatarioService, LivroService livroService) {
        this.aluguelRepository = aluguelRepository;
        this.locatarioService = locatarioService;
        this.livroService = livroService;
    }

    public Aluguel buscarAluguel(Long id) {
        Aluguel aluguel = aluguelRepository.buscarPorId(id);

        if (aluguel == null) {
            throw new RuntimeException("Aluguel não encontrado");
        }

        return aluguel;
    }

    public Aluguel criarAluguel(AluguelRequest request) {
        Locatario locatario = locatarioService.buscarLocatario(request.locatarioId());

        List<Livro> livros = new ArrayList<>();

        for (Long livroId : request.livroIds()) {
            Livro livro = livroService.buscarLivro(livroId);

            if (livro.isAlugado()) {
                throw new RuntimeException("Livro já está alugado");
            }

            livros.add(livro);
        }

        for (Livro livro : livros) {
            livro.setAlugado(true);
        }

        LocalDate retirada = LocalDate.now();
        LocalDate devolucao = retirada.plusDays(2);

        Aluguel aluguel = new Aluguel(retirada, devolucao, livros, locatario);
        aluguel.setId(proximoId++);
        aluguelRepository.salvar(aluguel);

        return aluguel;
    }

    public void removerAluguel(Long id) {
        Aluguel aluguel = buscarAluguel(id);

        for (Livro livro : aluguel.getLivros()) {
            livro.setAlugado(false);
        }

        aluguelRepository.remover(aluguel);
    }

    public List<Aluguel> listarTodosAlugueis() {
        return aluguelRepository.getAlugueis();
    }

    public List<Livro> listarLivrosAlugadosPorLocatario(Long locatarioId) {
        List<Livro> livrosAlugadosPorLocatario = new ArrayList<>();

        for (Aluguel aluguel : aluguelRepository.getAlugueis()) {
            if (aluguel.getLocatario().getId().equals(locatarioId)) {
                for (Livro livro : aluguel.getLivros()){
                    livrosAlugadosPorLocatario.add(livro);
                }
            }
        }

        return livrosAlugadosPorLocatario;
    }

}
