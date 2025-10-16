package br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.Size

data class UpdateBrandRequest(
    @field:Size(max = 100, message = "Name must not exceed 100 characters")
    val name: String?,

    @field:Size(max = 500, message = "Description must not exceed 500 characters")
    val description: String?
)