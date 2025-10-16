package br.com.supermercado.estoque.application.validate.brand

import br.com.supermercado.estoque.domain.exception.BusinessException
import br.com.supermercado.estoque.domain.exception.NotFoundException
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.domain.validation.BrandValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.common.annotation.UpdateValidation
import br.com.supermercado.estoque.infrastructure.common.constant.ErrorMessages
import org.springframework.stereotype.Component

@Component
@UpdateValidation
class UpdateBrandValidate(
    private val repository: BrandRepositoryAdapter
):  BrandValidator(), ValidationStrategy<Brand> {

    override fun execute(item: Brand) {
        val brandId = item.id ?: throw BusinessException(ErrorMessages.BRAND_REQUIRED_ID)

        require(verifyIfExistId(brandId, repository)) {
            throw NotFoundException(ErrorMessages.BRAND_NOT_FOUND)
        }

        if (isNameRegisteredForOtherBrand(item.name, repository)) {
            throw BusinessException(ErrorMessages.BRAND_ALREADY_EXISTS)
        }
    }
}