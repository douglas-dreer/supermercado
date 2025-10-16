package br.com.supermercado.estoque.application.validate.brand

import br.com.supermercado.estoque.domain.exception.BusinessException
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.domain.validation.BrandValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import br.com.supermercado.estoque.infrastructure.common.constant.ErrorMessages
import org.springframework.stereotype.Component

@Component
@CreateValidation
class CreateBrandValidate(
    private val repository: BrandRepositoryAdapter
): BrandValidator(), ValidationStrategy<Brand> {
    override fun execute(item: Brand) {
        if (super.isNameRegisteredForOtherBrand(item.name, repository)) {
            throw BusinessException(ErrorMessages.BRAND_ALREADY_EXISTS)
        }
    }
}