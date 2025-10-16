package br.com.supermercado.estoque.application.validate.product

import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import br.com.supermercado.estoque.domain.exception.BrandNotFoundException
import br.com.supermercado.estoque.domain.exception.ProductAlreadyExistsException
import br.com.supermercado.estoque.domain.model.Product
import br.com.supermercado.estoque.domain.validation.ProductValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.ProductRepositoryAdapter
import org.springframework.stereotype.Component

@Component
@CreateValidation
class CreateProductValidator(
    private val productRepository: ProductRepositoryAdapter,
    private val brandRepository: BrandRepositoryAdapter
): ProductValidator(), ValidationStrategy<Product> {
    override fun execute(item: Product) {

        if (super.verifyIfExistNameAlrighRegistred(item.name, productRepository)) {
            throw ProductAlreadyExistsException(item.name)
        }

        if (!super.verifyIfBrandIdExist(item.brandId, brandRepository)){
            throw BrandNotFoundException(item.brandId)
        }
    }
}