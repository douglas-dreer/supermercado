package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.mapper

import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.BrandEntity
import org.springframework.stereotype.Component

@Component
class BrandEntityMapper {

    fun toEntity(brand: Brand): BrandEntity {
        return BrandEntity(
            id = brand.id,
            name = brand.name,
            description = brand.description,
            active = brand.active,
            createdAt = brand.createdAt,
            updatedAt = brand.updatedAt
        )
    }

    fun toDomain(entity: BrandEntity): Brand {
        return Brand(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            active = entity.active,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}