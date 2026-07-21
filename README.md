# StockFlow

StockFlow é uma API REST para gerenciamento de estoque sendo desenvolvida com Java e Spring Boot.

O projeto surgiu a partir da observação de processos de controle de estoque realizados manualmente por meio de anotações em papel. Esse tipo de controle está sujeito a falhas humanas, perda de informações e dificuldades de rastreamento, podendo gerar inconsistências no estoque e impactar a tomada de decisões.

A proposta do StockFlow é oferecer uma solução digital para registro e acompanhamento de produtos e movimentações de estoque, fornecendo uma base confiável para futuras integrações com aplicações clientes.

>  Projeto em desenvolvimento.

Além da implementação das funcionalidades de negócio, o projeto tem como foco o aprofundamento dos conhecimentos em:

- Java
- Spring Boot
- Arquitetura em camadas
- APIs REST
- Persistência de dados
- Testes unitários
- Docker e Docker Compose
- Boas práticas de desenvolvimento

## Aplicação Cliente

O StockFlow foi planejado para ser consumido por uma aplicação Android, permitindo que operações de estoque sejam realizadas diretamente por dispositivos móveis.

Entre os benefícios esperados estão:

- Registro rápido de movimentações
- Consulta de produtos em tempo real
- Redução de erros operacionais
- Maior rastreabilidade das informações
- Eliminação de controles manuais em papel

## Funcionalidades Planejadas

### Usuários

- [x] Cadastro de usuário
- [x] Consulta de usuário
- [x] Atualização de usuário
- [x] Remoção de usuário

### Produtos

- [x] Cadastro de produtos
- [x] Consulta de produtos
- [x] Atualização de produtos
- [x] Remoção de produtos

### Estoque

- [x] Registro de entrada de produtos
- [x] Registro de saída de produtos
- [x] Controle de quantidade disponível
- [x] Histórico de movimentações

### Relatórios

- [ ] Consulta de movimentações
- [ ] Produtos com baixo estoque
- [ ] Indicadores básicos de estoque

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Docker
- Docker Compose
- JUnit 5
- Mockito

## Status Atual

Atualmente o projeto possui:

- Endpoint de criação de usuários
- Testes unitários da camada de serviço
- Containerização da aplicação com Docker
- Banco de dados PostgreSQL executando em container
- Ambiente configurado com Docker Compose

## Executando o Projeto

### Docker

```bash
docker compose up --build
```

### Maven

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

## Arquitetura

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

---

# Estrutura do Projeto

```text
src
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── service
├── config
└── test
```

---

# Próximas Funcionalidades

- [ ] Implementar autenticação e autorização utilizando Spring Security e JWT
- [ ] Desenvolver uma aplicação Web para gerenciamento de estoque
- [ ] Desenvolver uma aplicação Android integrada à API

---

## Autor

Lucas Alexandre de Souza
