# Estoque — Módulo do Supermercado

> Status: Estável e em evolução contínua. Este módulo segue os mesmos padrões de documentação, arquitetura e qualidade
> definidos no README raiz do monorepo.

## Sumário

- [Visão Geral](#visão-geral)
- [Arquitetura](#arquitetura)
- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Como Executar](#como-executar)
- [Perfis e Configurações](#perfis-e-configurações)
- [Migrações de Banco](#migrações-de-banco)
- [Documentação da API](#documentação-da-api)
- [Testes](#testes)
- [Qualidade e Observabilidade](#qualidade-e-observabilidade)
- [Estrutura do Módulo](#estrutura-do-módulo)
- [Convenções de Código e Commits](#convenções-de-código-e-commits)
- [Contribuição](#contribuição)
- [Suporte](#suporte)
- [Licença](#licença)

## Visão Geral

Serviço responsável pelo catálogo e controle de estoque, abrangendo cadastro de Marcas e Produtos, além de regras de
validação e paginação de resultados. Implementado em Kotlin + Spring Boot, utilizando Arquitetura Hexagonal (Ports &
Adapters) e práticas de DDD.

## Arquitetura

Seguindo Arquitetura Hexagonal, com separação entre domínio, aplicação e infraestrutura:

```
estoque/
├─ src/main/kotlin/br/com/supermercado/estoque/
│  ├─ domain/            # Regras de negócio e modelos
│  ├─ application/       # Casos de uso e portas (input/output)
│  └─ infrastructure/    # Adapters REST, JPA, configuração
└─ src/main/resources/   # Configurações, Flyway, application-*.yml
```

Pontos-chave:

- Ports (interfaces) expostas na camada de aplicação; adapters implementam portas na infraestrutura
- Spring Data JPA para persistência; mapeamento separado (Entity/Mapper)
- Tratamento global de erros e respostas padronizadas

## Tecnologias

- Kotlin 1.9.x
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL (prod) e H2 (test/dev)
- Flyway
- Swagger/OpenAPI
- JUnit 5, MockK, Testcontainers

## Funcionalidades

- Marcas (Brand)
    - CRUD completo (criar, listar paginado, buscar por ID, atualizar, deletar)
    - Validação de nome duplicado e mensagens de erro padronizadas
- Produtos (Product)
    - CRUD, busca por ID e por código de barras
    - Paginação consistente e mapeamentos DTO

## Como Executar

Pré-requisitos:
- Java 17+
- (Opcional) Subir infraestrutura de apoio via Docker Compose na raiz do monorepo

1) Infra de apoio (opcional, recomendado para Postgres, Sonar, Kafka) — executar a partir da raiz do repositório:
```bash
docker compose -f docker-composer.yml up -d
```

2) Executar o módulo Estoque em desenvolvimento

- Linux/Mac:
```bash
cd estoque && ./gradlew bootRun
```

- Windows (PowerShell):

```powershell
cd estoque; .\gradlew.bat bootRun
```

A aplicação sobe, por padrão, na porta 8080.

## Perfis e Configurações

Perfis disponíveis: `dev`, `test`, `prod`.

- Arquivos: `src/main/resources/application.yml`, `application-dev.yml`, `application-test.yml`
- Variáveis comuns (exemplos):

```properties
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/supermercado
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=Admin@123
```

## Migrações de Banco

- Migrado via Flyway automaticamente ao iniciar a aplicação
- Scripts localizados em: `src/main/resources/db/imigration`
    - `V1__CREATE_SCHEMA.sql`, `V2__CREATE_BRAND_TABLE.sql`, `V3__CREATE_PRODUCT_TABLE.sql`, `V4__INSERT_BRANDS.sql`

## Documentação da API

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Testes

- Testes Unitários e de Integração (JUnit 5, MockK, Testcontainers)
- Como executar:
    - Linux/Mac:
      ```bash
      cd estoque && ./gradlew test
      ```
    - Windows (PowerShell):
      ```powershell
      cd estoque; .\gradlew.bat test
      ```

## Qualidade e Observabilidade

- SonarQube (infra disponível via Docker Compose na raiz: http://localhost:9000)
- Tratamento global de exceções com `GlobalExceptionHandler` e payload `ErrorResponse`
- Padrão de paginação e respostas consistente (`PageResponse`, DTOs dedicados)

## Estrutura do Módulo

- `src/main/kotlin/br/com/supermercado/estoque/application` — casos de uso e portas
- `src/main/kotlin/br/com/supermercado/estoque/domain` — modelos de domínio e exceções
- `src/main/kotlin/br/com/supermercado/estoque/infrastructure` — controllers REST, adapters JPA, config
- `src/main/resources/db/imigration` — scripts Flyway
- `docs/` — guias e tutoriais (ver também no README raiz)

## Convenções de Código e Commits

- Kotlin + Spring Boot 3, Arquitetura Hexagonal
- Mensagens de erro e contratos HTTP padronizados
- Commits semânticos: `feat`, `fix`, `docs`, `test`, `refactor`, `chore`

## Contribuição

- Abra uma issue descrevendo contexto e objetivo
- Envie PR pequena, com testes e aderente às convenções

## Suporte

- Dúvidas e problemas: issues no repositório
- Consulte também `estoque/docs` e o README na raiz para visão geral do monorepo

## Licença

Este módulo segue a mesma licença definida no README raiz do repositório (proprietária, ou ajuste para MIT/Apache-2.0
conforme aplicável).