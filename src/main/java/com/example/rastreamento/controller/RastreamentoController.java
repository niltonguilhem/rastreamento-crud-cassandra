package com.example.rastreamento.controller;

import com.example.rastreamento.model.Clientes;
import com.example.rastreamento.model.ClientesRequest;
import com.example.rastreamento.model.ClientesResponse;
import com.example.rastreamento.service.ClientesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/rastreamento")
public class RastreamentoController {

    private static final Logger logger = LoggerFactory.getLogger(RastreamentoController.class);
    @Autowired
    private ClientesService service;

    @GetMapping()
    public ResponseEntity<List<ClientesResponse>>getAllClientes() {
        logger.info("m=getAllClientes - status=start");
        List<Clientes> clientesList = service.findAllClientes();
        List<ClientesResponse> clientesResponseList = clientesList.stream()
                .map(clientes -> new ClientesResponse()
                .withBuilderId(clientes.getId())
                .withBuilderBairro(clientes.getBairro())
                .withBuilderCidade(clientes.getCidade())
                .withBuilderNome(clientes.getNome())
                .withBuilderNumero_logradouro(clientes.getNumero_logradouro())
                .withBuilderRua(clientes.getRua())
                .withBuilderTelefone(clientes.getTelefone())).collect(Collectors.toList());
        logger.info("m=getAllClientes - status=finish");
        return new ResponseEntity<>(clientesResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientesResponse> getIdCliente(@PathVariable("id") UUID id) {
        logger.info("m=getIdCliente - status=start " + id);
        Clientes clientes = service.getClientesById(id);
        ClientesResponse response = new ClientesResponse()
                .withBuilderId(clientes.getId())
                .withBuilderBairro(clientes.getBairro())
                .withBuilderCidade(clientes.getCidade())
                .withBuilderNome(clientes.getNome())
                .withBuilderNumero_logradouro(clientes.getNumero_logradouro())
                .withBuilderRua(clientes.getRua())
                .withBuilderTelefone(clientes.getTelefone());
        logger.info("m=getIdCliente - status=finish " + id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

   @PostMapping
    public ResponseEntity<ClientesResponse> postCarro(@RequestBody ClientesRequest clientesRequest){
       logger.info("m=postClientes - status=start");
        Clientes clientes = service.save(new Clientes()
                .withBuilderId(UUID.randomUUID())
                .withBuilderBairro(clientesRequest.getBairro())
                .withBuilderCidade(clientesRequest.getCidade())
                .withBuilderNome(clientesRequest.getNome())
                .withBuilderNumero_logradouro(clientesRequest.getNumero_logradouro())
                .withBuilderRua(clientesRequest.getRua())
                .withBuilderTelefone(clientesRequest.getTelefone()));

        ClientesResponse response = new ClientesResponse()
                .withBuilderId(clientes.getId())
                .withBuilderBairro(clientes.getBairro())
                .withBuilderCidade(clientes.getCidade())
                .withBuilderNome(clientes.getNome())
                .withBuilderNumero_logradouro(clientes.getNumero_logradouro())
                .withBuilderRua(clientes.getRua())
                .withBuilderTelefone(clientes.getTelefone());
       logger.info("m=postClientes - status=finish");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientesResponse> putClientes (@PathVariable("id")UUID id,
                                                   @RequestBody ClientesRequest clientesRequest){
        logger.info("m=putClientes - status=start " + id);
        Clientes clientesUpdate = service.update(new Clientes()
                .withBuilderId(id)
                .withBuilderBairro(clientesRequest.getBairro())
                .withBuilderCidade(clientesRequest.getCidade())
                .withBuilderNome(clientesRequest.getNome())
                .withBuilderNumero_logradouro(clientesRequest.getNumero_logradouro())
                .withBuilderRua(clientesRequest.getRua())
                .withBuilderTelefone(clientesRequest.getTelefone()));

        ClientesResponse response = new ClientesResponse()
                .withBuilderId(clientesUpdate.getId())
                .withBuilderBairro(clientesUpdate.getBairro())
                .withBuilderCidade(clientesUpdate.getCidade())
                .withBuilderNome(clientesUpdate.getNome())
                .withBuilderNumero_logradouro(clientesUpdate.getNumero_logradouro())
                .withBuilderRua(clientesUpdate.getRua())
                .withBuilderTelefone(clientesUpdate.getTelefone());
        logger.info("m=putClientes - status=finish " + id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete (@PathVariable ("id") UUID id) {
        logger.info("m=delete - status=start " + id);
        service.delete(id);
        logger.info("m=delete - status=finish " + id);
    }
}
