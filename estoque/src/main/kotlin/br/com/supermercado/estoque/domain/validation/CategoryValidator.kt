package br.com.supermercado.estoque.domain.validation

import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.CategoryRepositoryAdapter
import java.util.*

abstract class CategoryValidator {
    fun verifyIfExistId(categoryId: UUID, repository: CategoryRepositoryAdapter): Boolean {
        return repository.existsById(categoryId)
    }

    fun isNameRegisteredForOtherCategory(categoryName: String, repository: CategoryRepositoryAdapter): Boolean {
        return repository.existsByName(categoryName)
    }
}