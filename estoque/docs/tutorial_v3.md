# Guia de Arquitetura Hexagonal (Ports & Adapters)

**Propósito:** documento técnico pronto para colocar em `raiz/docs/architecture_guide.md`. Combina visão arquitetural, exemplos práticos (Kotlin + Spring Boot) e, novo neste arquivo, um guia prático de responsabilidades, skills e checklists organizados por senioridade (Júnior, Pleno, Sênior, Tech Lead).

---

## Sumário

1. Visão Geral
2. Estrutura do Projeto e Configuração
3. Camada de Domínio
4. Camada de Aplicação
5. Camada de Infraestrutura
6. Padrões & Convenções
7. Testes
8. Configuração & Operação
9. Extensões Avançadas
10. Migração de Legado
11. Boas Práticas
12. Guia por Senioridade (Júnior → Sênior)
13. Checklists Práticos
14. Anexos: Exemplos de Código

---

# 1. Visão Geral

A Arquitetura Hexagonal (Ports & Adapters) separa o núcleo da aplicação (domínio + casos de uso) das dependências externas (banco, UI, frameworks). Isso torna o domínio testável, intercambiável e coeso.

> Benefícios práticos: testabilidade, isolamento, facilidade para trocar infra (ex.: trocar PostgreSQL por MongoDB sem tocar regras de negócio).

---

# 2. Estrutura do Projeto e Configuração

Exemplo de `build.gradle.kts` (resumido) com dependências essenciais: Spring Web, Data JPA, Validation, Actuator, Flyway, H2/Postgres.

```kotlin
// bloco reduzido
plugins {
  id("org.springframework.boot") version "3.2.0"
  kotlin("jvm") version "1.9.20"
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("com.h2database:h2")
}
```

Estrutura de pacotes sugerida (Kotlin):

```
br.com.empresa.produto
├─ domain
│  ├─ model
│  └─ exception
├─ application
│  ├─ port
│  │  ├─ input
│  │  └─ output
│  └─ usecase
├─ infra
│  ├─ adapter
│  └─ config
└─ web
   └─ controller
```

---

# 3. Camada de Domínio

Contém modelos, regras de negócio e validações *fail-fast*. Deve ser puramente Kotlin/Java — sem anotações de frameworks.

Exemplo minimalista (domain model):

```kotlin
data class Product(val id: UUID?, val name: String, val price: BigDecimal) {
  init {
    require(name.isNotBlank()) { "Nome do produto não pode ser vazio" }
    require(price > BigDecimal.ZERO) { "Preço deve ser positivo" }
  }
}
```

Exceções semânticas: crie subclasses de `BusinessException` para erros de domínio.

---

# 4. Camada de Aplicação

Here we define *Use Cases* e *Ports*.

* **Input Port:** contrato que o controller/CLI chama (ex.: `CreateProductUseCase`).
* **Output Port:** contrato que abstrai persistência/serviços externos (ex.: `ProductRepositoryPort`).

Use cases devem orquestrar: validar comandos, chamar repositórios via ports, emitir eventos de domínio.

---

# 5. Camada de Infraestrutura

Adapters concretos:

* **REST controllers** → converte HTTP ↔ DTO ↔ Command/Query
* **Repository adapters (JPA)** → converte Domain ↔ Entity
* **Event adapters** → Kafka/RabbitMQ etc.

Regras práticas:

* Nunca coloque anotações JPA nas classes de domínio.
* Mantenha mappers centralizados e simples (ex.: `toEntity()`, `toDomain()`).

---

# 6. Padrões & Convenções

* Hexagonal (Ports & Adapters)
* DDD (quando necessário — agregados, entidades, value objects)
* CQRS (apenas quando traz benefícios claros)
* Fail-fast validation
* Small interfaces (Single Responsibility)

---

# 7. Testes

Níveis de testes:

* **Unit**: dominio + use cases isolados (MockK/Mockito)
* **Integration**: controllers + Spring context (MockMvc / WebTestClient)
* **Repository**: `@DataJpaTest` com H2
* **E2E**: via ambientes reais/containers

Princípios: rápidos, determinísticos, claros.

---

# 8. Configuração & Operação

Perfis: `dev`, `test`, `prod`. Use `application-*.yml`. Migrations com Flyway. Observabilidade com Actuator + Micrometer.

---

# 9. Extensões Avançadas

Event-Driven design, Domain Events, CQRS, Sagas e Outbox pattern para garantir consistência eventual.

---

# 10. Migração de Legado

Abordagem incremental:

1. Identificar um pequeno módulo com regras de negócio.
2. Extrair domínio puro e ports.
3. Implementar adapters novos que usem o domínio.
4. Testes antes e depois.

---

# 11. Boas Práticas (resumo)

* Domínio sem frameworks
* Small focused use cases
* Testes rápidos
* Documentar contratos de ports

---

# 12. Guia por Senioridade (Júnior → Sênior)

Este é o *novo* e prático trecho que orienta tarefas, expectativas e planos de aprendizado para cada nível de desenvolvedor.

## 12.1 Júnior — "Construtor curioso"

**Objetivo:** aprender padrões básicos, entregar pequenas features, entender o fluxo request → DB.

**Responsabilidades comuns:**

* Implementar endpoints simples (CRUD) com orientação.
* Escrever testes unitários básicos.
* Corrigir bugs e lidar com tickets de baixa complexidade.
* Participar de code reviews como leitor.

**Habilidades técnicas a desenvolver:**

* Linguagem (Kotlin/Java) — estruturas básicas, collections, nullable.
* Spring Boot fundamentals — controllers, services, repositories.
* SQL básico e JPA (entender entidades e mapeamentos).
* Git (branches, PRs, rebase básico).
* Testes unitários com MockK/Mockito.

**Exemplo de checklist diário:**

* Entendi a task? Posso reproduzir o bug localmente?
* Adicionei testes que cobrem o comportamento?
* PR com descrição clara e screenshots (se UI).

**Boas práticas de revisão (como revisor júnior):**

* Verificar nomes, validações e mensagens de erro.
* Garantir que testes foram adicionados.
* Não aprovar sem CI verde e revisão de um sênior.

**Roadmap de 6 meses:**

* Entender projetos locais, rodar app, executar testes.
* Entregar 6–10 tasks pequenas com supervisão.
* Ler sobre Hexagonal e DDD (resumos práticos).

## 12.2 Pleno — "Orquestrador competente"

**Objetivo:** entregar features moderadas, propor melhorias, mentoring leve.

**Responsabilidades comuns:**

* Projetar endpoints e modelos para features completas.
* Implementar use cases e integração com infraestrutura.
* Escrever testes de integração e unitários robustos.
* Revisar PRs de juniores e participar do design.

**Habilidades técnicas a desenvolver:**

* Design de APIs (REST/HTTP semantics, status codes).
* Padrões arquiteturais (Ports & Adapters, DTOs, mappers).
* Performance e otimização simples (index, queries).
* Observabilidade (logs estruturados, métricas simples).
* Conceitos de segurança (OAuth2/JWT básicos).

**Checklist para PR de Pleno:**

* Test coverage aceitável para a área modificada.
* Documentação de API (OpenAPI/Swagger) atualizada.
* Migrações corretamente versionadas (Flyway).
* Considerações de operações (se cache, se invalidar cache?).

**Mentoria:**

* Ensinar boas práticas de testes e code style.
* Ajudar juniores em debugging.

**Roadmap de 12 meses:**

* Liderar 2–3 features completas.
* Propor e aplicar 1 melhoria de arquitetura/infra.
* Aprender patterns avançados (event-driven, outbox).

## 12.3 Sênior — "Arquiteto de problemas"

**Objetivo:** desenhar sistemas confiáveis, escáveis e orientar tecnicamente o time.

**Responsabilidades comuns:**

* Definir padrões arquiteturais e decisões de design.
* Revisar e aprovar mudanças críticas.
* Coordenação com SRE/Infra para deploys e SLIs.
* Mentoria formal para plenos e juniores.

**Habilidades técnicas a desenvolver:**

* Design para escalabilidade e disponibilidade.
* Consistência eventual, transações distribuídas e sagas.
* Observabilidade avançada (tracing distribuído — OpenTelemetry).
* Segurança aplicada (OWASP, threat modeling).
* Definir SLAs/SLIs e deploy strategies (blue/green, canary).

**Checklist de arquitetura para Senior:**

* Avaliar trade-offs (consistência vs disponibilidade).
* Plano de rollback e estratégia de migração.
* Testes de carga e pontos de falha identificados.
* Políticas de segurança e compliance verificadas.

**Mentoria e cultura:**

* Conduzir design reviews e sessões de conhecimento.
* Orientar carreiras (path técnico vs gerencial).

**Roadmap de 12–24 meses:**

* Liderar redesigns importantes.
* Introduzir práticas de Observability & SRE.
* Ajudar a criar roadmap técnico do produto.

## 12.4 Tech Lead / Staff — "Guardião do ecossistema"

**Objetivo:** alinhar visão técnica com negócio, capacitar times e garantir saúde técnica do produto.

**Principais responsabilidades:**

* Roadmap técnico e visão cross-team.
* Facilitar decisões organizacionais (tooling, infra).
* Mentoria de líderes e arquitetura de alto nível.
* Comunicar riscos técnicos ao negócio.

**Habilidades esperadas:**

* Liderança técnica e comunicação com stakeholders.
* Arquitetura de sistemas distribuídos em larga escala.
* Governança técnica (codificação de padrões, compliance).

---

# 13. Checklists Práticos (copy-paste para PR templates)

## Checklist básico para PR

* [ ] Descrição clara do problema/feature.
* [ ] Links para tickets/issue.
* [ ] Testes unitários adicionados/atualizados.
* [ ] Testes de integração (quando relevante).
* [ ] Documentação da API atualizada.
* [ ] Migrações incluídas e testadas.
* [ ] CI verde.

## Checklist de revisão de arquitetura

* [ ] Objetivos da mudança claramente documentados.
* [ ] Alternativas consideradas (trade-offs).
* [ ] Impacto em performance, custo e manutenção.
* [ ] Plano de rollout e rollback.
* [ ] Testes de carga/benchmark planejados.

---

# 14. Anexos: Exemplos de Código

> Observação: os exemplos abaixo são intencionais compactos. Use-os como *pattern*.

### Use Case: CreateBrand (Kotlin)

```kotlin
interface CreateBrandUseCase { fun execute(cmd: CreateBrandCommand): Brand }

data class CreateBrandCommand(val name: String, val description: String?)

class CreateBrandUseCaseImpl(private val repo: BrandRepositoryPort): CreateBrandUseCase {
  override fun execute(cmd: CreateBrandCommand): Brand {
    if (repo.existsByName(cmd.name)) throw BrandAlreadyExists(cmd.name)
    val brand = Brand(name = cmd.name, description = cmd.description)
    return repo.save(brand)
  }
}
```

### Repo Port

```kotlin
interface BrandRepositoryPort {
  fun save(brand: Brand): Brand
  fun findById(id: UUID): Brand?
  fun existsByName(name: String): Boolean
}
```

---

## Observações finais (curtas)

* Este arquivo foi preparado para ser publicado em `raiz/docs/architecture_guide.md`.
* Contém conselhos práticos por senioridade que podem ser incorporados em planos de carreira, PR templates e onboarding.

---

*Fim do documento.*
