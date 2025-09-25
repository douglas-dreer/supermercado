package br.com.supermercado.estoque.integration.validate

import br.com.supermercado.estoque.application.port.input.brand.validate.CreateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.exception.BrandAlreadyExistsException
import br.com.supermercado.estoque.infrastructure.providers.BrandProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("it")
class CreateBrandValidateIT {

    @Autowired
    private lateinit var createBrandValidate: CreateBrandValidate

    @Autowired
    private lateinit var brandRepository: BrandRepositoryPort

    private val brandProvider = BrandProvider()

    companion object {
        @Container
        private val postgresqlContainer = PostgreSQLContainer("postgres:15-alpine")
            .withDatabaseName("supermercado-integration-test")


        @JvmStatic
        @DynamicPropertySource
        fun databaseProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") {
                postgresqlContainer.jdbcUrl + "?currentSchema=estoque"
            }
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
        }
    }

    @BeforeEach
    fun setup() {
        brandRepository.deleteAll()
    }

    @Test
    fun `should throw BrandAlreadyExistsException when brand name already exists in database`() {
        val existingBrand = brandProvider.createABrand(name = "Pepsi")
        brandRepository.save(existingBrand)

        val newBrandWithSameName = brandProvider.createABrand(name = existingBrand.name)

        assertThrows<BrandAlreadyExistsException> {
            createBrandValidate.execute(newBrandWithSameName)
        }
    }

    @Test
    fun `should not throw exception when brand name does not exist in database`() {
        val brand = brandProvider.createABrand()
        assertDoesNotThrow { createBrandValidate.execute(brand) }
    }
}
