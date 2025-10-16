package br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response

import java.time.LocalDateTime
import java.util.*

data class CategoryResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)