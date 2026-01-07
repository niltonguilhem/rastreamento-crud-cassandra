package com.example.rastreamento;

import com.example.rastreamento.model.Clientes;
import com.example.rastreamento.repository.ClientesRepository;
import com.example.rastreamento.service.ClientesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientesServiceTest {

    @Mock
    private ClientesRepository repository; // "Fingimos" o banco de dados

    @InjectMocks
    private ClientesService service; // A classe que queremos testar

    @Test
    public void deveBuscarClientePorIdComSucesso() {
        // Cenário
        UUID id = UUID.randomUUID();
        Clientes clienteFake = new Clientes();
        clienteFake.setId(id);
        clienteFake.setNome("Nilton");

        when(repository.findById(id)).thenReturn(Optional.of(clienteFake));

        // Execução
        Clientes resultado = service.getClientesById(id);

        // Validação
        assertNotNull(resultado);
        assertEquals("Nilton", resultado.getNome());
        verify(repository, times(1)).findById(id); // Garante que o banco foi consultado
    }
}
