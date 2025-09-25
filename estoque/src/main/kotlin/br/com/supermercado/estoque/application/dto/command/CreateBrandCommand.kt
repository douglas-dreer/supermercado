package br.com.supermercado.estoque.application.dto.command

data class CreateBrandCommand(
    val name: String,
    val description: String?
)