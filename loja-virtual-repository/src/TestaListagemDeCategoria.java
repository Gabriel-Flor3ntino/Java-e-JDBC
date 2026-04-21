import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.jdbc.ConnectionFactory;
import br.com.jdbc.dao.CategoriaDAO;
import br.com.jdbc.modelo.Categoria;

public class TestaListagemDeCategoria {

	public static void main(String[] args) throws SQLException {
		
		try (Connection connection = new ConnectionFactory().recuperarConexao()) {
			CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
			List<Categoria> listaDeCategoria = categoriaDAO.listar();
			listaDeCategoria.stream().forEach(ct -> IO.println(ct.getNome()));
			
		}
		

	}

}
