package br.com.supermercado.estoque.infrastructure.adapter.input.rest.mapper

import br.com.supermercado.estoque.application.dto.command.CreateCategoryCommand
import br.com.supermercado.estoque.application.dto.command.UpdateCategoryCommand
import br.com.supermercado.estoque.domain.model.Category
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.CreateCategoryRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.UpdateCategoryRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.CategoryResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class CategoryMapper {
    fun toCommander(createCategoryRequest: CreateCategoryRequest): CreateCategoryCommand = CreateCategoryCommand(
        name = createCategoryRequest.name,
        description = createCategoryRequest.description
    )

    fun toCommander(categoryId: UUID, updateCategoryRequest: UpdateCategoryRequest): UpdateCategoryCommand =
        UpdateCategoryCommand(
            id = categoryId,
            name = updateCategoryRequest.name,
            description = updateCategoryRequest.description
        )

    fun toResponse(domain: Category): CategoryResponse = CategoryResponse(
        id = domain.id!!,
        name = domain.name,
        description = domain.description,
        active = domain.active,
        createdAt = domain.createdAt,
        updatedAt = domain.updatedAt
    )
}