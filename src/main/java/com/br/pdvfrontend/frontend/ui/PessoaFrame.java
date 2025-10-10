package com.br.pdvfrontend.frontend.ui;

import com.br.pdvfrontend.api.dto.PessoaRequest;
import com.br.pdvfrontend.api.dto.PessoaResponse;
import com.br.pdvfrontend.enums.TipoPessoa;
import com.br.pdvfrontend.frontend.service.PessoaHttpClient;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class PessoaFrame extends JFrame {

    private final PessoaHttpClient pessoaHttpClient;
    private final JTable tabelaPessoas;
    private final PessoaTableModel tableModel;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PessoaFrame(PessoaHttpClient pessoaHttpClient) {
        this.pessoaHttpClient = pessoaHttpClient;

        // 1. Configuração Básica
        setTitle("Gerenciamento de Pessoas (CRUD) - Front-end Swing");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 2. Tabela de Dados e Modelo
        this.tableModel = new PessoaTableModel();
        this.tabelaPessoas = new JTable(tableModel);
        this.tabelaPessoas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabelaPessoas), BorderLayout.CENTER);

        // 3. Painel de Ações (Botões)
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnListar = new JButton("🔄 Atualizar Lista");
        JButton btnNovo = new JButton("➕ Novo");
        JButton btnEditar = new JButton("✏️ Editar");
        JButton btnDeletar = new JButton("🗑️ Deletar");

        panelBotoes.add(btnListar);
        panelBotoes.add(btnNovo);
        panelBotoes.add(btnEditar);
        panelBotoes.add(btnDeletar);
        add(panelBotoes, BorderLayout.SOUTH);

        // 4. Listeners (Eventos)
        btnListar.addActionListener(e -> carregarPessoas());
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarPessoaSelecionada());
        btnDeletar.addActionListener(e -> deletarPessoaSelecionada());

        carregarPessoas();
    }

    // --- Métodos de Controle ---

    private void carregarPessoas() {
        // Usa uma nova Thread para a chamada HTTP (bloqueante)
        new Thread(() -> {
            try {
                final List<PessoaResponse> pessoas = pessoaHttpClient.listarTodos();
                // Retorna à Event Dispatch Thread (EDT) para atualizar a UI
                SwingUtilities.invokeLater(() -> tableModel.setDados(pessoas));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> mostrarMensagemErro("Erro ao carregar dados: " + getErrorMessage(ex)));
            }
        }).start();
    }

    private void editarPessoaSelecionada() {
        int linha = tabelaPessoas.getSelectedRow();
        if (linha != -1) {
            abrirFormulario(tableModel.getPessoaAt(linha));
        } else {
            mostrarMensagemErro("Selecione uma pessoa para editar.");
        }
    }

    private void deletarPessoaSelecionada() {
        int linha = tabelaPessoas.getSelectedRow();
        if (linha != -1) {
            PessoaResponse pessoa = tableModel.getPessoaAt(linha);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja deletar " + pessoa.nomeCompleto() + " (ID: " + pessoa.id() + ")?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                new Thread(() -> {
                    try {
                        pessoaHttpClient.deletarPessoa(pessoa.id());
                        SwingUtilities.invokeLater(() -> {
                            mostrarMensagemSucesso("Pessoa deletada com sucesso!");
                            carregarPessoas();
                        });
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> mostrarMensagemErro("Erro ao deletar: " + getErrorMessage(ex)));
                    }
                }).start();
            }
        } else {
            mostrarMensagemErro("Selecione uma pessoa para deletar.");
        }
    }

    private void abrirFormulario(PessoaResponse pessoaExistente) {
        // --- Componentes do Formulário ---
        JTextField nomeField = new JTextField(20);
        JTextField cpfCnpjField = new JTextField(20);
        JTextField ctpsField = new JTextField(20);
        JTextField dataNascimentoField = new JTextField(20);
        JComboBox<TipoPessoa> tipoPessoaCombo = new JComboBox<>(TipoPessoa.values());

        // Preencher campos se for edição
        if (pessoaExistente != null) {
            nomeField.setText(pessoaExistente.nomeCompleto());
            cpfCnpjField.setText(pessoaExistente.cpfCnpj());
            ctpsField.setText(pessoaExistente.numeroCtps() != null ? pessoaExistente.numeroCtps().toString() : "");
            dataNascimentoField.setText(pessoaExistente.dataNascimento().format(dateFormatter));
            tipoPessoaCombo.setSelectedItem(pessoaExistente.tipoPessoa());
        } else {
            // Valor padrão para data em novo registro
            dataNascimentoField.setText(LocalDate.now().format(dateFormatter));
        }

        // --- Layout do Painel ---
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nome Completo:"));
        panel.add(nomeField);
        panel.add(new JLabel("CPF/CNPJ:"));
        panel.add(cpfCnpjField);
        panel.add(new JLabel("Nº CTPS (Opcional):"));
        panel.add(ctpsField);
        panel.add(new JLabel("Data Nasc. (dd/MM/yyyy):"));
        panel.add(dataNascimentoField);
        panel.add(new JLabel("Tipo de Pessoa:"));
        panel.add(tipoPessoaCombo);

        // --- Exibir e Capturar Ação ---
        int result = JOptionPane.showConfirmDialog(this, panel,
                pessoaExistente == null ? "Nova Pessoa" : "Editar Pessoa (ID: " + pessoaExistente.id() + ")",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            processarFormulario(pessoaExistente, nomeField, cpfCnpjField, ctpsField, dataNascimentoField, tipoPessoaCombo);
        }
    }

    private void processarFormulario(PessoaResponse pessoaExistente, JTextField nomeField, JTextField cpfCnpjField, JTextField ctpsField, JTextField dataNascimentoField, JComboBox<TipoPessoa> tipoPessoaCombo) {
        try {
            // Conversão e Validação básica
            String nome = nomeField.getText().trim();
            String cpfCnpj = cpfCnpjField.getText().trim();
            Long ctps = ctpsField.getText().trim().isEmpty() ? null : Long.parseLong(ctpsField.getText().trim());
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoField.getText().trim(), dateFormatter);
            TipoPessoa tipoPessoa = (TipoPessoa) tipoPessoaCombo.getSelectedItem();

            if (nome.isEmpty() || cpfCnpj.isEmpty()) {
                throw new IllegalArgumentException("Nome e CPF/CNPJ são campos obrigatórios.");
            }

            PessoaRequest request = new PessoaRequest(nome, cpfCnpj, ctps, dataNascimento, tipoPessoa);

            // Chamada ao serviço em nova thread
            new Thread(() -> {
                try {
                    PessoaResponse response;
                    if (pessoaExistente == null) {
                        response = pessoaHttpClient.criarPessoa(request);
                        SwingUtilities.invokeLater(() -> mostrarMensagemSucesso("Pessoa criada (ID: " + response.id() + ")!"));
                    } else {
                        response = pessoaHttpClient.atualizarPessoa(pessoaExistente.id(), request);
                        SwingUtilities.invokeLater(() -> mostrarMensagemSucesso("Pessoa atualizada (ID: " + response.id() + ")!"));
                    }
                    SwingUtilities.invokeLater(this::carregarPessoas);
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> mostrarMensagemErro("Falha na operação: " + getErrorMessage(ex)));
                }
            }).start();

        } catch (NumberFormatException | DateTimeParseException e) {
            mostrarMensagemErro("Erro de formato. Verifique CTPS (número) e Data (dd/MM/yyyy).");
        } catch (IllegalArgumentException e) {
            mostrarMensagemErro(e.getMessage());
        } catch (Exception e) {
            mostrarMensagemErro("Erro inesperado: " + e.getMessage());
        }
    }

    // --- Métodos Utilitários ---

    private String getErrorMessage(Exception ex) {
        // Tenta extrair uma mensagem de erro mais legível do corpo da resposta HTTP
        String msg = ex.getMessage();
        if (msg != null && msg.contains("400 Bad Request")) {
            return "Falha na validação dos dados. Verifique campos obrigatórios e formato (CPF/CNPJ).";
        }
        return msg;
    }

    private void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensagemSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}