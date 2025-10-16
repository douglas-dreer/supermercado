package br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.util.UUID

data class UpdateProductRequest(
    @field:Size(max = 200, message = "Name must not exceed 200 characters")
    val name: String?,

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String?,

    @field:Size(max = 50, message = "Barcode must not exceed 50 characters")
    val barcode: String?,

    @field:Positive(message = "Price must be positive")
    val price: BigDecimal?,

    @field:Min(value = 0, message = "Stock quantity cannot be negative")
    val stockQuantity: Int?,

    @field:Min(value = 0, message = "Minimum stock quantity cannot be negative")
    val minStockQuantity: Int?,

    val brandId: UUID?,

    @field:Size(max = 100, message = "Category must not exceed 100 characters")
    val category: String?,

    val active: Boolean?
) {
    init {
        require(name.isNullOrBlank(), { "O campo nome, não pode ser nulo ou vazio" })
        require(category.isNullOrBlank(), { "O campo categoria não pode ser nulo ou vazio" })
        requireNotNull(active, { "Campo active não pode ser nulo"})
    }
}