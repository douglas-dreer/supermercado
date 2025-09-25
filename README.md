# Supermercado — Monorepo de Microsserviços

Um ecossistema de serviços para gestão de supermercados, com foco em escalabilidade, qualidade e manutenibilidade. Este
repositório hospeda múltiplos módulos (monorepo), como PDV (ponto de venda) e Estoque.

> Status do projeto: Em evolução contínua. O módulo de Estoque recebeu melhorias significativas recentemente (detalhes
> abaixo).

## Sumário

- [Visão Geral](#visão-geral)
- [Arquitetura e Módulos](#arquitetura-e-módulos)
- [Destaques (Módulo Estoque – alterações recentes)](#destaques-módulo-estoque--alterações-recentes)
- [Requisitos](#requisitos)
- [Como executar (Docker Compose)](#como-executar-docker-compose)
- [Desenvolvimento local por módulo](#desenvolvimento-local-por-módulo)
- [Migrações e Banco de Dados](#migrações-e-banco-de-dados)
- [Documentação da API](#documentação-da-api)
- [Qualidade, Segurança e Observabilidade](#qualidade-segurança-e-observabilidade)
- [Testes](#testes)
- [Estrutura do Repositório](#estrutura-do-repositório)
- [Convenções de Código e Commits](#convenções-de-código-e-commits)
- [Roadmap](#roadmap)
- [Contribuição](#contribuição)
- [Suporte e Contato](#suporte-e-contato)
- [Licença](#licença)

## Visão Geral

Este projeto suporta operações essenciais de um supermercado por meio de serviços independentes. Cada módulo segue boas
práticas de engenharia (DDD estratégico, Arquitetura Hexagonal/Ports & Adapters, testes automatizados e automações de
qualidade).

## Arquitetura e Módulos

- PDV (Ponto de Venda): Registro de vendas, pagamentos e emissão de cupons. [`pdv/`](pdv/)
- Estoque: Inventário, cadastro de produtos e marcas, integrações e validações. [`estoque/`](estoque/)
- Planejados: Compras, Financeiro, Relatórios, CRM.

Para detalhes técnicos do módulo de Estoque, consulte:

- Guia de arquitetura: [`estoque/docs/architecture_guide.md`](estoque/docs/architecture_guide.md)
- Tutoriais: [`estoque/docs/tutorial_v2.md`](estoque/docs/tutorial_v2.md) e [
  `estoque/docs/tutorial_v3.md`](estoque/docs/tutorial_v3.md)
- README do módulo: [`estoque/README.md`](estoque/README.md)

## Destaques (Módulo Estoque – alterações recentes)

As últimas iterações focaram na robustez do domínio de Marcas (Brand) e na solidez das operações de catálogo:

- Gestão de Marcas (Brand)
    - CRUD completo (criar, listar paginado, buscar por ID, atualizar, deletar)
    - Validações de negócio (prevenção de nomes duplicados, mensagens de erro padronizadas)
    - Migrações versionadas com Flyway (`V1__CREATE_SCHEMA.sql`, `V2__CREATE_BRAND_TABLE.sql`, `V4__INSERT_BRANDS.sql`)
    - Repositório com Spring Data JPA (adapter + mapper) e portas de saída bem definidas
    - Testes unitários e de integração com Testcontainers cobrindo validações e persistência
- Produtos (Product)
    - CRUD e busca por código de barras, com paginação e regras de negócio associadas
- Observabilidade e Boas Práticas
    - Tratamento global de exceções (`GlobalExceptionHandler`)
    - DTOs de request/response e paginação consistente
    - Documentação OpenAPI/Swagger habilitada no módulo

## Requisitos

- Docker e Docker Compose instalados
- Java 17+ (para desenvolvimento local dos módulos)

## Como executar (Docker Compose)

Na raiz deste repositório, há um orquestrador de infraestrutura para desenvolvimento com Postgres, SonarQube e
Kafka/Zookeeper.

1) Suba os serviços de apoio:

```bash
docker compose -f docker-composer.yml up -d
```

Serviços provisionados:

- postgres_supermercado: Postgres 14 (porta 5432)
- sonarqube e sonarqube_db: análise de qualidade (porta 9000)
- zookeeper e kafka: mensageria (porta 9092)

2) Acompanhe logs (opcional):

```bash
docker compose -f docker-composer.yml logs -f
```

3) Para parar:

```bash
docker compose -f docker-composer.yml down
```

## Desenvolvimento local por módulo

- Estoque
    - Executar:
        - Linux/Mac:
          ```bash
          cd estoque && ./gradlew bootRun
          ```
        - Windows (PowerShell):
          ```powershell
          cd estoque; .\gradlew.bat bootRun
          ```
    - Perfis: dev, test, prod (`application.yml`, `application-dev.yml`, `application-test.yml`)
    - Documentação da API: http://localhost:8080/swagger-ui.html (após subir o módulo)
    - Detalhes adicionais no [`estoque/README.md`](estoque/README.md)
- PDV
    - Estrutura inicial disponível em [`pdv/`](pdv/)

## Migrações e Banco de Dados

- Flyway gerencia a evolução do esquema (`estoque/src/main/resources/db/imigration`)
- Scripts adicionais de seed em [`init-db/`](init-db/)
- Ao iniciar a aplicação (com o perfil apropriado), as migrações são aplicadas automaticamente

## Documentação da API

- Swagger UI (módulo Estoque): http://localhost:8080/swagger-ui.html
- OpenAPI JSON (módulo Estoque): http://localhost:8080/v3/api-docs

## Qualidade, Segurança e Observabilidade

- Qualidade: SonarQube disponível em http://localhost:9000
- Exceções e erros padronizados via `ErrorResponse` e `GlobalExceptionHandler` no módulo de Estoque
- Validações centralizadas e mensagens de erro consistentes

## Testes

- Testes Unitários e de Integração (JUnit 5, MockK, Testcontainers)
- Como executar (no módulo Estoque):
    - Linux/Mac:
      ```bash
      ./gradlew test
      ```
    - Windows (PowerShell):
      ```powershell
      .\gradlew.bat test
      ```

## Estrutura do Repositório

- [`estoque/`](estoque/): serviço de Estoque (código, docs e testes)
- [`pdv/`](pdv/): serviço de PDV (esqueleto inicial)
- [`init-db/`](init-db/): scripts SQL auxiliares
- [`docker-composer.yml`](docker-composer.yml): infraestrutura local (Postgres, SonarQube, Kafka/ZooKeeper)

## Convenções de Código e Commits

- Kotlin + Spring Boot 3, seguindo princípios de Arquitetura Hexagonal
- Padronização de mensagens de erro e respostas HTTP no módulo Estoque
- Commits semânticos recomendados: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`

## Roadmap

- Integração PDV ↔ Estoque via eventos (Kafka)
- Módulos de Compras, Financeiro, Relatórios e CRM
- Ampliação de testes de contrato e performance

## Contribuição

Contribuições são bem-vindas! Recomendações:

- Abra uma issue descrevendo o contexto e o objetivo
- Proponha uma PR pequena e com testes
- Mantenha o estilo e as convenções do projeto

## Suporte e Contato

- Problemas e dúvidas: abra uma issue neste repositório
- Discussões técnicas: consulte os documentos em `estoque/docs` e o README do módulo

## Licença

Este projeto é distribuído sob licença proprietária da organização (ou ajuste para MIT/Apache-2.0 conforme aplicável).
