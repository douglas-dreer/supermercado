package br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateBrandRequest(
    @field:NotNull(message = "Name is required")
    @field:NotBlank(message = "Name is required")
    @field:Size(max = 100, message = "Name must not exceed 100 characters")
    var name: String,

    @field:Size(max = 500, message = "Description must not exceed 500 characters")
    val description: String?
)