package com.db.biblioteca.repository;

import com.db.biblioteca.model.Aluguel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AluguelRepository {
    private List<Aluguel> alugueis = new ArrayList<>();

    public Aluguel buscarPorId(Long id) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getId().equals(id)) {
                return aluguel;
            }
        }
        return null;
    }

    public void salvar(Aluguel aluguel) {
        alugueis.add(aluguel);
    }

    public void remover(Aluguel aluguel) {
        alugueis.remove(aluguel);
    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }
}
