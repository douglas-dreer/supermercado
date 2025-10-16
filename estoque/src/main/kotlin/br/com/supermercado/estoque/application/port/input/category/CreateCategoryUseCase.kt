package br.com.supermercado.estoque.application.port.input.category

import br.com.supermercado.estoque.application.dto.command.CreateCategoryCommand
import br.com.supermercado.estoque.domain.model.Category

interface CreateCategoryUseCase {
    fun execute(command: CreateCategoryCommand): Category
    fun executeValidate(category: Category)
}