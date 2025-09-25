Baseado no arquivo que você enviou (converti e revisei todo o conteúdo).

Abaixo está a versão revisada e reorganizada em capítulos — pronta para colar em `raiz/docs/architecture_guide.md` ou salvar como novo arquivo Markdown. Mantive o conteúdo técnico, melhorei a clareza, corrigi português e organizei por capítulos com títulos, sumário e trechos de código formatados.

# 📘 Guia Prático — Arquitetura Hexagonal (Sistema de Estoque)

**Resumo rápido:** este guia apresenta uma implementação prática da *Arquitetura Hexagonal (Ports & Adapters)* aplicada a um sistema de estoque em Kotlin + Spring Boot. Contém estrutura de pastas, exemplos de código, decisões de design, padrões, testes e recomendações para evolução.

---

## Sumário (capítulos)

1. [Capítulo 1 — Visão Geral](#capitulo-1---visão-geral)
2. [Capítulo 2 — Configuração do Projeto](#capitulo-2---configuração-do-projeto)
3. [Capítulo 3 — Camada de Domínio](#capitulo-3---camada-de-domínio)
4. [Capítulo 4 — Camada de Aplicação](#capitulo-4---camada-de-aplicação)
5. [Capítulo 5 — Camada de Infraestrutura](#capitulo-5---camada-de-infraestrutura)
6. [Capítulo 6 — Padrões & Convenções](#capitulo-6---padrões--convenções)
7. [Capítulo 7 — Testes](#capitulo-7---testes)
8. [Capítulo 8 — Configurações & Operação](#capitulo-8---configurações--operação)
9. [Capítulo 9 — Extensões Avançadas](#capitulo-9---extensões-avançadas)
10. [Capítulo 10 — Migração para Hexagonal](#capitulo-10---migração-para-hexagonal)
11. [Capítulo 11 — Resumo de Boas Práticas](#capitulo-11---resumo-de-boas-práticas)
12. [Anexo — Recursos de Estudo](#anexo---recursos-de-estudo)

---

# Capítulo 1 — Visão Geral

## O que é Hexagonal (Ports & Adapters)

Arquitetura Hexagonal isola o núcleo da aplicação (domínio + casos de uso) de tudo que é externo: banco, UI, frameworks, integrações. A ideia é que a lógica de negócio fique independente e testável, enquanto adaptadores traduzem o mundo externo para os *ports* que o núcleo espera.

### Diagrama conceitual

```
┌─────────────────────────────────────────┐
│           INFRASTRUCTURE               │
│  ┌─────────────────────────────────┐    │
│  │         APPLICATION            │    │
│  │  ┌─────────────────────────┐   │    │
│  │  │        DOMAIN          │   │    │
│  │  └─────────────────────────┘   │    │
│  └─────────────────────────────────┘    │
└─────────────────────────────────────────┘
```

### Benefícios

* Testabilidade do domínio.
* Troca fácil de implementações (DB, cache, etc.).
* Separação clara de responsabilidades.

---

# Capítulo 2 — Configuração do Projeto

## Dependências essenciais (`build.gradle.kts`)

Exemplo mínimo do bloco `plugins` e dependências principais:

```kotlin
plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")
}
```

## Classe principal

```kotlin
@SpringBootApplication
@EnableCaching
class EstoqueApplication
```

---

# Capítulo 3 — Camada de Domínio

O domínio é o coração — regras e modelos puros, sem dependências de frameworks.

## Modelos (exemplos)

### `Brand.kt`

* `data class` imutável com validações no `init`.
* Use `UUID` para id, `LocalDateTime` para timestamps.

```kotlin
data class Brand(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val active: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(name.isNotBlank()) { "Brand name cannot be blank" }
        require(name.length <= 100) { "Brand name cannot exceed 100 characters" }
    }
    
    fun update(name: String? = null): Brand { /*...*/ }
}
```

### `Product.kt`

* Use `BigDecimal` para preço.
* Encapsule regras em métodos do próprio modelo (ex.: `isLowStock()`).

```kotlin
data class Product(
    val id: UUID? = null,
    val name: String,
    val barcode: String,
    val price: BigDecimal,
    val stockQuantity: Int = 0,
    val brandId: UUID
) {
    init {
        require(name.isNotBlank()) { "Product name cannot be blank" }
        require(barcode.isNotBlank()) { "Product barcode cannot be blank" }
        require(price > BigDecimal.ZERO) { "Product price must be positive" }
        require(stockQuantity >= 0) { "Stock quantity cannot be negative" }
    }
    
    fun isLowStock(minStockQuantity: Int = 5): Boolean = stockQuantity <= minStockQuantity
}
```

## Exceções de domínio

Crie exceções semânticas que herdem de `DomainException`. Ex.: `BrandNotFoundException`, `ProductAlreadyExistsException`.

---

# Capítulo 4 — Camada de Aplicação

A camada que orquestra casos de uso (use cases) — não deve conter lógica de negócio complexa, apenas coordenação.

## Ports (contratos)

* **Input Ports**: interfaces para os use cases (ex.: `CreateBrandUseCase`).
* **Output Ports**: contratos para persistência e comunicação externa (ex.: `BrandRepositoryPort`).

Exemplo de port de repositório:

```kotlin
interface BrandRepositoryPort {
    fun save(brand: Brand): Brand
    fun findById(id: UUID): Brand?
    fun findAll(pageable: Pageable): Page<Brand>
    fun existsByName(name: String): Boolean
}
```

## DTOs

* **Commands**: para operações de escrita (`CreateBrandCommand`).
* **Queries**: para leitura (`FindAllBrandsQuery`).

## Exemplo de Use Case

```kotlin
@UseCase
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort
) : CreateBrandUseCase {
    
    override fun execute(command: CreateBrandCommand): Brand {
        if (brandRepository.existsByName(command.name)) {
            throw BrandAlreadyExistsException(command.name)
        }
        val brand = Brand(name = command.name, description = command.description)
        return brandRepository.save(brand)
    }
}
```

`@UseCase` é uma annotation simples que fixa componentes como beans do Spring.

---

# Capítulo 5 — Camada de Infraestrutura

Adapters concretos: REST controllers, repositórios JPA, integrações externas e mapeadores.

## Input Adapters — REST

* Controllers tratam HTTP, validação e conversão DTO ↔ domínio.
* Use `@Valid` em request DTOs.

Exemplo de request DTO:

```kotlin
data class CreateBrandRequest(
    @field:NotBlank @field:Size(max = 100)
    val name: String,
    @field:Size(max = 500)
    val description: String?
)
```

## Output Adapters — Persistência (JPA)

* Separe entidades JPA dos modelos de domínio.
* Mappers para converter `Entity <-> Domain`.

Exemplo de `BrandEntity` e mapper:

```kotlin
@Entity
@Table(name = "brands")
data class BrandEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    
    @Column(nullable = false, unique = true, length = 100)
    val name: String,
    // ...
)

@Component
class BrandEntityMapper {
    fun toEntity(brand: Brand): BrandEntity { /*...*/ }
    fun toDomain(entity: BrandEntity): Brand { /*...*/ }
}
```

## Adapter (Repository Adapter)

Implemente `BrandRepositoryPort` usando `BrandJpaRepository` + `BrandEntityMapper`.

---

# Capítulo 6 — Padrões & Convenções

## Principais padrões aplicados

* **Hexagonal (Ports & Adapters)** — isolamento do domínio.
* **CQRS** — separar modelos de leitura e escrita quando faz sentido.
* **DDD (Domain-Driven Design)** — modelo rico de domínio.
* **SOLID** — especialmente Dependency Inversion (ports/interfaces).

## Nomenclatura recomendada

* `br.com.supermercado.estoque.domain.model`
* `application.port.input`, `application.port.output`, `application.usecase`
* Interfaces: `CreateBrandUseCase` / Implementações: `CreateBrandUseCaseImpl`
* Repositórios: `BrandRepositoryPort` / adapters: `BrandRepositoryAdapter`

---

# Capítulo 7 — Testes

## Estratégia geral

Siga a pirâmide de testes: unitários → integração → ponta-a-ponta.

### Unit tests (use cases, domain)

* Rápidos, sem I/O. Use mocks (MockK).
* Teste comportamento e regras de negócio.

Exemplo:

```kotlin
class CreateBrandUseCaseImplTest {
    // mock setup ...
}
```

### Integration tests (controllers)

* `@SpringBootTest`, `MockMvc`, `@AutoConfigureTestDatabase`.
* Teste mapeamento HTTP, validação e fluxo completo.

### Repository tests

* `@DataJpaTest` para validar mapeamentos e constraints.

---

# Capítulo 8 — Configurações & Operação

## Perfis Spring

* **dev**: H2, `ddl-auto=create-drop`, SQL logging.
* **test**: H2 com `DB_CLOSE_DELAY=-1`, sem Flyway.
* **prod**: PostgreSQL via env var, pool otimizado, logs reduzidos.

Exemplo `application-dev.yml` (resumido):

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop
```

## Migrations (Flyway)

* Migrations versionadas, pequenas e imutáveis.
* Exemplo: `V1__create_brand_table.sql` com índice e triggers.

## Caching

* Recomendado: Caffeine em memória para caches locais.
* Anotações `@Cacheable` / `@CacheEvict`.

## Observabilidade

* Spring Actuator: `/actuator/health`, `/metrics`, `/prometheus`.
* Integrar `MeterRegistry` (Micrometer) para métricas customizadas.

---

# Capítulo 9 — Extensões Avançadas

## Event-Driven

* Defina eventos de domínio (ex.: `BrandCreated`) e `EventPublisherPort`.
* Use para desacoplar integrações (fila, stream).

## Segurança

* Contexto de segurança (`UserContext`) injetado nos use cases quando necessário.
* Valide roles/permissions no nível do caso de uso.

## Métricas

* Use `Counter`/`Timer` para medir operações críticas (ex.: criação de entidades).

---

# Capítulo 10 — Migração (legado → hexagonal)

Passos práticos:

1. Extrair modelos de domínio (tirar anotações JPA do domínio).
2. Criar use cases (casos de uso separados da lógica de infra).
3. Definir ports (interfaces) para repositórios e serviços externos.
4. Implementar adapters (JPA adapters, controllers).
5. Testar incrementalmente e garantir compatibilidade.

Exemplo de transformação (antes / depois) incluído no guia original.

---

# Capítulo 11 — Resumo de Boas Práticas

### Do’s

* Manter domínio puro e testável.
* Modelos imutáveis e validação *fail-fast*.
* Interfaces pequenas e específicas.
* Documentar intentions via nomenclatura clara.

### Don’ts

* Não misturar JPA com modelos de domínio.
* Evitar *God classes* que fazem tudo.
* Não usar exceções genéricas para erros de negócio.

---

# Anexo — Recursos de Estudo

* *Clean Architecture* — Robert C. Martin
* *Domain-Driven Design* — Eric Evans
* *Implementing Domain-Driven Design* — Vaughn Vernon
* Artigos de referência sobre Hexagonal (Alistair Cockburn) e blogs técnicos.

---

## Observações finais (curtas e úteis)

* Este documento foi reorganizado e revisado a partir do seu arquivo original.
* Se quiser, eu transformo isso num arquivo pronto (por exemplo `docs/architecture_guide.md`) e te entrego o conteúdo com um diff visível — cole aqui se prefere que eu gere o arquivo já formatado para baixar.

Pronto — revisei, limpei e estruturei em capítulos. Quer que eu também:

* gere a versão final em `raiz/docs/architecture_guide.md` e te entregue como arquivo para download, OU
* aplique um tom ainda mais informal (mais piadas nerds) OU
* produza uma versão resumida (one-pager) para o README do projeto?

Você escolhe — mas sem temporizadores, prometo, farei já. 😄
