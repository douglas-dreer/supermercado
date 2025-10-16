package br.com.supermercado.estoque.application.dto.command

import java.util.*

data class UpdateCategoryCommand(
    val id: UUID,
    val name: String?,
    val description: String?,
)