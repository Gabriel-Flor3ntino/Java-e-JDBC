package br.com.jdbc.view;

import java.awt.Container;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import br.com.jdbc.controller.CategoriaController;
import br.com.jdbc.controller.ProdutoController;
import br.com.jdbc.modelo.Categoria;
import br.com.jdbc.modelo.Produto;

public class ProdutoCategoriaFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField textoNome, textoDescricao;
    private JComboBox<Categoria> comboCategoria;
    private JTable tabela;
    private DefaultTableModel modelo;

    private ProdutoController produtoController;
    private CategoriaController categoriaController;

    public ProdutoCategoriaFrame() {
        super("Produtos");

        this.produtoController = new ProdutoController();
        this.categoriaController = new CategoriaController();

        Container container = getContentPane();
        container.setLayout(null);

        // Labels
        JLabel labelNome = new JLabel("Nome do Produto");
        JLabel labelDescricao = new JLabel("Descrição do Produto");
        JLabel labelCategoria = new JLabel("Categoria");

        labelNome.setBounds(10, 5, 200, 20);
        labelDescricao.setBounds(10, 45, 200, 20);
        labelCategoria.setBounds(10, 85, 200, 20);

        container.add(labelNome);
        container.add(labelDescricao);
        container.add(labelCategoria);

        // Campos
        textoNome = new JTextField();
        textoDescricao = new JTextField();
        comboCategoria = new JComboBox<>();

        textoNome.setBounds(10, 25, 250, 20);
        textoDescricao.setBounds(10, 65, 250, 20);
        comboCategoria.setBounds(10, 105, 250, 20);

        container.add(textoNome);
        container.add(textoDescricao);
        container.add(comboCategoria);

        // Carregar categorias
        comboCategoria.addItem(new Categoria(0, "Selecione"));
        for (Categoria c : listarCategoria()) {
            comboCategoria.addItem(c);
        }

        // Botões
        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoLimpar = new JButton("Limpar");
        JButton botaoEditar = new JButton("Alterar");
        JButton botaoApagar = new JButton("Excluir");

        botaoSalvar.setBounds(10, 140, 100, 25);
        botaoLimpar.setBounds(120, 140, 100, 25);
        botaoEditar.setBounds(10, 500, 100, 25);
        botaoApagar.setBounds(120, 500, 100, 25);

        container.add(botaoSalvar);
        container.add(botaoLimpar);
        container.add(botaoEditar);
        container.add(botaoApagar);

        // Tabela
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Descrição");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 180, 760, 300);

        container.add(scroll);

        // Eventos
        botaoSalvar.addActionListener(e -> {
            salvar();
            atualizarTabela();
        });

        botaoLimpar.addActionListener(e -> limpar());

        botaoEditar.addActionListener(e -> {
            alterar();
            atualizarTabela();
        });

        botaoApagar.addActionListener(e -> {
            deletar();
            atualizarTabela();
        });

        // Inicialização
        atualizarTabela();

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void salvar() {
        if (textoNome.getText().isEmpty() || textoDescricao.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha nome e descrição");
            return;
        }

        Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

        Produto produto = new Produto(
                textoNome.getText(),
                textoDescricao.getText()
        );

        produto.setCategoriaId(categoria.getId());

        produtoController.salvar(produto);

        JOptionPane.showMessageDialog(this, "Salvo com sucesso");
        limpar();
    }

    private void alterar() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha");
            return;
        }

        Integer id = (Integer) modelo.getValueAt(linha, 0);
        String nome = (String) modelo.getValueAt(linha, 1);
        String descricao = (String) modelo.getValueAt(linha, 2);

        produtoController.alterar(nome, descricao, id);
    }

    private void deletar() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha");
            return;
        }

        Integer id = (Integer) modelo.getValueAt(linha, 0);

        produtoController.deletar(id);
    }

    private void atualizarTabela() {
        limparTabela();

        List<Produto> produtos = listarProduto();

        for (Produto p : produtos) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getDescricao()
            });
        }
    }

    private void limparTabela() {
        modelo.setRowCount(0);
    }

    private void limpar() {
        textoNome.setText("");
        textoDescricao.setText("");
        comboCategoria.setSelectedIndex(0);
    }

    private List<Produto> listarProduto() {
        return produtoController.listar();
    }

    private List<Categoria> listarCategoria() {
        return categoriaController.listar();
    }
}