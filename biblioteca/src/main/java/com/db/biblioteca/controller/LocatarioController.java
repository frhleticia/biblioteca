package com.db.biblioteca.controller;

import com.db.biblioteca.service.LocatarioService;

import java.time.LocalDate;
import java.util.Scanner;

public class LocatarioController {
    private final LocatarioService locatarioService;
    Scanner scanner = new Scanner(System.in);

    public LocatarioController(LocatarioService locatarioService) {
        this.locatarioService = locatarioService;
    }

    private String lerTexto(String mensagem) {
        System.out.println(mensagem);
        return scanner.nextLine();
    }

    public void criarLocatario() {
        String nome = lerTexto("Nome: ");
        String sexo = lerTexto("Sexo ('M', 'F', 'NB' ou vazio): ");
        String telefone = lerTexto("Telefone: ");
        String email = lerTexto("Email: ");
        String cpf = lerTexto("CPF: ");
        System.out.println("Data de nascimento (aaaa-mm-dd): ");
        LocalDate dataNasc = LocalDate.parse(scanner.nextLine());

        locatarioService.criarLocatario(nome, sexo, telefone, email, dataNasc, cpf);
    }
}
