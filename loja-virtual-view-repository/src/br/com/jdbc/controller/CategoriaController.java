package br.com.jdbc.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.jdbc.dao.CategoriaDAO;
import br.com.jdbc.factory.ConnectionFactory;
import br.com.jdbc.modelo.Categoria;

public class CategoriaController {
	
	private CategoriaDAO categoriaDAO;
	
	public CategoriaController() {
		Connection connection =
				new ConnectionFactory().recuperarConexao(); 
		this.categoriaDAO =  new CategoriaDAO(connection);
	}
	
	public List<Categoria> listar() {
		return this.categoriaDAO.listar();
	}
}
