# StockFlow

StockFlow é uma API REST para gerenciamento de estoque sendo desenvolvida com Java e Spring Boot.

O projeto surgiu a partir da observação de processos de controle de estoque realizados manualmente por meio de anotações em papel. Esse tipo de controle está sujeito a falhas humanas, perda de informações e dificuldades de rastreamento, podendo gerar inconsistências no estoque e impactar a tomada de decisões.

A proposta do StockFlow é oferecer uma solução digital para registro e acompanhamento de produtos e movimentações de estoque, fornecendo uma base confiável para futuras integrações com aplicações clientes.

>  Projeto em desenvolvimento.

## Objetivo

Desenvolver uma API robusta para gerenciamento de estoque, aplicando boas práticas de desenvolvimento backend e servindo como backend para uma futura aplicação Android.

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
- [ ] Consulta de usuário
- [ ] Atualização de usuário
- [ ] Remoção de usuário

### Produtos

- [ ] Cadastro de produtos
- [ ] Consulta de produtos
- [ ] Atualização de produtos
- [ ] Remoção de produtos

### Estoque

- [ ] Registro de entrada de produtos
- [ ] Registro de saída de produtos
- [ ] Controle de quantidade disponível
- [ ] Histórico de movimentações

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

## Autor

Lucas de Souza
