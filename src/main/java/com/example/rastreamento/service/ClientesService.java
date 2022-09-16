package com.example.rastreamento.service;

import com.example.rastreamento.model.Clientes;
import com.example.rastreamento.repository.ClientesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientesService {

    private static final Logger logger = LoggerFactory.getLogger(ClientesService.class);
    @Autowired
    private ClientesRepository repository;

    public List<Clientes> findAllClientes() {
        logger.info("m=findAllClientes - status=start");
        List<Clientes> clientesList = repository.findAll();
        logger.info("m=findAllClientes - status=finish");
        return clientesList;
    }

    public Clientes getClientesById(UUID id){
        logger.info("m=getClientesById - status=start " + id);
        Clientes clientes =repository.findById(id).get();
        logger.info("m=getClientesById - status=finish " + id);
        return clientes;
    }
    public Optional<Clientes> getClientesByIdOptional(UUID id) {
        logger.info("m=getClientesByIdOptional - status=start " + id);
        Optional<Clientes> clientesOptional = repository.findById(id);
        logger.info("m=getClientesByIdOptional - status=finish " + id);
        return clientesOptional;
    }

    public Clientes save (Clientes clientes){
        logger.info("m=save - status=start ");
        Clientes clientesSave = repository.save(clientes);
        logger.info("m=save - status=finish ");
        return clientesSave;
    }

    public Clientes update(Clientes clientes) {
        logger.info("m=update - status=start " + clientes.getId());
        Optional<Clientes> optional = getClientesByIdOptional(clientes.getId());
        if (optional.isPresent()){
            Clientes clientesEntity = optional.get();
            clientesEntity.setBairro(clientes.getBairro());
            clientesEntity.setCidade(clientes.getCidade());
            clientesEntity.setNome(clientes.getNome());
            clientesEntity.setNumero_logradouro(clientes.getNumero_logradouro());
            clientesEntity.setRua(clientes.getRua());
            clientesEntity.setTelefone(clientes.getTelefone());
            repository.save(clientesEntity);
            logger.info("m=update - status=finish " + clientes.getId());
            return clientesEntity;

        }
        else {
            logger.warn("m=update - status=warn " + clientes.getId());
            throw new RuntimeException();
        }
    }

    public void delete(UUID id){
        logger.info("m=delete - status=start " + id);
        Optional<Clientes> clientes = getClientesByIdOptional(id);
        if (clientes.isPresent()){
            logger.info("m=delete - status=finish " + id);
            repository.deleteById(id);
        }else {
            logger.warn("m=delete - status=warn " + id);
            throw new RuntimeException("O id informado é inexistente.");
        }
    }
}
