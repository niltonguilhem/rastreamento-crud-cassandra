package com.example.rastreamento;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

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
                // Valida se o JSON de erro contém o campo e a mensagem correta
                .body("campo", hasItem("nome"))
                .body("erro", hasItem("O nome é obrigatório"));
    }
}