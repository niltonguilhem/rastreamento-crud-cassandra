package com.example.rastreamento.unit;

import com.example.rastreamento.utils.ClientesUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ClientesUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"Star-Park", "Center-Park", "Downton-Park"})
    @DisplayName("Deve retornar true para parceiros válidos")
    void deveRetornarTrueParaParceirosValidos(String partner) throws Exception {
        assertTrue(ClientesUtils.validatedPartner(partner));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o parceiro for inválido")
    void deveLancarExcecaoParaParceiroInvalido() {
        String partnerInvalido = "Outro-Park";

        Exception exception = assertThrows(Exception.class, () -> {
            ClientesUtils.validatedPartner(partnerInvalido);
        });

        assertEquals("Partner inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o parceiro for nulo ou vazio")
    void deveLancarExcecaoParaPartnerVazio() {
        assertThrows(Exception.class, () -> ClientesUtils.validatedHeader(null));
        assertThrows(Exception.class, () -> ClientesUtils.validatedHeader(""));
    }

    @Test
    @DisplayName("validatedHeader não deve lançar exceção para parceiro válido")
    void validatedHeaderDevePassarComSucesso() {
        assertDoesNotThrow(() -> ClientesUtils.validatedHeader("Star-Park"));
    }
}
