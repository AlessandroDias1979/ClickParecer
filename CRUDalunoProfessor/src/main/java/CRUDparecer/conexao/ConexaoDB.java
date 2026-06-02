package CRUDparecer.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    
    private static final String URL = "jdbc:mysql://avaliacaotcc.mysql.dbaas.com.br:3306/avaliacaotcc";
    private static final String USER = "avaliacaotcc";
    private static final String PASSWORD = "Avaliacao@TCC2";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection conexao;
    
    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Class.forName(DRIVER);
                
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                
                System.out.println(" Conexão com MySQL estabelecida com sucesso!");
                
            } catch (ClassNotFoundException e) {
                System.err.println(" Driver MySQL não encontrado: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println(" Erro ao conectar ao banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return conexao;
    }
    
    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
                System.out.println("✓ Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("✗ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
    
    public static boolean isConectado() {
        try {
            return conexao != null && !conexao.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
