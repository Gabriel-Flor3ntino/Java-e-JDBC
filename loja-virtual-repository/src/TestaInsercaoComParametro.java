import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection connection = factory.recuperarConexa()) {

			connection.setAutoCommit(false);

			try (PreparedStatement stm =
					connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
					) {

				adicionarVarial("SmartTv", "45 polegadas", stm);
				adicionarVarial("Radio", "Radio de bateria", stm);

				connection.commit();

				stm.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				IO.println("ROLLBACK EXECUTADO");
				connection.rollback();
			}
		}
	}

	private static void adicionarVarial(String nome, String descricao, PreparedStatement stm) throws SQLException {
		stm.setString(1, nome);
		stm.setString(2, descricao);

		if (nome.equals("Radio")) {
			throw new RuntimeException("Não foi possível adicionar produto");
		}

		stm.execute();

		try (ResultSet rst = stm.getGeneratedKeys()) {
			while (rst.next()) {
				Integer id = rst.getInt(1);
				IO.println("O id criado foi: " + id);

			}
		}
	}
}
