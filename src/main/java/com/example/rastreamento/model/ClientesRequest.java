package com.example.rastreamento.model;

import java.util.UUID;

public class ClientesRequest {

    private UUID id;
    private String bairro;
    private String cidade;
    private String nome;
    private int numero_logradouro;
    private String rua;
    private int telefone;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero_logradouro() {
        return numero_logradouro;
    }

    public void setNumero_logradouro(int numero_logradouro) {
        this.numero_logradouro = numero_logradouro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

}

