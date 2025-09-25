package br.com.supermercado.estoque.application.validate

import br.com.supermercado.estoque.application.port.input.brand.validate.DeleteBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.exeception.BrandNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DeleteBrandValidateImpl(
    private val repository: BrandRepositoryPort
): DeleteBrandValidate {
    override fun execute(brandId: UUID) {
        verifyIfBrandIdExists(brandId)
    }

    private fun verifyIfBrandIdExists(brandId: UUID) {
        if (repository.existsById(brandId).not()) {
            throw BrandNotFoundException(brandId)
        }
    }
}