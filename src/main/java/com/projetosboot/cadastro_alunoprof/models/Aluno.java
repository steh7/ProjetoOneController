package com.projetosboot.cadastro_alunoprof.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class Aluno extends Pessoa {
    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    private int idade;

    // Construtor vazio
    public Aluno() {}

    // Construtor com parâmetros
    public Aluno(int id, String nome, String sobrenome, String cpf, int idade) {
        super(id, nome, sobrenome, cpf);
        this.idade = idade;
    }

    // Getter e Setter para idade
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}