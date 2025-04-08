package com.projetosboot.cadastro_alunoprof.models;

public class Funcionario extends Pessoa {
    // Construtor vazio
    public Funcionario() {}

    // Construtor com parâmetros
    public Funcionario(int id, String nome, String sobrenome, String cpf) {
        super(id, nome, sobrenome, cpf);
    }
}