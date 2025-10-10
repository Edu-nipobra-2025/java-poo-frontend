package br.com.mock;

import br.com.model.Produto;
import java.util.ArrayList;
import java.util.List;

public class ProdutoMock {

    private static List<Produto> produtos = new ArrayList<>();

    public static void init() {
        produtos.add(new Produto("Gasolina Comum", "GCOM", "Posto Ipiranga", "Ipiranga"));
        produtos.add(new Produto("Gasolina Aditivada", "GADT", "Posto Ipiranga", "Ipiranga"));
        produtos.add(new Produto("Etanol", "ETAN", "Posto Ipiranga", "Ipiranga"));
        produtos.add(new Produto("Diesel S10", "DS10", "Posto Ipiranga", "Ipiranga"));
    }

    public static List<Produto> getAll() {
        return produtos;
    }

    public static void add(Produto produto) {
        produtos.add(produto);
    }

    public static void update(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getReferencia().equals(produto.getReferencia())) {
                produtos.set(i, produto);
                break;
            }
        }
    }

    public static void remove(String referencia) {
        produtos.removeIf(produto -> produto.getReferencia().equals(referencia));
    }
}
