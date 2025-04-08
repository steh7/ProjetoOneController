package com.projetosboot.cadastro_alunoprof.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetosboot.cadastro_alunoprof.models.Pessoa;
import com.projetosboot.cadastro_alunoprof.models.Aluno;
import com.projetosboot.cadastro_alunoprof.models.Professor;
import com.projetosboot.cadastro_alunoprof.models.Funcionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {

    // Lista única de Pessoas
    private static final List<Pessoa> cadastros = new ArrayList<>();

    // Validação simples de CPF (apenas formato básico)
    private boolean isValidCPF(String cpf) {
        return cpf != null && cpf.matches("\\d{11}"); // 11 dígitos numéricos
    }

    // ALUNOS
    @GetMapping("/alunos")
    public ResponseEntity<Object> getAlunos() {
        List<Aluno> alunos = cadastros.stream()
            .filter(p -> p instanceof Aluno)
            .map(p -> (Aluno) p)
            .toList();
        
        return alunos.isEmpty() ? 
            ResponseEntity.ok(Map.of("message", "Nenhum aluno cadastrado.")) : 
            ResponseEntity.ok(alunos);
    }

    @PostMapping("/alunos")
    public ResponseEntity<Object> addAluno(@RequestBody @Valid Aluno aluno) {
        if (!isValidCPF(aluno.getCpf())) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "CPF inválido",
                "message", "O CPF deve conter 11 dígitos numéricos"
            ));
        }
        
        if (cadastros.stream().anyMatch(p -> p.getCpf().equals(aluno.getCpf()))) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "CPF já cadastrado",
                "message", "Já existe uma pessoa com este CPF no sistema"
            ));
        }
        
        cadastros.add(aluno);
        return ResponseEntity.status(201).body(Map.of(
            "success", "Aluno cadastrado com sucesso!",
            "aluno", aluno
        ));
    }

    @PutMapping("/aluno/{cpf}/edit")
    public ResponseEntity<Object> updateAluno(@PathVariable String cpf, @RequestBody @Valid Aluno alunoNovo) {
        Optional<Pessoa> cadastroOpt = cadastros.stream()
            .filter(p -> p instanceof Aluno && p.getCpf().equals(cpf))
            .findFirst();

        if (cadastroOpt.isPresent()) {
            Aluno aluno = (Aluno) cadastroOpt.get();
            if (aluno.getIdade() >= 18) {
                aluno.setNome(alunoNovo.getNome());
                aluno.setSobrenome(alunoNovo.getSobrenome());
                aluno.setIdade(alunoNovo.getIdade());
                return ResponseEntity.ok(Map.of(
                    "success", "Aluno atualizado com sucesso!",
                    "aluno", aluno
                ));
            } else {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Atualização negada!",
                    "message", "Somente alunos maiores de 18 anos podem alterar suas informações."
                ));
            }
        }
        
        return ResponseEntity.status(404).body(Map.of(
            "error", "Aluno não encontrado!",
            "message", "Não foi possível localizar o aluno com o CPF fornecido."
        ));
    }

    @DeleteMapping("/aluno/{cpf}/delete")
    public ResponseEntity<Object> deleteAluno(@PathVariable String cpf) {
        Optional<Pessoa> cadastroOpt = cadastros.stream()
            .filter(p -> p instanceof Aluno && p.getCpf().equals(cpf))
            .findFirst();

        if (cadastroOpt.isPresent()) {
            cadastros.remove(cadastroOpt.get());
            return ResponseEntity.ok(Map.of(
                "success", "Aluno deletado com sucesso!",
                "message", "O aluno foi removido do sistema permanentemente."
            ));
        }

        return ResponseEntity.status(404).body(Map.of(
            "error", "Aluno não encontrado!",
            "message", "Não há aluno com o CPF fornecido para remoção."
        ));
    }

    // PROFESSORES
    @GetMapping("/professores")
    public ResponseEntity<Object> getProfessores() {
        List<Professor> professores = cadastros.stream()
            .filter(p -> p instanceof Professor)
            .map(p -> (Professor) p)
            .toList();
        
        return professores.isEmpty() ? 
            ResponseEntity.ok(Map.of("message", "Nenhum professor cadastrado.")) : 
            ResponseEntity.ok(professores);
    }

    @PostMapping("/professores")
    public ResponseEntity<Object> addProfessor(@RequestBody @Valid Professor professor) {
        if (!isValidCPF(professor.getCpf())) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "CPF inválido",
                "message", "O CPF deve conter 11 dígitos numéricos"
            ));
        }
        
        if (cadastros.stream().anyMatch(p -> p.getCpf().equals(professor.getCpf()))) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "CPF já cadastrado",
                "message", "Já existe uma pessoa com este CPF no sistema"
            ));
        }
        
        cadastros.add(professor);
        return ResponseEntity.status(201).body(Map.of(
            "success", "Professor cadastrado com sucesso!",
            "professor", professor
        ));
    }

    @PutMapping("/professor/{cpf}/edit")
    public ResponseEntity<Object> updateProfessor(@PathVariable String cpf, @RequestBody @Valid Professor professorNovo) {
        Optional<Pessoa> cadastroOpt = cadastros.stream()
            .filter(p -> p instanceof Professor && p.getCpf().equals(cpf))
            .findFirst();

        if (cadastroOpt.isPresent()) {
            Professor professor = (Professor) cadastroOpt.get();
            professor.setNome(professorNovo.getNome());
            professor.setSobrenome(professorNovo.getSobrenome());
            return ResponseEntity.ok(Map.of(
                "success", "Professor atualizado com sucesso!",
                "professor", professor
            ));
        }
        
        return ResponseEntity.status(404).body(Map.of(
            "error", "Professor não encontrado!",
            "message", "Não foi possível localizar o professor com o CPF fornecido."
        ));
    }

    @DeleteMapping("/professor/{cpf}/delete")
    public ResponseEntity<Object> deleteProfessor(@PathVariable String cpf) {
        Optional<Pessoa> cadastroOpt = cadastros.stream()
            .filter(p -> p instanceof Professor && p.getCpf().equals(cpf))
            .findFirst();

        if (cadastroOpt.isPresent()) {
            cadastros.remove(cadastroOpt.get());
            return ResponseEntity.ok(Map.of(
                "success", "Professor deletado com sucesso!",
                "message", "O professor foi removido do sistema permanentemente."
            ));
        }

        return ResponseEntity.status(404).body(Map.of(
            "error", "Professor não encontrado!",
            "message", "Não há professor com o CPF fornecido para remoção."
        ));
    }

    // FUNCIONÁRIOS
    @GetMapping("/funcionarios")
    public ResponseEntity<Object> getFuncionarios() {
        List<Funcionario> funcionarios = cadastros.stream()
            .filter(p -> p instanceof Funcionario)
            .map(p -> (Funcionario) p)
            .toList();
        
        return funcionarios.isEmpty() ? 
            ResponseEntity.ok(Map.of("message", "Nenhum funcionário cadastrado.")) : 
            ResponseEntity.ok(funcionarios);
    }

    @PostMapping("/funcionarios")
    public ResponseEntity<Object> addFuncionario(@RequestBody @Valid Funcionario funcionario) {
        if (!isValidCPF(funcionario.getCpf())) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "CPF inválido",
                "message", "O CPF deve conter 11 dígitos numéricos"
            ));
        }
        
        if (cadastros.stream().anyMatch(p -> p.getCpf().equals(funcionario.getCpf()))) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "CPF já cadastrado",
                "message", "Já existe uma pessoa com este CPF no sistema"
            ));
        }
        
        cadastros.add(funcionario);
        return ResponseEntity.status(201).body(Map.of(
            "success", "Funcionário cadastrado com sucesso!",
            "funcionario", funcionario
        ));
    }

    @PutMapping("/funcionario/{cpf}/edit")
    public ResponseEntity<Object> updateFuncionario(@PathVariable String cpf, @RequestBody @Valid Funcionario funcionarioNovo) {
        Optional<Pessoa> cadastroOpt = cadastros.stream()
            .filter(p -> p instanceof Funcionario && p.getCpf().equals(cpf))
            .findFirst();

        if (cadastroOpt.isPresent()) {
            Funcionario funcionario = (Funcionario) cadastroOpt.get();
            funcionario.setNome(funcionarioNovo.getNome());
            funcionario.setSobrenome(funcionarioNovo.getSobrenome());
            return ResponseEntity.ok(Map.of(
                "success", "Funcionário atualizado com sucesso!",
                "funcionario", funcionario
            ));
        }
        
        return ResponseEntity.status(404).body(Map.of(
            "error", "Funcionário não encontrado!",
            "message", "Não foi possível localizar o funcionário com o CPF fornecido."
        ));
    }

    @DeleteMapping("/funcionario/{cpf}/delete")
    public ResponseEntity<Object> deleteFuncionario(@PathVariable String cpf) {
        Optional<Pessoa> cadastroOpt = cadastros.stream()
            .filter(p -> p instanceof Funcionario && p.getCpf().equals(cpf))
            .findFirst();

        if (cadastroOpt.isPresent()) {
            cadastros.remove(cadastroOpt.get());
            return ResponseEntity.ok(Map.of(
                "success", "Funcionário deletado com sucesso!",
                "message", "O funcionário foi removido do sistema permanentemente."
            ));
        }

        return ResponseEntity.status(404).body(Map.of(
            "error", "Funcionário não encontrado!",
            "message", "Não há funcionário com o CPF fornecido para remoção."
        ));
    }

    // CONSULTA GENÉRICA POR CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Object> showByCpf(@PathVariable String cpf) {
        Optional<Pessoa> cadastroOpt = cadastros.stream()
            .filter(p -> p.getCpf().equals(cpf))
            .findFirst();

        if (cadastroOpt.isPresent()) {
            Pessoa pessoa = cadastroOpt.get();
            String type = pessoa instanceof Aluno ? "aluno" : 
                          pessoa instanceof Professor ? "professor" : "funcionario";
            return ResponseEntity.ok(Map.of(
                "message", type.substring(0, 1).toUpperCase() + type.substring(1) + " encontrado!",
                type, pessoa
            ));
        }

        return ResponseEntity.status(404).body(Map.of(
            "error", "Não encontrado!",
            "message", "Não há cadastro com este CPF."
        ));
    }
}