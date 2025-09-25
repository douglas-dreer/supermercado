# 📚 Guia Detalhado da Arquitetura Hexagonal - Sistema de Estoque

Este documento explica detalhadamente cada componente do sistema de estoque implementado seguindo os princípios da **Arquitetura Hexagonal (Ports and Adapters)**.

## 🎯 Índice

1. [Visão Geral da Arquitetura](#visão-geral)
2. [Configuração do Projeto](#configuração)
3. [Domain Layer](#domain-layer)
4. [Application Layer](#application-layer)
5. [Infrastructure Layer](#infrastructure-layer)
6. [Padrões e Convenções](#padrões)
7. [Testes](#testes)
8. [Configurações](#configurações)

---

## 🏗️ Visão Geral da Arquitetura {#visão-geral}

### Princípios da Arquitetura Hexagonal

A **Arquitetura Hexagonal**, também conhecida como **Ports and Adapters**, foi criada por Alistair Cockburn e tem como objetivo principal **isolar o núcleo da aplicação** (domínio e casos de uso) das **preocupações externas** (frameworks, banco de dados, APIs externas).

#### 🎨 Estrutura Visual
```
┌─────────────────────────────────────────┐
│           INFRASTRUCTURE               │
│  ┌─────────────────────────────────┐    │
│  │         APPLICATION            │    │
│  │  ┌─────────────────────────┐   │    │
│  │  │        DOMAIN          │   │    │
│  │  │                        │   │    │
│  │  │  - Models              │   │    │
│  │  │  - Business Rules      │   │    │
│  │  │  - Domain Services     │   │    │
│  │  └─────────────────────────┘   │    │
│  │                                │    │
│  │  - Use Cases                   │    │
│  │  - Ports (Interfaces)         │    │
│  └─────────────────────────────────┘    │
│                                        │
│  - Controllers (REST)                  │
│  - Repositories (JPA)                  │
│  - External Services                   │
└─────────────────────────────────────────┘
```

### 📂 Estrutura de Pastas Detalhada

```
src/main/kotlin/br/com/supermercado/estoque/
├── EstoqueApplication.kt                    # Aplicação principal Spring Boot
├── domain/                                  # 🎯 CAMADA DE DOMÍNIO
│   ├── model/                              # Entidades de negócio
│   │   ├── Brand.kt                        # Modelo de Marca
│   │   └── Product.kt                      # Modelo de Produto
│   ├── exception/                          # Exceções de domínio
│   │   ├── DomainException.kt              # Exceção base
│   │   ├── BrandNotFoundException.kt       # Marca não encontrada
│   │   ├── ProductNotFoundException.kt     # Produto não encontrado
│   │   ├── BrandAlreadyExistsException.kt  # Marca já existe
│   │   ├── ProductAlreadyExistsException.kt# Produto já existe
│   │   └── ValidationException.kt          # Erro de validação
│   └── service/                            # Serviços de domínio
│       ├── BrandDomainService.kt           # Lógicas complexas de marca
│       └── ProductDomainService.kt         # Lógicas complexas de produto
├── application/                            # ⚙️ CAMADA DE APLICAÇÃO
│   ├── port/                               # Contratos (Interfaces)
│   │   ├── input/                          # Entrada (Use Cases)
│   │   │   ├── brand/                      # Casos de uso de marca
│   │   │   └── product/                    # Casos de uso de produto
│   │   └── output/                         # Saída (Repositórios)
│   │       ├── BrandRepositoryPort.kt      # Contrato repositório marca
│   │       └── ProductRepositoryPort.kt    # Contrato repositório produto
│   ├── usecase/                            # Implementação dos casos de uso
│   │   ├── brand/                          # Implementações marca
│   │   └── product/                        # Implementações produto
│   └── dto/                                # Objects de transferência
│       ├── command/                        # Comandos (escritas)
│       └── query/                          # Consultas (leituras)
└── infrastructure/                         # 🔌 CAMADA DE INFRAESTRUTURA
    ├── config/                             # Configurações Spring
    ├── adapter/                            # Adaptadores
    │   ├── input/                          # Adaptadores de entrada
    │   │   └── rest/                       # Controllers REST
    │   └── output/                         # Adaptadores de saída
    │       └── persistence/                # Persistência JPA
    └── common/                             # Utilitários compartilhados
```

---

## 🔧 Configuração do Projeto {#configuração}

### 📄 `build.gradle.kts`

```kotlin
plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
}
```

**Explicação das Dependências:**

#### 🌱 **Spring Boot Starters**
```kotlin
implementation("org.springframework.boot:spring-boot-starter-web")
```
- **Propósito**: Fornece toda infraestrutura para criar APIs REST
- **Inclui**: Spring MVC, Tomcat embarcado, JSON binding (Jackson)
- **Por que usar**: Simplifica drasticamente a configuração de uma aplicação web

```kotlin
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
```
- **Propósito**: Abstração sobre JPA/Hibernate para persistência
- **Inclui**: Spring Data JPA, Hibernate, pool de conexões HikariCP
- **Por que usar**: Reduz código boilerplate para operações de banco

```kotlin
implementation("org.springframework.boot:spring-boot-starter-validation")
```
- **Propósito**: Validação de dados usando Bean Validation (JSR-303)
- **Inclui**: Hibernate Validator, anotações como `@NotNull`, `@Size`
- **Por que usar**: Centraliza e padroniza validações

#### 📊 **Observabilidade**
```kotlin
implementation("org.springframework.boot:spring-boot-starter-actuator")
```
- **Propósito**: Endpoints para monitoramento e métricas
- **Fornece**: `/health`, `/metrics`, `/info`
- **Por que usar**: Essencial para ambientes de produção

#### 🗄️ **Banco de Dados**
```kotlin
runtimeOnly("com.h2database:h2")              # Desenvolvimento
runtimeOnly("org.postgresql:postgresql")       # Produção
implementation("org.flywaydb:flyway-core")    # Migrations
```
- **H2**: Banco em memória para desenvolvimento/testes
- **PostgreSQL**: Banco para produção
- **Flyway**: Versionamento e migração de schema

### 🚀 `EstoqueApplication.kt`

```kotlin
@SpringBootApplication
@EnableCaching
class EstoqueApplication
```

**Anotações Explicadas:**

- **`@SpringBootApplication`**: 
  - Combina `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan`
  - Auto-configura a aplicação baseada nas dependências no classpath

- **`@EnableCaching`**: 
  - Habilita o suporte a cache declarativo
  - Permite usar `@Cacheable`, `@CacheEvict` nos métodos

---

## 🎯 Domain Layer {#domain-layer}

Esta é o **coração da aplicação** - contém toda a lógica de negócio e regras de domínio. É **independente** de qualquer framework ou tecnologia externa.

### 📋 Models (Entidades de Domínio)

#### 🏷️ `Brand.kt` - Modelo de Marca

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
    
    fun update(name: String? = null, /*...*/ ): Brand { /*...*/ }
}
```

**📝 Características Importantes:**

1. **Data Class**: 
   - Gera automaticamente `equals()`, `hashCode()`, `toString()`
   - Imutabilidade por padrão (todos os campos `val`)

2. **Validações no `init`**:
   - Garantem que o objeto nunca seja criado em estado inválido
   - Falham rápido com `require()` - princípio **fail-fast**

3. **Método `update()`**:
   - Retorna nova instância (imutabilidade)
   - Usa **named parameters** para clareza
   - Atualiza automaticamente `updatedAt`

4. **Tipos Seguros**:
   - `UUID` para IDs (não sequencial, seguro para APIs públicas)
   - `LocalDateTime` (sem timezone - mais simples para domínio)
   - `Boolean` para flags

#### 📦 `Product.kt` - Modelo de Produto

```kotlin
data class Product(
    val id: UUID? = null,
    val name: String,
    val barcode: String,
    val price: BigDecimal,
    val stockQuantity: Int = 0,
    val brandId: UUID,
    // ... outros campos
) {
    init {
        require(name.isNotBlank()) { "Product name cannot be blank" }
        require(barcode.isNotBlank()) { "Product barcode cannot be blank" }
        require(price > BigDecimal.ZERO) { "Product price must be positive" }
        require(stockQuantity >= 0) { "Stock quantity cannot be negative" }
    }
    
    fun isLowStock(): Boolean = stockQuantity <= minStockQuantity
}
```

**💡 Decisões de Design:**

1. **`BigDecimal` para preços**:
   - Evita problemas de arredondamento com `Double`
   - Precisão financeira necessária

2. **Método de negócio `isLowStock()`**:
   - Encapsula lógica de negócio
   - Legibilidade e reutilização

3. **Referência por ID (`brandId`)**:
   - Evita carregamento desnecessário (lazy loading)
   - Mantém modelo simples

### ⚠️ Domain Exceptions

```kotlin
abstract class DomainException(message: String, cause: Throwable? = null) 
    : RuntimeException(message, cause)

class BrandNotFoundException(brandId: UUID) 
    : DomainException("Brand not found with id: $brandId")
```

**🎯 Vantagens das Exceções de Domínio:**

1. **Semântica Clara**: Nome da exceção explica exatamente o problema
2. **Hierarchy**: Todas herdam de `DomainException`
3. **Informações Contextuais**: IDs específicos nas mensagens
4. **Tratamento Centralizado**: Podem ser capturadas no `@ControllerAdvice`

---

## ⚙️ Application Layer {#application-layer}

Esta camada **orquestra** os casos de uso da aplicação. Ela **não contém lógica de negócio**, apenas **coordena** as operações entre domínio e infraestrutura.

### 🔌 Ports (Interfaces)

Os **Ports** são **contratos** que definem **como** a aplicação interage com o mundo exterior, sem especificar **o que** implementa esses contratos.

#### 🎯 Input Ports - Casos de Uso

```kotlin
interface CreateBrandUseCase {
    fun execute(command: CreateBrandCommand): Brand
}
```

**Por que usar interfaces?**

1. **Testabilidade**: Fácil de mockar nos testes
2. **Flexibility**: Diferentes implementações (síncronas, assíncronas)
3. **Dependency Inversion**: A aplicação não depende de implementações concretas
4. **Claro Contracts**: Interface expressa exatamente o que o caso de uso faz

#### 🔄 Output Ports - Repositórios

```kotlin
interface BrandRepositoryPort {
    fun save(brand: Brand): Brand
    fun findById(id: UUID): Brand?
    fun findAll(pageable: Pageable): Page<Brand>
    fun existsByName(name: String): Boolean
}
```

**Características dos Output Ports:**

1. **Abstrações**: Não sabem nada sobre JPA, SQL ou qualquer tecnologia
2. **Domain Objects**: Trabalham apenas com objetos de domínio
3. **Focused**: Cada repositório foca em uma agregação específica

### 📥📤 DTOs (Data Transfer Objects)

#### Commands (Escritas)
```kotlin
data class CreateBrandCommand(
    val name: String,
    val description: String?
)
```

#### Queries (Leituras)
```kotlin
data class FindAllBrandsQuery(
    val pageable: Pageable
)
```

**🎯 CQRS (Command Query Responsibility Segregation):**

- **Commands**: Representam **intenções** de mudar estado
- **Queries**: Representam **consultas** sem efeitos colaterais
- **Separation**: Modelos otimizados para cada operação

### 🏭 Use Case Implementations

```kotlin
@UseCase
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort
) : CreateBrandUseCase {
    
    override fun execute(command: CreateBrandCommand): Brand {
        // 1. Validar regras de negócio
        if (brandRepository.existsByName(command.name)) {
            throw BrandAlreadyExistsException(command.name)
        }
        
        // 2. Criar objeto de domínio
        val brand = Brand(
            name = command.name,
            description = command.description
        )
        
        // 3. Persistir através do port
        return brandRepository.save(brand)
    }
}
```

**📋 Anatomia de um Use Case:**

1. **Single Responsibility**: Cada use case faz exatamente uma coisa
2. **Business Logic**: Valida regras antes de executar
3. **Dependency Injection**: Recebe dependências via construtor
4. **Pure Functions**: Entrada → Processamento → Saída
5. **Exception Handling**: Usa exceções de domínio para casos excepcionais

**🏷️ `@UseCase` Annotation:**

```kotlin
@Component
annotation class UseCase
```

- **Semantics**: Deixa claro que a classe é um caso de uso
- **Spring Integration**: Permite injeção de dependência
- **Documentation**: Auto-documenta a arquitetura

---

## 🔌 Infrastructure Layer {#infrastructure-layer}

Esta camada contém **todos os detalhes técnicos** e **implementações concretas** dos ports. É a **única camada** que conhece frameworks específicos.

### 🌐 Input Adapters - REST Controllers

#### 🎮 Controller Structure

```kotlin
@RestController
@RequestMapping("/api/v1/brands")
@Tag(name = "Brands", description = "Brand management operations")
class BrandController(
    private val createBrandUseCase: CreateBrandUseCase,
    private val findBrandByIdUseCase: FindBrandByIdUseCase,
    // ... outros use cases
)
```

**🔧 Controller Responsibilities:**

1. **HTTP Protocol Handling**: Request/Response mapping
2. **Input Validation**: `@Valid` annotation
3. **Use Case Orchestration**: Chama o use case apropriado
4. **Data Transformation**: Converte DTOs ↔ Domain Objects
5. **HTTP Status Codes**: Retorna códigos apropriados

#### 📨 Request/Response DTOs

```kotlin
data class CreateBrandRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 100, message = "Name must not exceed 100 characters")
    val name: String,
    
    @field:Size(max = 500, message = "Description must not exceed 500 characters")
    val description: String?
)
```

**🎯 Validation Strategy:**

- **Bean Validation**: JSR-303 annotations
- **Clear Messages**: User-friendly error messages
- **Field-level**: `@field:` prefix for Kotlin properties
- **Constraint Composition**: Multiple constraints per field

#### 🎪 Exception Handling

```kotlin
@RestControllerAdvice
class GlobalExceptionHandler {
    
    @ExceptionHandler(BrandNotFoundException::class)
    fun handleBrandNotFoundException(ex: BrandNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(message = ex.message ?: "Brand not found")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }
}
```

**✨ Benefits of Global Exception Handler:**

1. **Centralized Error Handling**: Um lugar para tratar todos os erros
2. **Consistent Error Format**: Padroniza formato de resposta de erro
3. **HTTP Status Mapping**: Converte exceções de domínio em códigos HTTP
4. **Clean Controllers**: Controllers focam apenas no happy path

### 🗄️ Output Adapters - Persistence

#### 🏗️ JPA Entities

```kotlin
@Entity
@Table(name = "brands")
data class BrandEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    
    @Column(nullable = false, unique = true, length = 100)
    val name: String,
    // ... outros campos
)
```

**🎯 JPA Design Decisions:**

- **Separate from Domain**: Entidades JPA ≠ Objetos de Domínio
- **Database-Focused**: Otimizadas para persistência
- **Annotations**: Metadados para mapeamento ORM

#### 🔄 Entity Mappers

```kotlin
@Component
class BrandEntityMapper {
    
    fun toEntity(brand: Brand): BrandEntity {
        return BrandEntity(
            id = brand.id,
            name = brand.name,
            description = brand.description,
            // ...
        )
    }
    
    fun toDomain(entity: BrandEntity): Brand {
        return Brand(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            // ...
        )
    }
}
```

**💡 Mapping Strategy:**

1. **Bidirectional**: Domain → Entity e Entity → Domain
2. **Explicit**: Mapeamento explícito (não automático)
3. **Component**: Spring bean para injeção de dependência
4. **Immutability Preservation**: Preserva imutabilidade do domínio

#### 🔧 Repository Adapters

```kotlin
@Adapter
class BrandRepositoryAdapter(
    private val repository: BrandJpaRepository,
    private val mapper: BrandEntityMapper
) : BrandRepositoryPort {
    
    override fun save(brand: Brand): Brand {
        val entity = mapper.toEntity(brand)
        val savedEntity = repository.save(entity)
        return mapper.toDomain(savedEntity)
    }
    
    override fun findById(id: UUID): Brand? {
        return repository.findById(id)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }
}
```

**🎪 Adapter Pattern Benefits:**

1. **Abstraction**: Esconde detalhes do JPA da aplicação
2. **Translation**: Converte entre diferentes modelos de dados
3. **Single Responsibility**: Cada adapter tem uma responsabilidade
4. **Testability**: Pode ser facilmente mockado

### ⚙️ Configuration Classes

#### 🫘 Bean Configuration

```kotlin
@Configuration
class BeanConfig {
    
    @Bean
    fun createBrandUseCase(brandRepository: BrandRepositoryPort): CreateBrandUseCase {
        return CreateBrandUseCaseImpl(brandRepository)
    }
}
```

**🎯 Manual Bean Configuration Benefits:**

1. **Explicit Dependencies**: Fica claro quais dependências cada use case precisa
2. **Testability**: Fácil de reconfigurar para testes
3. **Flexibility**: Pode condicionar criação de beans
4. **Documentation**: Serve como documentação da arquitetura

#### 📖 Swagger Configuration

```kotlin
@Configuration
class SwaggerConfig {
    
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Estoque API")
                    .description("API para gerenciamento de estoque")
                    .version("1.0.0")
            )
    }
}
```

---

## 🎨 Padrões e Convenções {#padrões}

### 🏗️ Architectural Patterns

#### 1. **Hexagonal Architecture (Ports & Adapters)**
```
Domain ← Application → Infrastructure
  ↑         ↑            ↑
Models   Use Cases   Controllers/Repos
```

**Benefits:**
- **Testability**: Domínio isolado e testável
- **Flexibility**: Fácil trocar implementações
- **Maintainability**: Responsabilidades bem separadas

#### 2. **CQRS (Command Query Responsibility Segregation)**
```kotlin
// Commands (Write Operations)
data class CreateBrandCommand(/*...*/)
interface CreateBrandUseCase { fun execute(command: CreateBrandCommand): Brand }

// Queries (Read Operations)  
data class FindBrandByIdQuery(/*...*/)
interface FindBrandByIdUseCase { fun execute(query: FindBrandByIdQuery): Brand }
```

**Benefits:**
- **Optimization**: Modelos otimizados para leitura vs escrita
- **Scalability**: Pode escalar leitura e escrita independentemente
- **Clarity**: Fica claro quais operações mudam estado

#### 3. **Domain-Driven Design (DDD)**
```kotlin
// Rich Domain Models
class Brand {
    fun update(name: String? = null): Brand { /*...*/ }
    
    init {
        require(name.isNotBlank()) { "Brand name cannot be blank" }
    }
}

// Domain Services for complex business logic
class ProductDomainService {
    fun calculateReorderPoint(product: Product): Int { /*...*/ }
}
```

### 🎯 SOLID Principles Applied

#### S - Single Responsibility Principle
```kotlin
// ✅ Each use case has single responsibility
class CreateBrandUseCaseImpl : CreateBrandUseCase
class UpdateBrandUseCaseImpl : UpdateBrandUseCase
class DeleteBrandUseCaseImpl : DeleteBrandUseCase
```

#### O - Open/Closed Principle
```kotlin
// ✅ Open for extension via new implementations
interface BrandRepositoryPort { /*...*/ }

class DatabaseBrandRepository : BrandRepositoryPort { /*...*/ }
class CacheBrandRepository : BrandRepositoryPort { /*...*/ }
class FileBrandRepository : BrandRepositoryPort { /*...*/ }
```

#### L - Liskov Substitution Principle
```kotlin
// ✅ Any BrandRepositoryPort implementation can be substituted
fun someFunction(repository: BrandRepositoryPort) {
    // Works with any implementation
    repository.save(brand)
}
```

#### I - Interface Segregation Principle
```kotlin
// ✅ Specific, focused interfaces
interface CreateBrandUseCase { fun execute(command: CreateBrandCommand): Brand }
interface FindBrandByIdUseCase { fun execute(query: FindBrandByIdQuery): Brand }

// ❌ Instead of one big interface
interface BrandService {
    fun create(command: CreateBrandCommand): Brand
    fun update(command: UpdateBrandCommand): Brand
    fun delete(id: UUID)
    fun findById(id: UUID): Brand
    // ... many other methods
}
```

#### D - Dependency Inversion Principle
```kotlin
// ✅ Depends on abstraction (BrandRepositoryPort)
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort  // ← Interface
) : CreateBrandUseCase

// ❌ Instead of depending on concretion
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandJpaRepository  // ← Concrete class
)
```

### 📝 Naming Conventions

#### Package Naming
```
br.com.supermercado.estoque.domain.model       # Entities
br.com.supermercado.estoque.domain.exception   # Domain exceptions
br.com.supermercado.estoque.application.port.input   # Use case interfaces
br.com.supermercado.estoque.application.usecase      # Use case implementations
```

#### Class Naming
```kotlin
// Use Cases
interface CreateBrandUseCase                    # Interface
class CreateBrandUseCaseImpl                    # Implementation

// Repositories  
interface BrandRepositoryPort                   # Interface
class BrandRepositoryAdapter                    # Implementation

// Entities
class Brand                                     # Domain entity
class BrandEntity                              # JPA entity

// DTOs
class CreateBrandCommand                        # Command
class FindBrandByIdQuery                       # Query
class CreateBrandRequest                        # HTTP request
class BrandResponse                            # HTTP response
```

#### Method Naming
```kotlin
// Use Cases
fun execute(command: CreateBrandCommand): Brand

// Repositories
fun save(brand: Brand): Brand
fun findById(id: UUID): Brand?
fun existsByName(name: String): Boolean

// Domain Models
fun update(name: String? = null): Brand
fun isLowStock(): Boolean
```

---

## 🧪 Testing Strategy {#testes}

### 🏗️ Testing Pyramid

```
        /\
       /UI\         ← Integration Tests (Controllers)
      /____\
     /      \
    / Unit   \      ← Unit Tests (Use Cases, Domain)
   /  Tests   \
  /____________\
```

### 🎯 Unit Tests - Use Cases

```kotlin
class CreateBrandUseCaseImplTest {
    
    private lateinit var brandRepository: BrandRepositoryPort
    private lateinit var createBrandUseCase: CreateBrandUseCaseImpl
    
    @BeforeEach
    fun setUp() {
        brandRepository = mockk()  // MockK for Kotlin
        createBrandUseCase = CreateBrandUseCaseImpl(brandRepository)
    }
    
    @Test
    fun `should create brand successfully`() {
        // Given
        val command = CreateBrandCommand(name = "Test Brand", description = "Test")
        every { brandRepository.existsByName(command.name) } returns false
        every { brandRepository.save(any()) } returns mockBrand
        
        // When
        val result = createBrandUseCase.execute(command)
        
        // Then
        assertEquals("Test Brand", result.name)
        verify(exactly = 1) { brandRepository.save(any()) }
    }
}
```

**🎯 Unit Test Characteristics:**
- **Fast**: Executam rapidamente (sem I/O)
- **Isolated**: Testam apenas uma unidade por vez
- **Mocked Dependencies**: Usam mocks para dependências
- **Focused**: Testam comportamento específico

### 🔗 Integration Tests - Controllers

```kotlin
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class BrandControllerIntegrationTest {
    
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper
    
    @Test
    fun `should create brand successfully`() {
        val request = CreateBrandRequest(name = "Test Brand", description = "Test")
        
        mockMvc.perform(
            post("/api/v1/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated)
        .andExpected(jsonPath("$.name").value("Test Brand"))
    }
}
```

**🎯 Integration Test Characteristics:**
- **Real Components**: Usa implementações reais
- **Database**: Testa contra banco real (H2 para testes)
- **HTTP Layer**: Testa serialização JSON, validation, etc
- **End-to-End**: Testa fluxo completo

### 🗄️ Repository Tests

```kotlin
@DataJpaTest
@ActiveProfiles("test")
class BrandJpaRepositoryTest {
    
    @Autowired private lateinit var brandRepository: BrandJpaRepository
    
    @Test
    fun `should save and retrieve brand`() {
        // Given
        val brand = BrandEntity(name = "Test Brand", description = "Test")
        
        // When
        val savedBrand = brandRepository.save(brand)
        val foundBrand = brandRepository.findById(savedBrand.id!!)
        
        // Then
        assertTrue(foundBrand.isPresent)
        assertEquals("Test Brand", foundBrand.get().name)
    }
}
```

**🎯 Repository Test Characteristics:**
- **@DataJpaTest**: Configura apenas JPA components
- **Database Focus**: Testa queries, mapeamentos, constraints
- **Transactional**: Rollback automático após cada teste

---

## ⚙️ Configuration Deep Dive {#configurações}

### 🌍 Application Profiles

#### Development Profile (`application-dev.yml`)
```yaml
spring:
  profiles:
    active: dev
  datasource:
    url: jdbc:h2:mem:testdb    # In-memory database
  h2:
    console:
      enabled: true            # H2 Console enabled
  jpa:
    show-sql: true             # SQL logging enabled
    hibernate:
      ddl-auto: create-drop    # Recreate schema on startup
```

**🎯 Development Optimizations:**
- **Fast Startup**: H2 in-memory é mais rápido que PostgreSQL
- **Easy Debugging**: H2 Console + SQL logging
- **Clean State**: Schema recriado a cada execução

#### Production Profile (`application-prod.yml`)
```yaml
spring:
  profiles:
    active: prod
  datasource:
    url: ${DATABASE_URL}       # External configuration
    hikari:
      maximum-pool-size: 20    # Production pool size
logging:
  level:
    root: WARN                 # Less verbose logging
  file:
    name: logs/estoque.log     # File-based logging
```

**🎯 Production Optimizations:**
- **Environment Variables**: Configuração via variáveis de ambiente
- **Connection Pooling**: Pool otimizado para carga de produção
- **Logging**: Logs estruturados e menos verbosos
- **Security**: Sensitive data não hardcoded

#### Test Profile (`application-test.yml`)
```yaml
spring:
  profiles:
    active: test
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1  # Persistent for test session
  jpa:
    hibernate:
      ddl-auto: create-drop    # Clean schema for each test class
  flyway:
    enabled: false             # Skip migrations for tests
```

**🎯 Test Optimizations:**
- **Isolated**: Cada test class tem schema limpo
- **Fast**: H2 em memória para velocidade
- **No Migrations**: Schema criado via JPA annotations

### 🗄️ Database Configuration

#### Flyway Migrations
```sql
-- V1__create_brand_table.sql
CREATE TABLE brands (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for performance
CREATE INDEX idx_brands_name ON brands(name);
CREATE INDEX idx_brands_active ON brands(active);
CREATE INDEX idx_brands_created_at ON brands(created_at);
```

**🎯 Migration Best Practices:**
- **Versioned**: Cada migration tem versão sequencial
- **Immutable**: Migrations nunca são alteradas após produção
- **Incremental**: Cada migration é pequena e focada
- **Rollback Strategy**: Sempre considerar como reverter

#### Database Triggers
```sql
-- Auto-update updated_at column
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$ language 'plpgsql';

CREATE TRIGGER update_brands_updated_at 
    BEFORE UPDATE ON brands 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
```

**🎯 Database-Level Features:**
- **Audit Trail**: Automatic timestamp updates
- **Data Integrity**: Foreign key constraints
- **Performance**: Strategic indexes
- **UUID Generation**: Database-generated UUIDs

### 📊 Caching Strategy

```yaml
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterAccess=30m
```

```kotlin
@Cacheable("brands")
fun findById(id: UUID): Brand? {
    return repository.findById(id)
        ?.let { mapper.toDomain(it) }
}

@CacheEvict(value = ["brands"], key = "#brand.id")
fun save(brand: Brand): Brand {
    // Implementation
}
```

**🎯 Caching Strategy:**
- **Caffeine**: High-performance, in-memory cache
- **Method-Level**: Declarative caching com annotations
- **TTL**: Time-based expiration
- **Eviction**: Cache invalidation em updates

### 📈 Monitoring & Observability

#### Spring Actuator Endpoints
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

**Available Endpoints:**
- **`/actuator/health`**: Application health check
- **`/actuator/info`**: Application information
- **`/actuator/metrics`**: Application metrics
- **`/actuator/prometheus`**: Prometheus-format metrics

#### Custom Health Indicators
```kotlin
@Component
class DatabaseHealthIndicator(
    private val brandRepository: BrandRepositoryPort
) : HealthIndicator {
    
    override fun health(): Health {
        return try {
            brandRepository.count()  // Simple DB check
            Health.up()
                .withDetail("database", "Available")
                .build()
        } catch (ex: Exception) {
            Health.down()
                .withDetail("database", "Unavailable")
                .withException(ex)
                .build()
        }
    }
}
```

---

## 🚀 Advanced Patterns & Extensions

### 🔄 Event-Driven Architecture (Future Extension)

```kotlin
// Domain Events
sealed class BrandEvent {
    data class BrandCreated(val brandId: UUID, val name: String) : BrandEvent()
    data class BrandUpdated(val brandId: UUID, val name: String) : BrandEvent()
    data class BrandDeleted(val brandId: UUID) : BrandEvent()
}

// Event Publisher Port
interface EventPublisherPort {
    fun publish(event: BrandEvent)
}

// Use Case with Events
@UseCase
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort,
    private val eventPublisher: EventPublisherPort
) : CreateBrandUseCase {
    
    override fun execute(command: CreateBrandCommand): Brand {
        val brand = Brand(name = command.name, description = command.description)
        val savedBrand = brandRepository.save(brand)
        
        // Publish domain event
        eventPublisher.publish(BrandEvent.BrandCreated(savedBrand.id!!, savedBrand.name))
        
        return savedBrand
    }
}
```

### 🔐 Security Integration (Future Extension)

```kotlin
// Security Context
data class UserContext(
    val userId: UUID,
    val roles: Set<String>,
    val tenantId: UUID?
)

// Secured Use Case
@UseCase
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort,
    private val securityContext: SecurityContext
) : CreateBrandUseCase {
    
    override fun execute(command: CreateBrandCommand): Brand {
        // Security check
        securityContext.requireRole("ADMIN")
        
        val brand = Brand(
            name = command.name,
            description = command.description,
            tenantId = securityContext.getCurrentUser().tenantId
        )
        
        return brandRepository.save(brand)
    }
}
```

### 📊 Metrics & Analytics

```kotlin
@UseCase
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort,
    private val meterRegistry: MeterRegistry
) : CreateBrandUseCase {
    
    private val brandCreationCounter = Counter.builder("brands.created")
        .description("Number of brands created")
        .register(meterRegistry)
    
    private val brandCreationTimer = Timer.builder("brands.creation.time")
        .description("Time to create a brand")
        .register(meterRegistry)
    
    override fun execute(command: CreateBrandCommand): Brand {
        return brandCreationTimer.recordCallable {
            val brand = Brand(name = command.name, description = command.description)
            val savedBrand = brandRepository.save(brand)
            
            brandCreationCounter.increment()
            
            savedBrand
        }!!
    }
}
```

---

## 🎯 Best Practices Summary

### ✅ Do's

1. **Keep Domain Pure**
   ```kotlin
   // ✅ Domain doesn't know about Spring, JPA, etc.
   data class Brand(val name: String) {
       init { require(name.isNotBlank()) }
   }
   ```

2. **Use Meaningful Names**
   ```kotlin
   // ✅ Clear intention
   interface CreateBrandUseCase
   class BrandAlreadyExistsException
   
   // ❌ Generic names  
   interface BrandService
   class BrandException
   ```

3. **Single Responsibility**
   ```kotlin
   // ✅ One responsibility per class
   class CreateBrandUseCaseImpl : CreateBrandUseCase
   class UpdateBrandUseCaseImpl : UpdateBrandUseCase
   
   // ❌ Multiple responsibilities
   class BrandManager {
       fun create() { }
       fun update() { }
       fun delete() { }
       fun sendEmail() { }
   }
   ```

4. **Fail Fast**
   ```kotlin
   // ✅ Validate early
   init {
       require(name.isNotBlank()) { "Name cannot be blank" }
   }
   
   // ❌ Lazy validation
   fun save() {
       if (name.isBlank()) throw Exception("Name cannot be blank")
   }
   ```

5. **Use Value Objects**
   ```kotlin
   // ✅ Type safety
   data class BrandId(val value: UUID)
   data class BrandName(val value: String) {
       init { require(value.isNotBlank()) }
   }
   
   // ❌ Primitive obsession
   fun findBrand(id: UUID): Brand  // Could pass wrong UUID
   ```

### ❌ Don'ts

1. **Don't Leak Implementation Details**
   ```kotlin
   // ❌ Domain knows about JPA
   data class Brand(
       @Id val id: UUID,
       @Column val name: String
   )
   
   // ✅ Clean domain
   data class Brand(val id: UUID, val name: String)
   ```

2. **Don't Create God Classes**
   ```kotlin
   // ❌ Too many responsibilities
   class BrandService {
       fun create() { }
       fun update() { }
       fun delete() { }
       fun sendEmail() { }
       fun generateReport() { }
       fun validateTax() { }
   }
   ```

3. **Don't Skip Input Validation**
   ```kotlin
   // ❌ No validation
   @PostMapping
   fun createBrand(@RequestBody request: CreateBrandRequest): BrandResponse
   
   // ✅ Proper validation
   @PostMapping  
   fun createBrand(@Valid @RequestBody request: CreateBrandRequest): BrandResponse
   ```

4. **Don't Use Generic Exceptions**
   ```kotlin
   // ❌ Generic exception
   throw RuntimeException("Something went wrong")
   
   // ✅ Specific exception
   throw BrandNotFoundException(brandId)
   ```

---

## 🔄 Migration Guide (From Traditional to Hexagonal)

### Step 1: Extract Domain Models
```kotlin
// Before: Anemic entities
@Entity
class Brand {
    var name: String = ""
    var active: Boolean = true
}

// After: Rich domain models
data class Brand(
    val name: String,
    val active: Boolean = true
) {
    init { require(name.isNotBlank()) }
    fun deactivate(): Brand = copy(active = false)
}
```

### Step 2: Create Use Cases
```kotlin
// Before: Fat controllers/services
@Service
class BrandService {
    fun createBrand(name: String): Brand {
        // Business logic mixed with persistence
        if (repository.existsByName(name)) throw Exception()
        return repository.save(Brand(name))
    }
}

// After: Focused use cases
@UseCase  
class CreateBrandUseCaseImpl(
    private val repository: BrandRepositoryPort
) : CreateBrandUseCase {
    override fun execute(command: CreateBrandCommand): Brand {
        if (repository.existsByName(command.name)) {
            throw BrandAlreadyExistsException(command.name)
        }
        return repository.save(Brand(name = command.name))
    }
}
```

### Step 3: Define Ports
```kotlin
// Before: Direct JPA dependency
class BrandService(private val repository: BrandJpaRepository)

// After: Abstract port
interface BrandRepositoryPort {
    fun save(brand: Brand): Brand
    fun existsByName(name: String): Boolean
}

class CreateBrandUseCase(private val repository: BrandRepositoryPort)
```

### Step 4: Implement Adapters
```kotlin
// JPA Adapter
@Adapter
class BrandRepositoryAdapter(
    private val jpaRepository: BrandJpaRepository,
    private val mapper: BrandEntityMapper
) : BrandRepositoryPort {
    
    override fun save(brand: Brand): Brand {
        val entity = mapper.toEntity(brand)
        val saved = jpaRepository.save(entity)
        return mapper.toDomain(saved)
    }
}
```

---

## 🎓 Learning Resources

### 📚 Books
- **"Clean Architecture"** by Robert C. Martin
- **"Domain-Driven Design"** by Eric Evans
- **"Implementing Domain-Driven Design"** by Vaughn Vernon
- **"Building Microservices"** by Sam Newman

### 🌐 Articles & Blogs
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/) - Alistair Cockburn
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) - Uncle Bob
- [DDD Reference](https://domainlanguage.com/ddd/reference/) - Eric Evans

### 🛠️ Tools & Frameworks
- **ArchUnit**: Testing architectural constraints
- **Testcontainers**: Integration testing with real databases
- **Micrometer**: Application metrics
- **Spring Boot Actuator**: Production monitoring

---

## 🎯 Conclusion

Esta arquitetura hexagonal oferece:

### 🎯 **Benefícios Técnicos**
- **Testabilidade**: Cada componente pode ser testado isoladamente
- **Flexibilidade**: Fácil trocar implementações (banco, cache, APIs)
- **Manutenibilidade**: Código organizado e responsabilidades claras
- **Escalabilidade**: Fácil adicionar novos casos de uso

### 🚀 **Benefícios de Negócio**  
- **Time to Market**: Desenvolvimento mais rápido com código limpo
- **Qualidade**: Menos bugs com testes abrangentes
- **Evolução**: Fácil adaptar a mudanças de requisitos
- **Performance**: Otimizações targeted sem afetar outras camadas

### 🎨 **Princípios Fundamentais**
1. **Domain First**: Domínio é o centro da aplicação
2. **Dependency Inversion**: Dependências apontam para dentro
3. **Interface Segregation**: Contratos pequenos e focados
4. **Single Responsibility**: Cada classe tem uma responsabilidade
5. **Fail Fast**: Validações no momento da criação

Este guia serve como referência completa para entender, implementar e evoluir sistemas usando arquitetura hexagonal com Kotlin e Spring Boot. A estrutura é flexível e pode ser adaptada conforme as necessidades específicas de cada projeto.

**🚀 Próximos Passos:**
1. Implementar os casos de uso restantes
2. Adicionar testes para 100% de cobertura
3. Configurar CI/CD pipeline
4. Implementar observabilidade avançada
5. Adicionar cache distribuído
6. Integrar com sistemas externos