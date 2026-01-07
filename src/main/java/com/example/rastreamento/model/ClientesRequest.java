package com.example.rastreamento.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

public class ClientesRequest {

    private UUID id;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O número do logradouro não pode ser nulo")
    @Positive(message = "O número deve ser maior que zero")
    private Integer numero_logradouro; // Alterado de int para Integer para aceitar @NotNull

    @NotBlank(message = "A rua é obrigatória")
    private String rua;

    @NotNull(message = "O telefone não pode ser nulo")
    private Integer telefone; // Alterado de int para Integer para aceitar @NotNull


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getNumero_logradouro() { return numero_logradouro; }
    public void setNumero_logradouro(Integer numero_logradouro) { this.numero_logradouro = numero_logradouro; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public Integer getTelefone() { return telefone; }
    public void setTelefone(Integer telefone) { this.telefone = telefone; }
}