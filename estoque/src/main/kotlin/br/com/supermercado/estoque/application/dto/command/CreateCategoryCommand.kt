package br.com.supermercado.estoque.application.dto.command

data class CreateCategoryCommand(
    val name: String,
    val description: String?
)