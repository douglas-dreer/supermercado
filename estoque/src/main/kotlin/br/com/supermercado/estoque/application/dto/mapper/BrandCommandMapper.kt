package br.com.supermercado.estoque.application.dto.mapper

import br.com.supermercado.estoque.application.dto.command.CreateBrandCommand
import br.com.supermercado.estoque.application.dto.command.UpdateBrandCommand
import br.com.supermercado.estoque.domain.model.Brand
import org.springframework.stereotype.Component

@Component
class BrandCommandMapper {
    fun toDomain(command: CreateBrandCommand): Brand {
        return Brand(name = command.name, description = command.description)
    }

    fun toDomain(command: UpdateBrandCommand): Brand {
        return Brand(
            id = command.id,
            name = command.name ?: "",
            description = command.description,
            active = command.active == true
        )
    }
}