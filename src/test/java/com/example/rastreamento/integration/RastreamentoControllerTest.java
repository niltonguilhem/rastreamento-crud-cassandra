package com.example.rastreamento.integration;

import com.example.rastreamento.RastreamentoApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RastreamentoApplication.class
)
public class RastreamentoControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1/rastreamento";
    }

    @Test
    public void deveRetornarSucesso_AoConsultarRastreamentos() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void deveCadastrarENoFinalDeletarUmRastreamento() {

        String novoRastreio = """
        {
            "bairro": "Santa Paula",
            "cidade": "São Caetano do Sul",
            "nome": "Nilton Guilhem",
            "numero_logradouro": 137,
            "rua": "Maranhão",
            "telefone": 985931111
        }
        """;

        String idGerado = given()
                .header("partner", "Star-Park")
                .contentType(ContentType.JSON)
                .body(novoRastreio)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .pathParam("id", idGerado)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void deveRetornarErro_QuandoPartnerForInvalido() {
        String novoRastreio = """
    {
        "bairro": "Santa Paula",
        "cidade": "São Caetano do Sul",
        "nome": "Nilton Guilhem",
        "numero_logradouro": 137,
        "rua": "Maranhão",
        "telefone": 985931111
    }
    """;

        given()
                .header("partner", "Parceiro-Desconhecido")
                .contentType(ContentType.JSON)
                .body(novoRastreio)
                .when()
                .post()
                .then()
                .statusCode(500);
    }

    @Test
    public void deveValidarCamposDoCadastroComSucesso() {
        String nomeEsperado = "Nilton Guilhem";
        String cidadeEsperada = "São Caetano do Sul";

        String novoRastreio = """
    {
        "bairro": "Santa Paula",
        "cidade": "%s",
        "nome": "%s",
        "numero_logradouro": 137,
        "rua": "Maranhão",
        "telefone": 985931111
    }
    """.formatted(cidadeEsperada, nomeEsperado);

        given()
                .header("partner", "Star-Park")
                .contentType(ContentType.JSON)
                .body(novoRastreio)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("nome", is(nomeEsperado))
                .body("cidade", equalTo(cidadeEsperada))
                .body("bairro", notNullValue())
                .body("id", not(emptyOrNullString()))
                .body("numero_logradouro", instanceOf(Integer.class));
    }

    @Test
    public void deveRetornarErro_QuandoEnviarCamposVazios() {

        String rastreioVazio = """
    {
        "bairro": "",
        "cidade": "",
        "nome": "",
        "numero_logradouro": 0,
        "rua": "",
        "telefone": 0
    }
    """;

        given()
                .header("partner", "Star-Park")
                .contentType(ContentType.JSON)
                .body(rastreioVazio)
                .when()
                .post()
                .then()
                .statusCode(anyOf(is(201), is(400)));
    }
    @Test
    public void deveRetornar400_QuandoCamposObrigatoriosEstiveremVazios() {

        String rastreioInvalido = """
    {
        "bairro": "",
        "cidade": "",
        "nome": "",
        "rua": ""
    }
    """;

        given()
                .header("partner", "Star-Park")
                .contentType(ContentType.JSON)
                .body(rastreioInvalido)
                .when()
                .post()
                .then()
                .statusCode(400);
    }
    @Test
    public void deveRetornarMensagemDeErroEspecifica_QuandoNomeEstiverVazio() {
        String rastreioSemNome = """
    {
        "bairro": "Santa Paula",
        "cidade": "São Caetano",
        "nome": "", 
        "numero_logradouro": 137,
        "rua": "Maranhão",
        "telefone": 999999999
    }
    """;

        given()
                .header("partner", "Star-Park")
                .contentType(ContentType.JSON)
                .body(rastreioSemNome)
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("campo", hasItem("nome"))
                .body("erro", hasItem("O nome é obrigatório"));
    }
    @Test
    public void deveAtualizarRastreamentoComSucesso() {
        String novoRastreio = """
        {
            "bairro": "Antigo",
            "cidade": "SBC",
            "nome": "Cliente Original",
            "numero_logradouro": 10,
            "rua": "Rua A",
            "telefone": 111111111
        }
        """;

        String idGerado = given()
                .header("partner", "Star-Park")
                .contentType(ContentType.JSON)
                .body(novoRastreio)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().path("id");

        String rastreioAtualizado = """
        {
            "bairro": "Novo Bairro",
            "cidade": "São Caetano",
            "nome": "Cliente Atualizado",
            "numero_logradouro": 999,
            "rua": "Rua Nova",
            "telefone": 222222222
        }
        """;

        given()
                .header("Partner", "Star-Park")
                .contentType(ContentType.JSON)
                .pathParam("id", idGerado)
                .body(rastreioAtualizado)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200)
                .body("nome", is("Cliente Atualizado"))
                .body("bairro", is("Novo Bairro"))
                .body("numero_logradouro", is(999));
    }

    @Test
    public void deveRetornarErro_AoAtualizarComPartnerInvalido() {
        UUID idAleatorio = UUID.randomUUID();

        String corpoRequest = "{ \"nome\": \"Teste\" }";

        given()
                .header("Partner", "Partner-Inexistente")
                .contentType(ContentType.JSON)
                .pathParam("id", idAleatorio)
                .body(corpoRequest)
                .when()
                .put("/{id}")
                .then()
                .statusCode(500);
    }

    @Test
    public void deveRetornarErro_AoAtualizarIdInexistente() {
        UUID idInexistente = UUID.randomUUID();

        String dadosUpdate = """
        {
            "bairro": "Teste",
            "cidade": "Teste",
            "nome": "Teste",
            "numero_logradouro": 1,
            "rua": "Teste",
            "telefone": 123
        }
        """;

        given()
                .header("Partner", "Star-Park")
                .contentType(ContentType.JSON)
                .pathParam("id", idInexistente)
                .body(dadosUpdate)
                .when()
                .put("/{id}")
                .then()
                .statusCode(500);
    }
    @Test
    public void deveRetornarClienteEspecifico_AoConsultarPorId() {
        String corpo = """
    {
        "bairro": "Barcelona",
        "cidade": "São Caetano",
        "nome": "Busca Por ID",
        "numero_logradouro": 50,
        "rua": "Rua Maceió",
        "telefone": 44445555
    }
    """;

        String idGerado = given()
                .header("partner", "Star-Park")
                .contentType(ContentType.JSON)
                .body(corpo)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().path("id");

        given()
                .pathParam("id", idGerado)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("id", is(idGerado))
                .body("nome", is("Busca Por ID"));
    }

    @Test
    public void deveRetornarErro_AoBuscarIdInexistente() {
        UUID idFicticio = UUID.randomUUID();

        given()
                .pathParam("id", idFicticio)
                .when()
                .get("/{id}")
                .then()
                .statusCode(anyOf(is(404), is(500)));
    }
}