package br.com.supermercado.estoque.integration.validate

import br.com.supermercado.estoque.application.port.input.brand.validate.brand.UpdateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.model.Brand
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
import java.util.*

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("it")
class UpdateBrandValidateIT {

    @Autowired
    private lateinit var updateBrandValidate: UpdateBrandValidate

    @Autowired
    private lateinit var brandRepository: BrandRepositoryPort

    private val brandProvider = BrandProvider()
    private lateinit var brands: Set<Brand>

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
        brands = brandRepository.saveAll(
            brandProvider.createBrandList(3)
        )

    }

    @Test
    fun `should not throw for update`() {
        val brand = brands.first().copy(name = "Nestlé")
        assertDoesNotThrow { updateBrandValidate.execute(brand) }
    }

    @Test
    fun `should throw BrandNotFoundException when id doesnt exists in database`() {
        val brandWithIdDoesNotExists = brandProvider.createABrand(UUID.randomUUID())
        assertThrows<BrandNotFoundException> { updateBrandValidate.execute(brandWithIdDoesNotExists) }
    }

    @Test
    fun `should throw BrandAlreadyExistsException when brand name already exists in database`() {
        val brandWithNameAlreadyExist = brands.first()

        assertThrows<BrandAlreadyExistsException> { updateBrandValidate.execute(brandWithNameAlreadyExist) }
    }
}
