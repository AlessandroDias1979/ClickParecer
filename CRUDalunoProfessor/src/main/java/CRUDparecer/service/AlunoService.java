package CRUDparecer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import CRUDparecer.model.Aluno;
import CRUDparecer.repository.AlunoRepository;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> getAllAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno getAlunoById(int id) {
        return alunoRepository.findById(id).orElse(null);
    }

    public List<Aluno> getAlunoByTurma(String turma) {
        return alunoRepository.findByTurma(turma);
    }

    public Aluno cadastrarAluno(Aluno aluno) {
        if (aluno == null || aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Aluno ou nome inválido");
        }
        if (aluno.getTurma() == null || aluno.getTurma().trim().isEmpty()) {
            throw new IllegalArgumentException("Turma é obrigatória");
        }
        return alunoRepository.save(aluno);
    }

    public Aluno atualizarAluno(int id, Aluno aluno) {
        Aluno existente = alunoRepository.findById(id).orElse(null);
        if (existente == null) {
            throw new IllegalArgumentException("Aluno não encontrado com o ID informado");
        }
        existente.setNome(aluno.getNome());
        existente.setTurma(aluno.getTurma());
        return alunoRepository.save(existente);
    }

    public boolean deletarAluno(int id) {
        if (!alunoRepository.existsById(id)) {
            return false;
        }
        alunoRepository.deleteById(id);
        return true;
    }
}