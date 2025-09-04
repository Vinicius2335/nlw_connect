# NLW Connect API

API RESTful desenvolvida em **Java 17+** com **Spring Boot** para gerenciamento de eventos e inscrições. Este projeto faz parte do NLW e serve como backend para o frontend [nlw-connect-web](../nlw-connect-web).

---

## Sumário

- [NLW Connect API](#nlw-connect-api)
  - [Sumário](#sumário)
  - [Funcionalidades](#funcionalidades)
  - [Tecnologias](#tecnologias)
  - [Como executar](#como-executar)
    - [Pré-requisitos](#pré-requisitos)
    - [Passos](#passos)
  - [Principais Endpoints](#principais-endpoints)
  - [Testes](#testes)
  - [Boas práticas adotadas](#boas-práticas-adotadas)
  - [Contribuição](#contribuição)
  - [Licença](#licença)

---

## Funcionalidades

- Cadastro e consulta de eventos
- Inscrição de usuários em eventos
- Ranking de indicações por evento
- Consulta de ranking individual
- População de inscrições em massa para testes

## Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- Lombok
- Swagger/OpenAPI
- Log4j2
- Banco de dados relacional (MySQL/PostgreSQL)
- JUnit 5 (testes)

## Como executar

### Pré-requisitos

- Java 17+
- Maven
- Banco de dados configurado

### Passos

1. **Clone o repositório**
   ```bash
   git clone https://github.com/seuusuario/nlw-connect-api.git
   cd nlw-connect-api
   ```

2. **Configure o banco de dados**
   Edite o arquivo `src/main/resources/application.properties` com suas credenciais.

3. **Build do projeto**
   ```bash
   mvn clean install
   ```

4. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

5. **Acesse a documentação Swagger**
   ```
   http://localhost:8080/swagger/docs
   ```

## Principais Endpoints

| Método | Rota                                      | Descrição                       |
|--------|-------------------------------------------|---------------------------------|
| POST   | `/events`                                 | Criação de evento               |
| GET    | `/events`                                 | Listagem de eventos             |
| GET    | `/events/{prettyName}`                    | Buscar evento por nome amigável |
| POST   | `/subscriptions/{prettyName}`             | Inscrever usuário em evento     |
| GET    | `/subscriptions/{prettyName}/ranking`     | Ranking dos principais inscritos|
| GET    | `/subscriptions/{prettyName}/ranking/{userId}` | Ranking de um usuário específico|

## Testes

Execute os testes automatizados com:

```bash
mvn test
```

## Boas práticas adotadas

- **Validação de dados** com anotações nos DTOs
- **Tratamento centralizado de erros** via `ApplicationExceptionHandler`
- **Documentação automática** com Swagger/OpenAPI
- **Injeção de dependências** e uso de `@Service`, `@Repository`
- **Testes unitários e de integração**
- **Estrutura modular** por domínio

## Contribuição

Contribuições são bem-vindas! Abra uma issue ou envie um pull request.

## Licença

Este projeto está sob a licença [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0).

---