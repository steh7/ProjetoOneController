package com.projetosboot.cadastro_alunoprof.models;

public class Professor extends Pessoa {
    // Construtor vazio
    public Professor() {}

    // Construtor com parâmetros
    public Professor(int id, String nome, String sobrenome, String cpf) {
        super(id, nome, sobrenome, cpf);
    }
}