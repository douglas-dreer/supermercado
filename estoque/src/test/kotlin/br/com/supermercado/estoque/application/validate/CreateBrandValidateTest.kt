package br.com.supermercado.estoque.application.validate

import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.application.validate.brand.CreateBrandValidateImpl

import br.com.supermercado.estoque.infrastructure.providers.BrandProvider
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CreateBrandValidateTest {

    @MockK
    private lateinit var repository: BrandRepositoryPort

    @InjectMockKs
    private lateinit var validate: CreateBrandValidateImpl

    private val brandProvider = BrandProvider()

    @Test
    fun `should not throw exception when brand name does not exist`() {
        val brand = brandProvider.createABrand()
        every { repository.existsByName(any<String>()) } returns false

        assertDoesNotThrow {
            validate.execute(brand)
        }

        verify(exactly = 1) { repository.existsByName(any<String>()) }
    }

    @Test
    fun `should throw BrandAlreadyExistsException when brand name already exists`() {
        val brand = brandProvider.createABrand()
        every { repository.existsByName(any<String>()) } returns true


        assertThrows<BrandAlreadyExistsException> {
            validate.execute(brand)
        }

        verify(exactly = 1) { repository.existsByName(any<String>()) }
    }
}
