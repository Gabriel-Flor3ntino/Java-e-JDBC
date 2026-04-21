package br.com.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import br.com.jdbc.modelo.Categoria;
import br.com.jdbc.modelo.Produto;

public class CategoriaDAO {

	private Connection connection;

	public CategoriaDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Produto produto) throws SQLException {
		String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES (?, ?)";

		try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstm.setString(1, produto.getNome());
			pstm.setString(2, produto.getDescricao());

			pstm.execute();

			try (ResultSet rst = pstm.getGeneratedKeys()) {
				while (rst.next()) {
					produto.setId(rst.getInt(1));
				}
			}
		}
	}

	public void salvarComCategoria(Produto produto) {
		try {
			String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, CATEGORIA_ID) VALUES (?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				pstm.setString(1, produto.getNome());
				pstm.setString(2, produto.getDescricao());
				pstm.setInt(3, produto.getCategoriaId());

				pstm.execute();

				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						produto.setId(rst.getInt(1));
					}
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Categoria> listar() {
		try {
			List<Categoria> categorias = new ArrayList<>();
			String sql = "SELECT ID, NOME FROM CATEGORIA";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));

						categorias.add(categoria);
					}
				}
			}
			return categorias;
		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}

	public List<Categoria> listarComProdutos() {
		try {
			Categoria ultima = null;
			List<Categoria> categorias = new ArrayList<>();

			IO.println("Executando a query de listar categoria");

			String sql = "SELECT C.ID, C.NOME, P.ID, P.NOME, P.DESCRICAO FROM CATEGORIA C INNER JOIN"
					+ " PRODUTO P ON C.ID = P.CATEGORIA_ID";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						if (ultima == null || !ultima.getNome().equals(rst.getString(2))) {
							Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));

							ultima = categoria;
							categorias.add(categoria);
						}
						Produto produto = new Produto(rst.getInt(3), rst.getString(4), rst.getString(5));
						ultima.adicionar(produto);
					}
				}
			}
			return categorias;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
