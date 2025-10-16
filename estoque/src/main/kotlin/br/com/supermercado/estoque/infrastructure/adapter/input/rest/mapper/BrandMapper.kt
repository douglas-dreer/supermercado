package br.com.supermercado.estoque.infrastructure.adapter.input.rest.mapper

import br.com.supermercado.estoque.application.dto.command.CreateBrandCommand
import br.com.supermercado.estoque.application.dto.command.UpdateBrandCommand
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.CreateBrandRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.UpdateBrandRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.BrandResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class BrandMapper {
    fun toCommander(createdRequest: CreateBrandRequest): CreateBrandCommand {
        return CreateBrandCommand(
            name = createdRequest.name,
            description = createdRequest.description
        )
    }

    fun toCommander(brandId: UUID, updateRequest: UpdateBrandRequest): UpdateBrandCommand {
        return UpdateBrandCommand(
            id = brandId,
            name = updateRequest.name,
            description = updateRequest.description
        )
    }

    fun toResponse(domain: Brand): BrandResponse {
        return BrandResponse(
            id = domain.id!!,
            name = domain.name,
            description = domain.description,
            active = domain.active,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }

}