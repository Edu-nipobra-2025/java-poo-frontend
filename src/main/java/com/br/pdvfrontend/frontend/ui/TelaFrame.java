package com.br.pdvfrontend.frontend.ui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class TelaFrame extends JFrame {

    private JButton acessoClienteButton;
    private JButton acessoFuncionárioButton;
    private JButton sairButton;

    public TelaFrame() {
        super("Posto PDV - Tela de Acesso");

        acessoClienteButton = new JButton("Acesso (Cliente)");
        acessoFuncionárioButton = new JButton("Acesso (Funcionário)");
        sairButton = new JButton("Sair");

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        painel.add(acessoClienteButton);
        painel.add(acessoFuncionárioButton);
        painel.add(sairButton);

        this.add(painel, BorderLayout.CENTER);

        configurarAcoes();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void configurarAcoes() {

        acessoClienteButton.addActionListener(e -> {
            System.out.println("-> Botão Cliente Clicado.");
            JOptionPane.showMessageDialog(this,
                    "Abrindo interface do Cliente.",
                    "Acesso Cliente",
                    JOptionPane.INFORMATION_MESSAGE);
        });


        acessoFuncionárioButton.addActionListener(e -> {
            System.out.println("-> Botão Funcionário Clicado.");
            JOptionPane.showMessageDialog(this,
                    "Solicitando autenticação de Funcionário.",
                    "Acesso Funcionário",
                    JOptionPane.WARNING_MESSAGE);
        });


        sairButton.addActionListener(e -> {
            System.out.println("-> Botão Sair Clicado.");

            int escolha = JOptionPane.showConfirmDialog(this,
                    "Deseja fechar a aplicação?",
                    "Confirmar Saída",
                    JOptionPane.YES_NO_OPTION);

            if (escolha == JOptionPane.YES_OPTION) {
                System.out.println("Aplicação encerrada.");
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaFrame();
        });
    }
}