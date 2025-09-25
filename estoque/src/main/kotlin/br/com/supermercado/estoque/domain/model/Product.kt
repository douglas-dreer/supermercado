package br.com.supermercado.estoque.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Product(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val barcode: String,
    val price: BigDecimal,
    val stockQuantity: Int = 0,
    val minStockQuantity: Int = 0,
    val brandId: UUID,
    val category: String,
    val active: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(name.isNotBlank()) { "Product name cannot be blank" }
        require(name.length <= 200) { "Product name cannot exceed 200 characters" }
        require(barcode.isNotBlank()) { "Product barcode cannot be blank" }
        require(price > BigDecimal.ZERO) { "Product price must be positive" }
        require(stockQuantity >= 0) { "Stock quantity cannot be negative" }
        require(minStockQuantity >= 0) { "Minimum stock quantity cannot be negative" }
        require(category.isNotBlank()) { "Product category cannot be blank" }
    }

    fun update(
        name: String? = null,
        description: String? = null,
        barcode: String? = null,
        price: BigDecimal? = null,
        stockQuantity: Int? = null,
        minStockQuantity: Int? = null,
        brandId: UUID? = null,
        category: String? = null,
        active: Boolean? = null
    ): Product {
        return copy(
            name = name ?: this.name,
            description = description ?: this.description,
            barcode = barcode ?: this.barcode,
            price = price ?: this.price,
            stockQuantity = stockQuantity ?: this.stockQuantity,
            minStockQuantity = minStockQuantity ?: this.minStockQuantity,
            brandId = brandId ?: this.brandId,
            category = category ?: this.category,
            active = active ?: this.active,
            updatedAt = LocalDateTime.now()
        )
    }

    fun isLowStock(): Boolean = stockQuantity <= minStockQuantity
}
