# Rastreamento CRUD Cassandra 🚗🛰️

Este projeto é uma API REST desenvolvida em **Java 17** com **Spring Boot 2.6.6**, focada no gerenciamento e rastreamento de dados de clientes, utilizando o banco de dados NoSQL **Apache Cassandra**.

O projeto inclui um pipeline completo de testes automatizados com **Rest-Assured** e métricas de cobertura de código com **JaCoCo**.

## 🚀 Tecnologias Utilizadas

* **Java 17** (Amazon Corretto)
* **Spring Boot 2.6.6**
* **Spring Data Cassandra** (Persistência NoSQL)
* **Rest-Assured** (Testes de API)
* **JaCoCo** (Code Coverage)
* **Maven** (Gerenciamento de dependências)
* **Docker** (Containerização do Banco de Dados)

## 🛠️ Como Executar o Projeto

### 1. Pré-requisitos
* Docker instalado e em execução.
* Java 17 instalado.
* Maven instalado.

### 2. Subir o Banco de Dados (Cassandra)
Utilize o Docker para subir uma instância do Cassandra pronta para uso:
```bash
docker run --name cassandra-test -p 9042:9042 -d cassandra:latest
Aguarde cerca de 45 segundos para a inicialização completa do banco.

3. Executar a Aplicação
Bash

mvn spring-boot:run
🧪 Testes Automatizados
Os testes de integração validam o fluxo completo da API (Caminho Feliz e Caminhos de Erro).

Para rodar os testes via terminal:

Bash

mvn clean test
Cenários Testados:
GET /api/v1/rastreamento: Valida a listagem de clientes.

POST /api/v1/rastreamento: Valida o cadastro com Partner autorizado e dados íntegros.

DELETE /api/v1/rastreamento/{id}: Valida a exclusão de registros.

Validação de Segurança: Bloqueio de requisições com Partner inválido.

Validação de Dados: Bloqueio de campos nulos ou vazios (@NotBlank/@NotNull).

📊 Cobertura de Código (JaCoCo)
Após executar os testes, você pode visualizar o relatório de cobertura:

Navegue até a pasta target/site/jacoco/.

Abra o arquivo index.html no seu navegador.

O projeto foca em manter uma cobertura acima de 80%, garantindo que as regras de negócio e tratadores de erro estejam protegidos.

🛡️ Regras de Negócio e Segurança
Partner Validation: A API só aceita requisições de parceiros homologados via Header (Star-Park, Center-Park, Downton-Park).

Global Error Handling: Tratamento especializado de exceções para retornar mensagens amigáveis em caso de erros de validação (400 Bad Request).

Desenvolvido por Nilton Guilhem como parte de estudos em arquitetura Spring Boot e Testes de Integração.