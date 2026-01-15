package com.db.biblioteca.repository;

import com.db.biblioteca.model.Autor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class AutorRepository {
    private List<Autor> autores = new ArrayList<>();

    public Autor buscarPorId(Long id) {
        for (Autor autor : autores) {
            if (Objects.equals(autor.getId(), id)) {
                return autor;
            }
        }
        return null;
    }

    public void salvar(Autor autor) {
        autores.add(autor);
    }

    public void remover(Autor autor) {
        autores.remove(autor);
    }

    public List<Autor> getAutores() {
        return autores;
    }
}
