package br.com.supermercado.estoque.application.validate.category

import br.com.supermercado.estoque.domain.exception.BusinessException
import br.com.supermercado.estoque.domain.exception.NotFoundException
import br.com.supermercado.estoque.domain.model.Category
import br.com.supermercado.estoque.domain.validation.CategoryValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.CategoryRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.common.annotation.UpdateValidation
import br.com.supermercado.estoque.infrastructure.common.constant.ErrorMessages
import org.springframework.stereotype.Component

@Component
@UpdateValidation
class UpdateCategoryValidate(
    private val repository: CategoryRepositoryAdapter
) : CategoryValidator(), ValidationStrategy<Category> {


    override fun execute(item: Category) {
        val categoryId = item.id ?: throw BusinessException(ErrorMessages.CATEGORY_REQUIRED_ID)
        require(verifyIfExistId(categoryId, repository)) {
            throw NotFoundException(ErrorMessages.BRAND_NOT_FOUND)
        }

        if (isNameRegisteredForOtherCategory(item.name, repository)) {
            throw BusinessException(ErrorMessages.CATEGORY_ALREADY_EXISTS)
        }
    }
}