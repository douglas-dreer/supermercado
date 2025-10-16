package br.com.supermercado.estoque.application.validate.category

import br.com.supermercado.estoque.domain.exception.CategoryAlreadyExistException
import br.com.supermercado.estoque.domain.model.Category
import br.com.supermercado.estoque.domain.validation.CategoryValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.CategoryRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import org.springframework.stereotype.Component

@Component
@CreateValidation
class CreateCategoryValidate(
    private val repository: CategoryRepositoryAdapter
) : CategoryValidator(), ValidationStrategy<Category> {
    override fun execute(item: Category) {
        if (super.isNameRegisteredForOtherCategory(item.name, repository)) {
            throw CategoryAlreadyExistException(item.name)
        }
    }
}