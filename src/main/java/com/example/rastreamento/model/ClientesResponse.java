package com.example.rastreamento.model;

import org.springframework.data.cassandra.core.mapping.Column;

import java.util.UUID;

public class ClientesResponse {

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

    public ClientesResponse withBuilderId(UUID id){
        setId(id);
        return this;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public ClientesResponse withBuilderBairro(String bairro){
        setBairro(bairro);
        return this;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public ClientesResponse withBuilderCidade(String cidade){
        setCidade(cidade);
        return this;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ClientesResponse withBuilderNome(String nome){
        setNome(nome);
        return this;
    }


    public int getNumero_logradouro() {
        return numero_logradouro;
    }

    public void setNumero_logradouro(int numero_logradouro) {
        this.numero_logradouro = numero_logradouro;
    }

    public ClientesResponse withBuilderNumero_logradouro(int numero_logradouro){
        setNumero_logradouro(numero_logradouro);
        return this;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public ClientesResponse withBuilderRua(String rua){
        setRua(rua);
        return this;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public ClientesResponse withBuilderTelefone(int telefone){
        setTelefone(telefone);
        return this;
    }
}
