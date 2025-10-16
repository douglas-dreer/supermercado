package br.com.supermercado.estoque.application.port.input.category

import java.util.*

interface DeleteCategoryUseCase {
    fun execute(productId: UUID)
}