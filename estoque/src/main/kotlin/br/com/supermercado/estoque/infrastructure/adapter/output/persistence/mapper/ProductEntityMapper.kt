package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.mapper

import br.com.supermercado.estoque.domain.model.Product
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.ProductEntity
import org.springframework.stereotype.Component

@Component
class ProductEntityMapper {

    fun toEntity(product: Product): ProductEntity {
        return ProductEntity(
            id = product.id,
            name = product.name,
            description = product.description,
            barcode = product.barcode ?: "",
            price = product.price,
            stockQuantity = product.stockQuantity,
            minStockQuantity = product.minStockQuantity,
            brandId = product.brandId,
            category = product.category,
            active = product.active,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )
    }

    fun toDomain(entity: ProductEntity): Product {
        return Product(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            barcode = entity.barcode,
            price = entity.price,
            stockQuantity = entity.stockQuantity,
            minStockQuantity = entity.minStockQuantity,
            brandId = entity.brandId,
            category = entity.category,
            active = entity.active,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
