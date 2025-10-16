package br.com.supermercado.estoque.application.validate.brand

import br.com.supermercado.estoque.application.port.input.brand.validate.brand.CreateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import br.com.supermercado.estoque.domain.exception.BrandAlreadyExistsException

import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.domain.validation.BrandValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import org.springframework.stereotype.Component
import java.util.*

@Component
@CreateValidation
class CreateBrandValidate(
    private val repository: BrandRepositoryAdapter
): BrandValidator(), ValidationStrategy<Brand> {
    override fun execute(item: Brand) {
        if (super.isNameRegisteredForOtherBrand(item.name, repository)) {
            throw BrandAlreadyExistsException(item.name)
        }
    }
}