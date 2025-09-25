package br.com.supermercado.estoque.application.port.input.product

import java.util.UUID

interface DeleteProductUseCase {
    fun execute(productId: UUID)
}