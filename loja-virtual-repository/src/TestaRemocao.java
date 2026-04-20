import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaRemocao {
	
	public static void main(String[] args) throws SQLException{
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.recuperarConexa();
		
		Statement stm = connection.createStatement();
		stm.execute("DELETE FROM PRODUTO WHERE ID > 2");
		
		Integer linhasModifiadas = stm.getUpdateCount();
		
		IO.println("Quantidade de linhas que foram modificadas: " + linhasModifiadas);
	}
	
}
