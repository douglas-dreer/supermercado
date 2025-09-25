package br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class ProductResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val barcode: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val minStockQuantity: Int,
    val brandId: UUID,
    val category: String,
    val active: Boolean,
    val isLowStock: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)