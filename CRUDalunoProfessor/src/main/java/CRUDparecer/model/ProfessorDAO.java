package CRUDparecer.model;

public class ProfessorDAO {

    public void cadastrarSenha(String email, String senha) {
        System.out.println("Cadastrando senha para o email: " + email);
    }

    public String recuperarSenha(String email) {
        System.out.println("Recuperando senha para o email: " + email);
        return "senha_exemplo";
    }
}