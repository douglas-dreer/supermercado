package br.com.supermercado.estoque.application.port.input.category

import br.com.supermercado.estoque.application.dto.command.UpdateProductCommand
import br.com.supermercado.estoque.domain.model.Product

interface UpdateCategoryUseCase {
    fun execute(command: UpdateProductCommand): Product
}
