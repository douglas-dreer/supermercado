package br.com.supermercado.estoque.infrastructure.adapter.input.rest.mapper

import br.com.supermercado.estoque.application.dto.command.CreateProductCommand
import br.com.supermercado.estoque.application.dto.command.UpdateProductCommand
import br.com.supermercado.estoque.domain.model.Product
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.CreateProductRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.UpdateProductRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.ProductResponse
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ProductMapper {
    fun toCommander(createRequest: CreateProductRequest): CreateProductCommand {
        return CreateProductCommand(
            name = createRequest.name,
            description = createRequest.description,
            barcode = createRequest.barcode,
            price = createRequest.price,
            stockQuantity = createRequest.stockQuantity,
            minStockQuantity = createRequest.minStockQuantity,
            brandId = createRequest.brandId,
            category = createRequest.category
        )
    }

    fun toCommander(productId: UUID, updateRequest: UpdateProductRequest): UpdateProductCommand {
        return UpdateProductCommand(
            id = productId,
            name = updateRequest.name,
            description = updateRequest.description,
            barcode = updateRequest.barcode,
            price = updateRequest.price,
            stockQuantity = updateRequest.stockQuantity,
            minStockQuantity = updateRequest.minStockQuantity,
            brandId = updateRequest.brandId,
            category = updateRequest.category,
            active = updateRequest.active,
        )
    }

    fun toResponse(domain: Product): ProductResponse {
        return ProductResponse(
            id = domain.id!! ,
            name = domain.name,
            description = domain.description,
            barcode = domain.barcode ?: "",
            price = domain.price,
            stockQuantity = domain.stockQuantity,
            minStockQuantity = domain.minStockQuantity,
            brandId = domain.brandId,
            category = domain.category,
            active = domain.active,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt,
            )
    }
}