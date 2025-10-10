package com.br.pdvfrontend;

import com.br.pdvfrontend.frontend.ui.PessoaFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication(scanBasePackages = "com.br.pdvfrontend")
public class PdvFrontendApplication {

    public static void main(String[] args) {
        // Inicializa o Spring Context como aplicação Desktop (sem servidor web)
        ConfigurableApplicationContext context = new SpringApplicationBuilder(PdvFrontendApplication.class)
                .headless(false)
                .web(org.springframework.boot.WebApplicationType.NONE) // Desativa o servidor web
                .run(args);

        // Cria e exibe a UI na Thread de Eventos do Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Não foi possível configurar Look and Feel: " + e.getMessage());
            }

            // Pega a janela principal do contexto Spring
            PessoaFrame pessoaFrame = context.getBean(PessoaFrame.class);
            pessoaFrame.setVisible(true);
        });
    }
}