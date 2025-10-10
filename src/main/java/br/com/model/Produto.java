package br.com.model;

public class Produto {

    //atributos
    private String nome;
    private String referencia;
    private String fornecedor;
    private String marca;

    //construtor
    public  Produto(String nome, String referencia, String fornecedor, String marca){
        this.nome = nome;
        this.referencia = referencia;
        this.fornecedor = fornecedor;
        this.marca = marca;
    }

    //getters
    public String getNome(){
        return nome;
    }
    public String getReferencia(){
        return referencia;
    }
    public String getFornecedor(){
        return fornecedor;
    }
    public String getMarca(){
        return marca;
    }

    //setters
    public void setNome(String nome){
        this.nome = nome;
    }

    public void setReferencia(String referencia){
        this.referencia = referencia;
    }
    public void setFornecedor(String fornecedor){
        this.fornecedor = fornecedor;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }
}