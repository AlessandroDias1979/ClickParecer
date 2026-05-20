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

import CRUDparecer.model.Professor;
import CRUDparecer.service.ProfessorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/professore")
public class ProfessorController{
    
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessores() {
        List<Professor> professores = professorService.getAllProfessores();
        return ResponseEntity.ok(professores);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long id) {
        Professor professor = professorService.getProfessorById(id);
        Optional<Professor> optionalProfessor = Optional.ofNullable(professor);
        return optionalProfessor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Professor> getProfessorByEmail(@PathVariable String email) {
        Professor professor = professorService.getProfessorByEmail(email);
        Optional<Professor> optionalProfessor = Optional.ofNullable(professor);
        return optionalProfessor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody @Valid Professor professor) {
        try {
            Professor novo = professorService.cadastrarProfessor(professor);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cadastrar-senha")
    public ResponseEntity<Professor> cadastrarSenha(@RequestBody @Valid SenhaRequest request) {
        try {
            Professor atualizado = professorService.cadastrarSenha(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/recuperar-senha/{email}")
    public ResponseEntity<String> recuperarSenha(@PathVariable String email) {
        try {
            String senha = professorService.recuperarSenha(email);
            return ResponseEntity.ok(senha);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable Long id, @RequestBody @Valid Professor professor) {
        try {
            Professor atualizado = professorService.atualizarProfessor(id, professor);
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
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        try {
            boolean deletado = professorService.deletarProfessor(id);
            if (deletado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public static class SenhaRequest {

        private String email;
        private String senha;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

    }

}
