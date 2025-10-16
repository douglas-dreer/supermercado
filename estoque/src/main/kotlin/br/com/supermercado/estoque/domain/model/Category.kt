package br.com.supermercado.estoque.domain.model

import java.time.LocalDateTime
import java.util.*

data class Category(
    val id: UUID,
    val name: String,
    val description: String,
    val active: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)