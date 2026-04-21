package br.com.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.jdbc.dao.CategoriaDAO;
import br.com.jdbc.modelo.Categoria;
import br.com.jdbc.modelo.Produto;

public class TestaListagemDeCategoria {

	public static void main(String[] args) throws SQLException {

		try (Connection connection = new ConnectionFactory().recuperarConexao()) {
			CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
			List<Categoria> listaDeCategoria = categoriaDAO.listarComProdutos();
			listaDeCategoria.stream().forEach(ct -> {
				IO.println(ct.getNome());

				for (Produto produto : ct.getProdutos()) {
					IO.println(ct.getNome() + " - " + produto.getNome());
				}
			});
		}
	}
}
