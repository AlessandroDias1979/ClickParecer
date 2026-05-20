package CRUDparecer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Aluno")
public class Aluno {

    @Id
    @Column(name = "idAluno")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Column(nullable = false, name = "NomeDoAluno")
    @NotBlank(message = "O nome do aluno é obrigatório")
    private String nome;

    @Column(nullable = false, name = "Turma")
    @NotBlank(message = "A turma é obrigatória")
    private String turma;

    public Aluno() {
    }

    public Aluno(String nome, String turma) {
        this.nome = nome;
        this.turma = turma;
    }

    // Getter
    public String getTurma() {
        return turma;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // Setter
    public void setTurma(String turma) {
        this.turma = turma;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Aluno: " + nome + " | Turma: " + turma;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((turma == null) ? 0 : turma.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aluno other = (Aluno) obj;
        if (turma == null) {
            if (other.turma != null)
                return false;
        } else if (!turma.equals(other.turma))
            return false;
        return true;
    }

}