package CRUDparecer.model;

public class AlunoDAO {

    // CREATE - cadastrar aluno
    public void cadastrarAluno(Aluno aluno) {
        System.out.println("Cadastrando aluno: " + aluno.getNome());
    }

    // UPDATE - editar aluno
    public void editarAluno(int id, String novoNome, String novaTurma) {
        System.out.println("Editando aluno ID: " + id);
        System.out.println("Novo nome: " + novoNome);
        System.out.println("Nova turma: " + novaTurma);
    }

    // DELETE - deletar aluno
    public void deletarAluno(int id) {
        System.out.println("Deletando aluno ID: " + id);
    }

    // READ - listar aluno (extra, bom pro professor ver)
    public void listarAlunos() {
        System.out.println("Listando alunos...");
    }
}