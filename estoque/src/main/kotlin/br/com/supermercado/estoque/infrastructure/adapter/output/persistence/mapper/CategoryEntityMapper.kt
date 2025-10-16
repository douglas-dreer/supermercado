package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.mapper

import br.com.supermercado.estoque.domain.model.Category
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.CategoryEntity
import org.springframework.stereotype.Component

@Component
class CategoryEntityMapper {

    fun toEntity(category: Category): CategoryEntity {
        return CategoryEntity(
            id = category.id,
            name = category.name,
            description = category.description,
            active = category.active,
            createdAt = category.createdAt,
            updatedAt = category.updatedAt
        )
    }

    fun toDomain(entity: CategoryEntity): Category {
        return Category(
            id = entity.id!!,
            name = entity.name,
            description = entity.description ?: "",
            active = entity.active,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}