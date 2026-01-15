package com.db.biblioteca.repository;

import com.db.biblioteca.model.Livro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LivroRepository {
    private List<Livro> livros = new ArrayList<>();

    public Livro buscarPorId(Long id) {
        for (Livro livro : livros) {
            if (livro.getId().equals(id)) {
                return livro;
            }
        }
        return null;
    }

    public void salvar(Livro livro) {
        livros.add(livro);
    }

    public void remover(Livro livro) {
        livros.remove(livro);
    }

    public List<Livro> getLivros() {
        return livros;
    }
}
