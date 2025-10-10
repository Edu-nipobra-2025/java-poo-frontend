package br.com.view;

import br.com.model.Produto;
import br.com.mock.ProdutoMock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProdutoView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton newButton, saveButton, deleteButton;
    private JTextField nomeField, referenciaField, fornecedorField, marcaField;

    public ProdutoView() {
        setTitle("Cadastro de Produtos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadProducts();
    }

    private void initComponents() {
        // Table
        tableModel = new DefaultTableModel(new Object[]{"Nome", "Referência", "Fornecedor", "Marca"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Referência:"));
        referenciaField = new JTextField();
        formPanel.add(referenciaField);

        formPanel.add(new JLabel("Fornecedor:"));
        fornecedorField = new JTextField();
        formPanel.add(fornecedorField);

        formPanel.add(new JLabel("Marca:"));
        marcaField = new JTextField();
        formPanel.add(marcaField);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        newButton = new JButton("Novo");
        saveButton = new JButton("Salvar");
        deleteButton = new JButton("Excluir");
        buttonPanel.add(newButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);

        // Add panels to frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        newButton.addActionListener(e -> clearFields());
        saveButton.addActionListener(e -> saveProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                nomeField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                referenciaField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                fornecedorField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                marcaField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                referenciaField.setEditable(false); // Cannot edit reference
            }
        });
    }

    private void loadProducts() {
        ProdutoMock.init();
        List<Produto> produtos = ProdutoMock.getAll();
        tableModel.setRowCount(0); // Clear existing data
        for (Produto produto : produtos) {
            tableModel.addRow(new Object[]{produto.getNome(), produto.getReferencia(), produto.getFornecedor(), produto.getMarca()});
        }
    }

    private void clearFields() {
        nomeField.setText("");
        referenciaField.setText("");
        fornecedorField.setText("");
        marcaField.setText("");
        referenciaField.setEditable(true);
        table.clearSelection();
    }

    private void saveProduct() {
        String nome = nomeField.getText();
        String referencia = referenciaField.getText();
        String fornecedor = fornecedorField.getText();
        String marca = marcaField.getText();

        if (nome.isEmpty() || referencia.isEmpty() || fornecedor.isEmpty() || marca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Produto produto = new Produto(nome, referencia, fornecedor, marca);

        // Check if it's an update or a new product
        boolean isUpdate = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 1).equals(referencia)) {
                isUpdate = true;
                break;
            }
        }

        if (isUpdate) {
            ProdutoMock.update(produto);
        } else {
            // Check if reference already exists
            for (Produto p : ProdutoMock.getAll()) {
                if (p.getReferencia().equals(referencia)) {
                    JOptionPane.showMessageDialog(this, "Referência já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            ProdutoMock.add(produto);
        }

        loadProducts();
        clearFields();
    }

    private void deleteProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String referencia = tableModel.getValueAt(selectedRow, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o produto?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ProdutoMock.remove(referencia);
            loadProducts();
            clearFields();
        }
    }
}
