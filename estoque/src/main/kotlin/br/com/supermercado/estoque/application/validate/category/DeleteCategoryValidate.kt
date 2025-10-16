package br.com.supermercado.estoque.application.validate.category

import br.com.supermercado.estoque.domain.exception.NotFoundException
import br.com.supermercado.estoque.domain.validation.CategoryValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.CategoryRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.common.annotation.DeleteValidation
import br.com.supermercado.estoque.infrastructure.common.constant.ErrorMessages
import org.springframework.stereotype.Component
import java.util.*

@Component
@DeleteValidation
class DeleteCategoryValidate(
    private val repository: CategoryRepositoryAdapter
) : CategoryValidator(), ValidationStrategy<UUID> {
    override fun execute(item: UUID) {
        require(verifyIfExistId(item, repository)) {
            throw NotFoundException(ErrorMessages.CATEGORY_NOT_FOUND)
        }
    }
}