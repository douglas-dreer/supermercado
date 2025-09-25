package br.com.supermercado.estoque.application.dto.command

import java.math.BigDecimal
import java.util.UUID

data class CreateProductCommand(
    val name: String,
    val description: String?,
    val barcode: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val minStockQuantity: Int,
    val brandId: UUID,
    val category: String
)