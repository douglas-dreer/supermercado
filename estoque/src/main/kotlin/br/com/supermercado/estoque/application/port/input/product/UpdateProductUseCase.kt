package br.com.supermercado.estoque.application.port.input.product

import br.com.supermercado.estoque.application.dto.command.UpdateProductCommand
import br.com.supermercado.estoque.domain.model.Product

interface UpdateProductUseCase {
    fun execute(command: UpdateProductCommand): Product
}
