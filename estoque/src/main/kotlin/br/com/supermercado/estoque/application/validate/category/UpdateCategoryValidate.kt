package br.com.supermercado.estoque.application.validate.category

import br.com.supermercado.estoque.domain.exception.BrandAlreadyExistsException
import br.com.supermercado.estoque.domain.exception.BrandNotFoundException
import br.com.supermercado.estoque.domain.exception.InvalidDataException
import br.com.supermercado.estoque.domain.model.Category
import br.com.supermercado.estoque.domain.validation.CategoryValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.CategoryRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.common.annotation.UpdateValidation
import org.springframework.stereotype.Component

@Component
@UpdateValidation
class UpdateCategoryValidate(
    private val repository: CategoryRepositoryAdapter
) : CategoryValidator(), ValidationStrategy<Category> {
    companion object {
        val MSG_ID_IS_REQUIRED = "Brand ID é obrigatório para operação de atualização"
    }

    override fun execute(item: Category) {
        val categoryId = item.id ?: throw InvalidDataException(MSG_ID_IS_REQUIRED)

        require(verifyIfExistId(categoryId, repository)) {
            throw BrandNotFoundException(categoryId)
        }

        if (isNameRegisteredForOtherCategory(item.name, repository)) {
            throw BrandAlreadyExistsException(item.name)
        }
    }
}