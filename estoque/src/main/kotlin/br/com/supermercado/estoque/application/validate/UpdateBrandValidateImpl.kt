package br.com.supermercado.estoque.application.validate

import br.com.supermercado.estoque.application.port.input.brand.validate.UpdateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.exception.BrandAlreadyExistsException
import br.com.supermercado.estoque.domain.exception.BrandNotFoundException
import br.com.supermercado.estoque.domain.model.Brand
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UpdateBrandValidateImpl(
    private val repository: BrandRepositoryPort
): UpdateBrandValidate {
    override fun execute(brand: Brand) {
        verifyIfBrandIdExists(brand.id!!)
        verifyIfExistNameAlrighRegistered(brand.name)

    }

    private fun verifyIfBrandIdExists(brandId: UUID) {
        repository.findAll()
        if (repository.existsById(brandId).not()) {
            throw BrandNotFoundException(brandId)
        }
    }

    private fun verifyIfExistNameAlrighRegistered(brandName: String) {
        if (repository.existsByName(brandName)) {
            throw BrandAlreadyExistsException(brandName)
        }
    }

}