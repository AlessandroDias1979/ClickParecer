package CRUDparecer.exemplo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import CRUDparecer.conexao.ConexaoDB;

/**
 * Exemplo de como usar a conexão com o banco de dados
 */
public class ExemploConexao {
    
    /**
     * Exemplo 1: Usando conexão direta (JDBC puro)
     */
    public static void exemploConexaoDireta() {
        Connection conexao = ConexaoDB.getConexao();
        
        if (conexao != null) {
            try {
                // Criar um statement
                Statement stmt = conexao.createStatement();
                
                // Executar uma query
                ResultSet rs = stmt.executeQuery("SELECT * FROM aluno");
                
                // Processar os resultados
                while (rs.next()) {
                    System.out.println("ID: " + rs.getLong("id"));
                    System.out.println("Nome: " + rs.getString("nome"));
                    System.out.println("Turma: " + rs.getString("turma"));
                    System.out.println("---");
                }
                
                // Fechar recursos
                rs.close();
                stmt.close();
                
            } catch (SQLException e) {
                System.err.println("Erro ao executar query: " + e.getMessage());
            }
        }
    }
    
    /**
     * Exemplo 2: Usar nos Controllers/Services (Recomendado)
     * 
     * Com Spring Data JPA, você não precisa gerenciar a conexão manualmente.
     * As repositories fazem isso automaticamente:
     * 
     * @Autowired
     * private AlunoRepository alunoRepository;
     * 
     * public List<Aluno> buscarTodos() {
     *     return alunoRepository.findAll();
     * }
     */
    
    public static void main(String[] args) {
        System.out.println("=== Testando Conexão com Banco de Dados ===\n");
        
        // Testar conexão
        Connection conexao = ConexaoDB.getConexao();
        
        if (ConexaoDB.isConectado()) {
            System.out.println("Status: ✓ Conectado ao banco de dados\n");
            exemploConexaoDireta();
        } else {
            System.out.println("Status: ✗ Falha na conexão\n");
        }
        
        // Fechar conexão quando terminar
        ConexaoDB.fecharConexao();
    }
}
