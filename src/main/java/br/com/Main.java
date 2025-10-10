package br.com;

import br.com.view.ProdutoView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProdutoView view = new ProdutoView();
            view.setVisible(true);
        });
    }
}
