package br.com.jdbc;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.jdbc.dao.ProdutoDAO;
import br.com.jdbc.modelo.Produto;

public class TestaInsercaoComProduto {

	public static void main(String[] args) throws SQLException {

		Produto comoda = new Produto("Cômoda", "Cômoda Vertical");

		try (Connection connection = new ConnectionFactory().recuperarConexao()) {
			ProdutoDAO produtoDao = new ProdutoDAO(connection);
			produtoDao.salvar(comoda);
			//Lista = persistenciaProduto.listar();
			
		}
	}
}
