package com.projetosboot.cadastro_alunoprof.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private static final List<Aluno> alunos = new ArrayList<>();
    private static final List<Professor> professores = new ArrayList<>();
    private static final List<Funcionario> funcionarios = new ArrayList<>();

    // Validação simples de CPF (apenas formato básico)
    private boolean isValidCPF(String cpf) {
        return cpf != null && cpf.matches("\\d{11}"); // 11 dígitos numéricos
    }

    // ALUNOS
    @GetMapping("/alunos")
    public ResponseEntity<Object> getAlunos() {
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
        
        if (alunos.stream().anyMatch(a -> a.getCpf().equals(aluno.getCpf()))) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Aluno já cadastrado",
                "message", "Já existe um aluno com este CPF"
            ));
        }
        
        alunos.add(aluno);
        return ResponseEntity.status(201).body(Map.of(
            "success", "Aluno cadastrado com sucesso!",
            "aluno", aluno
        ));
    }

    @PutMapping("/aluno/{cpf}/edit")
    public ResponseEntity<Object> updateAluno(@PathVariable String cpf, @RequestBody @Valid Aluno alunoNovo) {
        Optional<Aluno> alunoOpt = alunos.stream()
            .filter(a -> a.getCpf().equals(cpf))
            .findFirst();

        if (alunoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
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
        Optional<Aluno> alunoOpt = alunos.stream()
            .filter(a -> a.getCpf().equals(cpf))
            .findFirst();

        if (alunoOpt.isPresent()) {
            alunos.remove(alunoOpt.get());
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
        
        if (professores.stream().anyMatch(p -> p.getCpf().equals(professor.getCpf()))) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Professor já cadastrado",
                "message", "Já existe um professor com este CPF"
            ));
        }
        
        professores.add(professor);
        return ResponseEntity.status(201).body(Map.of(
            "success", "Professor cadastrado com sucesso!",
            "professor", professor
        ));
    }

    @PutMapping("/professor/{cpf}/edit")
    public ResponseEntity<Object> updateProfessor(@PathVariable String cpf, @RequestBody @Valid Professor professorNovo) {
        Optional<Professor> professorOpt = professores.stream()
            .filter(p -> p.getCpf().equals(cpf))
            .findFirst();

        if (professorOpt.isPresent()) {
            Professor professor = professorOpt.get();
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
        Optional<Professor> professorOpt = professores.stream()
            .filter(p -> p.getCpf().equals(cpf))
            .findFirst();

        if (professorOpt.isPresent()) {
            professores.remove(professorOpt.get());
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
        
        if (funcionarios.stream().anyMatch(f -> f.getCpf().equals(funcionario.getCpf()))) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Funcionário já cadastrado",
                "message", "Já existe um funcionário com este CPF"
            ));
        }
        
        funcionarios.add(funcionario);
        return ResponseEntity.status(201).body(Map.of(
            "success", "Funcionário cadastrado com sucesso!",
            "funcionario", funcionario
        ));
    }

    @PutMapping("/funcionario/{cpf}/edit")
    public ResponseEntity<Object> updateFuncionario(@PathVariable String cpf, @RequestBody @Valid Funcionario funcionarioNovo) {
        Optional<Funcionario> funcionarioOpt = funcionarios.stream()
            .filter(f -> f.getCpf().equals(cpf))
            .findFirst();

        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();
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
        Optional<Funcionario> funcionarioOpt = funcionarios.stream()
            .filter(f -> f.getCpf().equals(cpf))
            .findFirst();

        if (funcionarioOpt.isPresent()) {
            funcionarios.remove(funcionarioOpt.get());
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

    // Método genérico para show
    private <T> ResponseEntity<Object> showEntity(List<T> list, String type, String cpf) {
        Optional<T> entity = list.stream()
            .filter(e -> {
                if (e instanceof Aluno) return ((Aluno)e).getCpf().equals(cpf);
                if (e instanceof Professor) return ((Professor)e).getCpf().equals(cpf);
                if (e instanceof Funcionario) return ((Funcionario)e).getCpf().equals(cpf);
                return false;
            })
            .findFirst();

        return entity.isPresent() ? 
            ResponseEntity.ok(Map.of("message", type + " encontrado!", type.toLowerCase(), entity.get())) :
            ResponseEntity.status(404).body(Map.of("error", type + " não encontrado!", 
                "message", "Não há " + type.toLowerCase() + " com este CPF"));
    }

    @GetMapping("/aluno/{cpf}")
    public ResponseEntity<Object> showAluno(@PathVariable String cpf) {
        return showEntity(alunos, "Aluno", cpf);
    }

    @GetMapping("/professor/{cpf}")
    public ResponseEntity<Object> showProfessor(@PathVariable String cpf) {
        return showEntity(professores, "Professor", cpf);
    }

    @GetMapping("/funcionario/{cpf}")
    public ResponseEntity<Object> showFuncionario(@PathVariable String cpf) {
        return showEntity(funcionarios, "Funcionário", cpf);
    }
}