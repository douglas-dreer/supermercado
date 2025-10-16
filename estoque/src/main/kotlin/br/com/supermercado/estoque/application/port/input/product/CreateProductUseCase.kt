package br.com.supermercado.estoque.application.port.input.product

import br.com.supermercado.estoque.application.dto.command.CreateProductCommand
import br.com.supermercado.estoque.domain.model.Product

interface CreateProductUseCase {
    fun execute(command: CreateProductCommand): Product
    fun executeValidate(product: Product)
}