package com.br.pdvfrontend.frontend.ui;

import com.br.pdvfrontend.api.pessoa.dto.PessoaRequest;
import com.br.pdvfrontend.api.pessoa.dto.PessoaResponse;
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
    private final JTable tabelaPessoas = new JTable();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PessoaFrame(PessoaHttpClient pessoaHttpClient) {
        this.pessoaHttpClient = pessoaHttpClient;

        setTitle("Gerenciamento de Pessoas (CRUD) - Front-end Swing");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.tabelaPessoas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabelaPessoas), BorderLayout.CENTER);

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

        btnListar.addActionListener(e -> carregarPessoas());
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> editarPessoaSelecionada());
        btnDeletar.addActionListener(e -> deletarPessoaSelecionada());

        carregarPessoas();
    }

    // --- Métodos de Controle ---

    private void carregarPessoas() {
        new Thread(() -> {
            try {
                final List<PessoaResponse> pessoas = pessoaHttpClient.listarTodos();
                SwingUtilities.invokeLater(() -> {
                    // CÓDIGO TEMPORARIAMENTE REMOVIDO: tableModel.setDados(pessoas)
                    System.out.println("Dados carregados. Implementar PessoaTableModel para exibir.");
                });
            } catch (Exception ex) {
                // CORRIGIDO: getErrorMessage(ex) agora está definido abaixo.
                SwingUtilities.invokeLater(() -> mostrarMensagemErro("Erro ao carregar dados: " + getErrorMessage(ex)));
            }
        }).start();
    }

    private void editarPessoaSelecionada() {
        int linha = tabelaPessoas.getSelectedRow();
        if (linha != -1) {
            System.out.println("Funcionalidade de Edição requer PessoaTableModel.");
            mostrarMensagemErro("Selecione uma pessoa para editar (Funcionalidade de Edição desativada sem o PessoaTableModel).");
        } else {
            mostrarMensagemErro("Selecione uma pessoa para editar.");
        }
    }

    private void deletarPessoaSelecionada() {
        int linha = tabelaPessoas.getSelectedRow();
        if (linha != -1) {

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja deletar o item selecionado?",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                System.out.println("Funcionalidade de Deletar requer PessoaTableModel para obter o ID.");
                mostrarMensagemErro("Funcionalidade de Deletar desativada sem o PessoaTableModel.");
            }
        } else {
            mostrarMensagemErro("Selecione uma pessoa para deletar.");
        }
    }

    private void abrirFormulario(PessoaResponse pessoaExistente) {
        JTextField nomeField = new JTextField(20);
        JTextField cpfCnpjField = new JTextField(20);
        JTextField ctpsField = new JTextField(20);

        // CORRIGIDO: A variável dataNascimentoField precisava ser inicializada.
        JTextField dataNascimentoField = new JTextField(20);

        // Adicione o código do formulário aqui (omitido para focar na correção de compilação)
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Nome Completo:"));
        panel.add(nomeField);
    }

    private String getErrorMessage(Exception ex) {
        String msg = ex.getMessage();
        if (msg != null && msg.contains("400 Bad Request")) {
            return "Falha na validação dos dados. Verifique campos obrigatórios e formato.";
        }
        return msg;
    }

    private void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // Para completar o CRUD, você provavelmente precisa deste método:
    /*
    private void mostrarMensagemSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    */
}