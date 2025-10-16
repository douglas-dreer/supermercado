package br.com.supermercado.estoque.application.validate.brand

import br.com.supermercado.estoque.infrastructure.common.annotation.UpdateValidation
import br.com.supermercado.estoque.domain.exception.BrandAlreadyExistsException
import br.com.supermercado.estoque.domain.exception.BrandNotFoundException
import br.com.supermercado.estoque.domain.exception.InvalidDataException
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.domain.validation.BrandValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import org.springframework.stereotype.Component

@Component
@UpdateValidation
class UpdateBrandValidate(
    private val repository: BrandRepositoryAdapter
):  BrandValidator(), ValidationStrategy<Brand> {
    companion object {
        val MSG_ID_IS_REQUIRED = "Brand ID é obrigatório para operação de atualização"
    }
    override fun execute(item: Brand) {
        val brandId = item.id ?: throw InvalidDataException(MSG_ID_IS_REQUIRED)

        require(verifyIfExistId(brandId, repository)) {
            throw BrandNotFoundException(brandId)
        }

        if (isNameRegisteredForOtherBrand(item.name, repository)) {
            throw BrandAlreadyExistsException(item.name)
        }
    }
}