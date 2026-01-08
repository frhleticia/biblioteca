package com.db.biblioteca.repository;

import com.db.biblioteca.model.Locatario;

import java.util.ArrayList;
import java.util.List;

public class LocatarioRepository {
    private List<Locatario> locatarios = new ArrayList<>();

    public void salvar(Locatario locatario) {
        locatarios.add(locatario);
    }

    public void remover(Locatario locatario) {
        locatarios.remove(locatario);
    }

    public List<Locatario> getLocatarios() {
        return locatarios;
    }
}
