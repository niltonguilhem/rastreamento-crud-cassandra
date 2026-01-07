package com.example.rastreamento.unit;

import com.example.rastreamento.model.Clientes;
import com.example.rastreamento.repository.ClientesRepository;
import com.example.rastreamento.service.ClientesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientesServiceTest {

    @Mock
    private ClientesRepository repository;

    @InjectMocks
    private ClientesService service;

    private Clientes cliente;
    private UUID id;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        cliente = new Clientes();
        cliente.setId(id);
        cliente.setNome("João Silva");
        cliente.setBairro("Centro");
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void deveListarTodosClientes() {
        when(repository.findAll()).thenReturn(List.of(cliente));
        List<Clientes> resultado = service.findAllClientes();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve buscar cliente por ID (Objeto direto)")
    void deveBuscarClientePorId() {
        when(repository.findById(id)).thenReturn(Optional.of(cliente));
        Clientes resultado = service.getClientesById(id);
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    @DisplayName("Deve salvar um novo cliente")
    void deveSalvarCliente() {
        when(repository.save(any(Clientes.class))).thenReturn(cliente);
        Clientes salvo = service.save(new Clientes());
        assertNotNull(salvo);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve atualizar cliente quando ID existir")
    void deveAtualizarClienteComSucesso() {
        when(repository.findById(id)).thenReturn(Optional.of(cliente));
        when(repository.save(any(Clientes.class))).thenReturn(cliente);

        Clientes updateData = new Clientes();
        updateData.setId(id);
        updateData.setNome("Nome Atualizado");

        Clientes resultado = service.update(updateData);

        assertEquals("Nome Atualizado", resultado.getNome());
        verify(repository).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar cliente inexistente")
    void deveLancarExcecaoNoUpdateInvalido() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(cliente));
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void deveDeletarCliente() {
        when(repository.findById(id)).thenReturn(Optional.of(cliente));

        assertDoesNotThrow(() -> service.delete(id));

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar cliente inexistente")
    void deveLancarExcecaoAoDeletarInexistente() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertEquals("O id informado é inexistente.", exception.getMessage());
    }
}