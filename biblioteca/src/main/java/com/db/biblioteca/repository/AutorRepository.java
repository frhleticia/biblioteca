package com.db.biblioteca.repository;

import com.db.biblioteca.model.Autor;

import java.util.ArrayList;
import java.util.List;

public class AutorRepository {
    private List<Autor> autores = new ArrayList<>();

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
