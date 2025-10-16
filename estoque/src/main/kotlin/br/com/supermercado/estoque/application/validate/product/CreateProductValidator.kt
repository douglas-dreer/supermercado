package br.com.supermercado.estoque.application.validate.product

import br.com.supermercado.estoque.domain.exception.BusinessException
import br.com.supermercado.estoque.domain.exception.NotFoundException
import br.com.supermercado.estoque.domain.model.Product
import br.com.supermercado.estoque.domain.validation.ProductValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.ProductRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import br.com.supermercado.estoque.infrastructure.common.constant.ErrorMessages
import org.springframework.stereotype.Component

@Component
@CreateValidation
class CreateProductValidator(
    private val productRepository: ProductRepositoryAdapter,
    private val brandRepository: BrandRepositoryAdapter
): ProductValidator(), ValidationStrategy<Product> {
    override fun execute(item: Product) {

        if (super.verifyIfExistNameAlrighRegistred(item.name, productRepository)) {
            throw BusinessException(ErrorMessages.PRODUCT_ALREADY_EXISTS)
        }

        if (!super.verifyIfBrandIdExist(item.brandId, brandRepository)){
            throw NotFoundException(ErrorMessages.PRODUCT_NOT_FOUND)
        }
    }
}