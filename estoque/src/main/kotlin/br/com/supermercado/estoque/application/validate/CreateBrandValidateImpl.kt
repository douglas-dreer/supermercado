package br.com.supermercado.estoque.application.validate

import br.com.supermercado.estoque.application.port.input.brand.validate.CreateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.exeception.BrandAlreadyExistsException
import br.com.supermercado.estoque.domain.model.Brand
import org.springframework.stereotype.Component

@Component
class CreateBrandValidateImpl(
    private val repository: BrandRepositoryPort
): CreateBrandValidate {
    override fun execute(brand: Brand) {
        if (verifyIfExistNameAlrighRegistered(brand.name)) {
            throw BrandAlreadyExistsException(brand.name)
        }
    }

    private fun verifyIfExistNameAlrighRegistered(brandName: String): Boolean {
        return repository.existsByName(brandName)
    }
}