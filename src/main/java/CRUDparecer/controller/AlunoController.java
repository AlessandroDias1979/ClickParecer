package CRUDparecer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CRUDparecer.model.Aluno;
import CRUDparecer.service.AlunoService;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoService alunoService;

    @Autowired
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/getAllAlunos")
    public ResponseEntity<List<Aluno>> getAllAlunos() {
        List<Aluno> aluno = alunoService.getAllAlunos();
        return ResponseEntity.ok(aluno);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getAlunoById(@PathVariable int id) {
        Aluno aluno = alunoService.getAlunoById(id);
        Optional<Aluno> optionalAluno = Optional.ofNullable(aluno);
        return optionalAluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("turma/{turma}")
    public ResponseEntity<List<Aluno>> getAlunoByTurma(@PathVariable String turma) {
        List<Aluno> alunos = alunoService.getAlunoByTurma(turma);
        return ResponseEntity.ok(alunos);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Aluno> cadastrarAluno(@RequestBody Aluno aluno) {
        try {
            Aluno novo = alunoService.cadastrarAluno(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable int id, @RequestBody Aluno aluno) {
        try {
            Aluno atualizado = alunoService.atualizarAluno(id, aluno);
            if (atualizado != null) {
                return ResponseEntity.ok(atualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable int id) {
        try {
            boolean deletado = alunoService.deletarAluno(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}