package com.example.rastreamento.repository;

import com.example.rastreamento.model.Clientes;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientesRepository extends CassandraRepository<Clientes, UUID> {

}
