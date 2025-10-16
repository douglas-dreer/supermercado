package br.com.supermercado.estoque.application.usecase.category

import br.com.supermercado.estoque.application.dto.command.CreateCategoryCommand
import br.com.supermercado.estoque.application.dto.mapper.CategoryCommandMapper
import br.com.supermercado.estoque.application.port.input.category.CreateCategoryUseCase
import br.com.supermercado.estoque.application.port.output.CategoryRepositoryPort
import br.com.supermercado.estoque.domain.model.Category
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase

@UseCase
class CreateCategoryUseCaseImpl(
    private val repository: CategoryRepositoryPort,
    private val converter: CategoryCommandMapper,
    @CreateValidation
    private val validators: List<ValidationStrategy<Category>>
) : CreateCategoryUseCase {
    override fun execute(command: CreateCategoryCommand): Category {
        val category = converter.toDomain(command)
        executeValidate(category)
        return repository.save(category)
    }

    override fun executeValidate(category: Category) {
        validators.forEach { it.execute(category) }
    }
}


