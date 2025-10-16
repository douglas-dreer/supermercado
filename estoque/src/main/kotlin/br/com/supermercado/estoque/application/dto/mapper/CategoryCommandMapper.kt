package br.com.supermercado.estoque.application.dto.mapper

import br.com.supermercado.estoque.application.dto.command.CreateCategoryCommand
import br.com.supermercado.estoque.application.dto.command.UpdateCategoryCommand
import br.com.supermercado.estoque.domain.model.Category
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CategoryCommandMapper {
    fun toDomain(command: CreateCategoryCommand): Category {
        return Category(
            id = null,
            name = command.name,
            description = command.description,
            active = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toDomain(command: UpdateCategoryCommand): Category {
        return Category(
            id = command.id,
            name = command.name ?: "",
            description = command.description ?: "",
            active = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

}