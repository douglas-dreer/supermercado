package br.com.supermercado.estoque.application.dto.command

import java.util.UUID

data class UpdateBrandCommand(
    val id: UUID,
    val name: String?,
    val description: String?,
    val active: Boolean?
)