package br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotNull

import java.math.BigDecimal
import java.util.UUID

data class CreateProductRequest(
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 200, message = "Name must not exceed 200 characters")
    val name: String,

    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    val description: String?,

    @field:NotBlank(message = "Barcode is required")
    @field:Size(max = 50, message = "Barcode must not exceed 50 characters")
    val barcode: String,

    @field:NotNull(message = "Price is required")
    @field:Positive(message = "Price must be positive")
    val price: BigDecimal,

    @field:NotNull(message = "Stock quantity is required")
    @field:Min(value = 0, message = "Stock quantity cannot be negative")
    val stockQuantity: Int,

    @field:NotNull(message = "Minimum stock quantity is required")
    @field:Min(value = 0, message = "Minimum stock quantity cannot be negative")
    val minStockQuantity: Int,

    @field:NotNull(message = "Brand ID is required")
    val brandId: UUID,

    @field:NotBlank(message = "Category is required")
    @field:Size(max = 100, message = "Category must not exceed 100 characters")
    val category: String
)