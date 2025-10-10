package com.br.pdvfrontend.frontend.ui;

import com.br.pdvfrontend.api.dto.PessoaResponse;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de Tabela Swing para exibir dados de PessoaResponse na JTable.
 */
public class PessoaTableModel extends AbstractTableModel {

    private final String[] colunas = {"ID", "Nome Completo", "CPF/CNPJ", "CTPS", "Nascimento", "Tipo"};
    private List<PessoaResponse> dados = new ArrayList<>();

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PessoaResponse pessoa = dados.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> pessoa.id();
            case 1 -> pessoa.nomeCompleto();
            case 2 -> pessoa.cpfCnpj();
            case 3 -> pessoa.numeroCtps() != null ? pessoa.numeroCtps() : "-";
            case 4 -> pessoa.dataNascimento();
            case 5 -> pessoa.tipoPessoa();
            default -> null;
        };
    }

    public void setDados(List<PessoaResponse> novosDados) {
        this.dados = novosDados;
        fireTableDataChanged();
    }

    public PessoaResponse getPessoaAt(int rowIndex) {
        return dados.get(rowIndex);
    }
}