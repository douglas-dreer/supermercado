# 🏪 Sistema de Estoque - Supermercado

Este é um sistema de gerenciamento de estoque desenvolvido em **Kotlin** com **Spring Boot**, seguindo os princípios da
**Arquitetura Hexagonal (Ports and Adapters)**.

## 🏗️ Arquitetura

O projeto segue a **Arquitetura Hexagonal**, garantindo:

- **Separação clara de responsabilidades**
- **Fácil testabilidade**
- **Flexibilidade para mudanças**
- **Independência de frameworks externos**

### 📁 Estrutura das Camadas

```
├── domain/          # Regras de negócio e entidades
├── application/     # Casos de uso e ports
└── infrastructure/ # Implementações e adaptadores
```

## 🚀 Tecnologias Utilizadas

- **Kotlin 1.9.20**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL / H2**
- **Flyway** (Migrações)
- **Swagger/OpenAPI**
- **JUnit 5 + MockK**
- **Caffeine Cache**

## 📋 Funcionalidades

### 🏷️ Marcas (Brands)

- ✅ Criar nova marca
- ✅ Listar marcas com paginação
- ✅ Buscar marca por ID
- ✅ Atualizar marca
- ✅ Excluir marca

### 📦 Produtos (Products)

- ✅ Criar novo produto
- ✅ Listar produtos com paginação
- ✅ Buscar produto por ID
- ✅ Buscar produto por código de barras
- ✅ Atualizar produto
- ✅ Excluir produto
- ✅ Controle de estoque mínimo

## 🛠️ Como Executar

### Pré-requisitos

- Java 17+
- Docker (opcional para PostgreSQL)

### 1. Clone o repositório

```bash
git clone <repository-url>
cd estoque
```

### 2. Execute com H2 (ambiente de desenvolvimento)

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### 3. Execute com PostgreSQL

```bash
# Inicie o PostgreSQL com Docker
docker run --name postgres-estoque -e POSTGRES_DB=estoque -e POSTGRES_USER=estoque -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres:15

# Execute a aplicação
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## 📖 Documentação da API

Após iniciar a aplicação, acesse:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Console H2 (ambiente dev)

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: *(vazio)*

## 🧪 Testes

### Executar todos os testes

```bash
./gradlew test
```

### Executar testes com relatório

```bash
./gradlew test jacocoTestReport
```

### Tipos de Testes Incluídos

- **Testes Unitários**: Use cases, domínio e repositórios
- **Testes de Integração**: Controllers e banco de dados
- **Testes de Contrato**: Validação de APIs

## 📊 Monitoramento

A aplicação inclui endpoints do **Spring Actuator**:

- **Health**: `/actuator/health`
- **Metrics**: `/actuator/metrics`
- **Info**: `/actuator/info`

## 🔧 Configuração

### Profiles Disponíveis

- `dev`: H2 em memória, logs detalhados
- `prod`: PostgreSQL, logs otimizados
- `test`: H2 para testes, configurações específicas

### Variáveis de Ambiente (Produção)

```bash
DATABASE_URL=jdbc:postgresql://localhost:5432/estoque
DATABASE_USERNAME=estoque
DATABASE_PASSWORD=password
SERVER_PORT=8080
```

## 📝 Exemplos de API

### Criar Marca

```bash
curl -X POST http://localhost:8080/api/v1/brands \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Coca-Cola",
    "description": "Bebidas e refrigerantes"
  }'
```

### Criar Produto

```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Coca-Cola 350ml",
    "description": "Refrigerante de cola",
    "barcode": "7894900011517",
    "price": 4.50,
    "stockQuantity": 100,
    "minStockQuantity": 10,
    "brandId": "uuid-da-marca",
    "category": "Bebidas"
  }'
```

### Listar Produtos com Paginação

```bash
curl "http://localhost:8080/api/v1/products?page=0&size=10&sort=name&direction=ASC"
```

## 🏛️ Padrões Utilizados

### Domain-Driven Design (DDD)

- **Entidades** ricas com comportamentos
- **Value Objects** para dados imutáveis
- **Repositórios** para persistência
- **Serviços de domínio** para lógicas complexas

### SOLID Principles

- **Single Responsibility**: Cada classe tem uma responsabilidade
- **Open/Closed**: Extensível sem modificar código existente
- **Liskov Substitution**: Interfaces bem definidas
- **Interface Segregation**: Ports específicos e coesos
- **Dependency Inversion**: Dependências abstraídas por interfaces

### Clean Code

- **Nomenclatura expressiva**
- **Funções pequenas e focadas**
- **Testes abrangentes**
- **Documentação clara**

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a MIT License - veja o arquivo [LICENSE](LICENSE) para detalhes.

## ✨ Próximas Funcionalidades

- [ ] Cache distribuído com Redis
- [ ] Mensageria com Kafka
- [ ] Métricas com Micrometer
- [ ] Integração com serviços externos
- [ ] API de relatórios
- [ ] Autenticação e autorização
- [ ] Versionamento de API
- [ ] Rate limiting

---

Desenvolvido com ❤️ usando Kotlin e Spring Boot