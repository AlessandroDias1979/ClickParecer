package CRUDparecer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import CRUDparecer.model.Professor;
import CRUDparecer.repository.ProfessorRepository;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> getAllProfessores() {
        return professorRepository.findAll();
    }

    public Professor getProfessorById(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    public Professor getProfessorByEmail(String email) {
        return professorRepository.findByEmail(email).orElse(null);
    }

    public Professor cadastrarProfessor(Professor professor) {
        if (professor == null || professor.getEmail() == null) {
            throw new IllegalArgumentException("Professor ou email inválido");
        }
        if (professorRepository.existsByEmail(professor.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        return professorRepository.save(professor);
    }

    public Professor cadastrarSenha(String email, String senha) {
        Optional<Professor> optional = professorRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Professor não encontrado com o email informado");
        }

        Professor professor = optional.get();
        professor.setSenha(senha);
        return professorRepository.save(professor);
    }

    public String recuperarSenha(String email) {
        Optional<Professor> optional = professorRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Professor não encontrado com o email informado");
        }

        Professor professor = optional.get();
        return "Senha recuperada: " + professor.getSenha();
    }

    public Professor atualizarProfessor(Long id, Professor professor) {
        Optional<Professor> optional = professorRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Professor não encontrado com o ID informado");
        }

        Professor existente = optional.get();
        existente.setEmail(professor.getEmail());
        existente.setSenha(professor.getSenha());
        return professorRepository.save(existente);
        
    }

    public boolean deletarProfessor(Long id) {
        if (!professorRepository.existsById(id)) {
            return false;
        }
        professorRepository.deleteById(id);
        return true;
    }

}
