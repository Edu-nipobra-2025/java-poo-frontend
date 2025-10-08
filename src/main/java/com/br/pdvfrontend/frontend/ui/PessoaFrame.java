package com.br.pdv.frontend.ui;

import com.br.pdv.frontend.domain.entity.Pessoa;
import com.br.pdv.frontend.domain.service.PessoaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PessoaFrame extends JFrame {

    private final PessoaService service;
    private JTable table;
    private DefaultTableModel tableModel;

    public PessoaFrame(PessoaService service) {
        this.service = service;
        setTitle("CRUD Pessoas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        loadTableData();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF/CNPJ", "CTPS", "Nascimento", "Tipo"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadTableData());

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(e -> deleteSelected());

        JPanel panel = new JPanel();
        panel.add(btnRefresh);
        panel.add(btnDelete);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // Limpa tabela
        List<Pessoa> pessoas = service.findAll();
        for (Pessoa p : pessoas) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNomeCompleto(),
                    p.getCpfCnpj(),
                    p.getNumeroCtps(),
                    p.getDataNascimento(),
                    p.getTipoPessoa()
            });
        }
    }

    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) table.getValueAt(selectedRow, 0);
            service.delete(id);
            loadTableData();
            JOptionPane.showMessageDialog(this, "Pessoa deletada!");
        }
    }

    public static void open(PessoaService service) {
        SwingUtilities.invokeLater(() -> {
            PessoaFrame frame = new PessoaFrame(service);
            frame.setVisible(true);
        });
    }
}
