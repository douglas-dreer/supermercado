package br.com.supermercado.estoque.application.dto.command

import java.util.*

data class UpdateBrandCommand(
    val id: UUID,
    val name: String?,
    val description: String?,
)