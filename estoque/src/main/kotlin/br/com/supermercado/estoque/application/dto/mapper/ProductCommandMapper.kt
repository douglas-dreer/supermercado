package br.com.supermercado.estoque.application.dto.mapper

import br.com.supermercado.estoque.application.dto.command.CreateProductCommand
import br.com.supermercado.estoque.application.dto.command.UpdateProductCommand
import br.com.supermercado.estoque.domain.model.Product
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Component
class ProductCommandMapper {
    fun toDomain(command: CreateProductCommand): Product {
        return Product(
            id = null,
            name = command.name,
            description = command.description,
            barcode = command.barcode,
            price = command.price,
            stockQuantity = command.stockQuantity,
            minStockQuantity = command.minStockQuantity,
            brandId = command.brandId,
            category = command.category,
            active = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    fun toDomain(command: UpdateProductCommand): Product {
      return Product(
          id = command.id,
          name = command.name!!,
          description = command.description,
          price = command.price ?: BigDecimal.ZERO,
          stockQuantity = command.stockQuantity ?: 0,
          minStockQuantity = command.minStockQuantity ?: 0,
          brandId = command.brandId ?: UUID.randomUUID(),
          category = command.category ?: "",
          active = true,
          updatedAt = LocalDateTime.now()
      )
    }
}